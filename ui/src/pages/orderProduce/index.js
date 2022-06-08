import { Form, Row, Col, Input, InputNumber, Tag, Table, Skeleton, Button, Modal, message } from "antd";
import {LeftOutlined, UnorderedListOutlined } from "@ant-design/icons";
import "../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";

 /** 骨架屏计时器(限制搜索过程中没有物品时骨架屏所显示时间1s) */
function useCounter() {
  const [count, setCount] = useState(0); // 计数
  useEffect(() => {
    const timer = setTimeout(() => {
      count<=1 && setCount(count + 1); // 时间小于2s，计数+1，否则暂停计数器
    }, 1000);
    return () => clearTimeout(timer); // 组件销毁前和更新前，清理 timer
  }, [count]); // 监听依赖列表
  // 重新搜索时重置计数
  const reset = () => setCount(0);

  return [count, reset]; 
}
/**
 * 出餐页面
 * @returns 
 */
export default function OrderProduce() {
    
    let navigate = useNavigate();
    let location = useLocation();
    const [form] = Form.useForm();
    const [loaddingTime, resetTime] = useCounter();
    const [orderItemList, setOrderItemList] = useState();
    const [informationModalVisible, setInformationModalVisible] = useState(false);

    const [orderItem, tenantId, userId, id] = [
      location.state.data,
      location.state.data.tenantId,
      location.state.data.auditing.createdBy,
      location.state.data.id,
    ];

    useEffect(() => {
      queryOrder(tenantId, userId, id);
    }, []);

    /** 订单项模块列 */
    const columns = [
      {
        title: '序号',
        render: (text, records, index)=> 
        <Tag style={{fontSize: 15}}>{getOrderItemNo(records, index)}</Tag>,
      },
      {
        title: '菜品名',
        dataIndex: 'name',
        width: '33%',
        render: (text, records, index) => (
          <span style={{color: 'gray', marginLeft: (records.type!=='product'?"5%":""),
          width: (records.type!=='product'?"90%":"")}}>
            {
              records.type === 'product' ?
              records.methodName ? 
                `${text}-${records.methodName}` : text
              :
              ' - '+records.name
            }
          </span>
        )
      },
      {
        title: '当前数量',
        dataIndex: 'latest',
        key: 'latest',
        sorter: (a, b) => a.latest - b.latest
      },
      {
        title:  '计量单位',
        dataIndex: 'unitOfMeasure',
        key: 'unitOfMeasure',
        filterSearch: true,
        filters: [
            {
              text: '斤',
              value: '元/斤',
            },
            {
              text: '份',
              value: '元/份',
            },
            {
              text: '盘',
              value: '元/盘',
            },
            {
              text: '碗',
              value: '元/碗'
            },
            {
              text: '克',
              value: '元/克',
            },
            {
              text: '公斤',
              value: '元/公斤',
            },
          ],
          onFilter: (value, record) => record.unitOfMeasure.indexOf(value) === 0,
          render: (text, records, index) => (
          text != null && (text.indexOf('/') !== -1 ? text.split('/')[1] : text)
        )
      },
      {
        title: '已出餐',
        dataIndex: 'onProduce',
      },
      {
        title: '本次出餐',
        dataIndex: 'quantityOnProduce',
        render: (text, records, index) => (
          records.status !== 'PLACED' && text !== undefined && <InputNumber min={0} max={records.maxOnProduce} value={text} onChange={(value)=>handleProduce(value, records)}/>
        )
      }
  ];

  /** 查询订单 */
  function queryOrder(tenantId, userId, id){
    const instance = axios.create({
      headers:{tenantId:tenantId, userId:userId}
    });
    instance
      .get(`/order/catering/${id}`)
      .then((res)=>{
        const data = res.data.data;
        initialForm(data);
        setOrderItemList(configData(data.items));
      })
  }

  /** 出餐请求 */
  function orderProduceRequest(request, formValues){
    const [tenantId, userId, id] = [formValues.tenantId, formValues.userId, formValues.id];
    const instance = axios.create({
      headers:{tenantId: tenantId, userId:userId}
    });
    instance
      .post(`/order/catering/${id}/produce`, request)
      .then(response=>{
        const code = response.data.status.code;
        if(code===0){
          message.success('出餐中');
          navigate('/order');
        }else{
          message.error(`操作失败，响应码${code}`);
        };
        console.log(response);
      }, error => {
        message.error(`操作失败，响应码${error.response.status}`);
        console.log(error);
      })
  };

  /** 重组订单信息 */
  const configData = (orderValues) => {
    let newOrderItemList = orderValues;
    newOrderItemList.map((item, index)=>{
      let children = [];
      item.accessories.length > 0 && item.accessories.map((i, ind)=>{
        children.push(
          {
            key: ind,
            type: 'accessory',
            productAccessoryId: i.id,
            name: i.productAccessorySnapshotOnPlace.name,
            unitOfMeasure: i.productAccessorySnapshotOnPlace.unitOfMeasure,
            latest: i.quantity.latest,
            onProduce: i.quantity.onProduce,
            version: i.version,
            status: i.status,
          }
        )
        children[ind].latest > children[ind].onProduce && ([children[ind]['quantityOnProduce'], children[ind]['maxOnProduce']] = [
          children[ind].latest - children[ind].onProduce, children[ind].latest - children[ind].onProduce
        ])
      })
      item['methodName'] = item.productMethodSnapshotOnPlace.name;
      item['latest'] = item.quantity.latest;
      item['onProduce'] = item.quantity.onProduce;
      item['unitOfMeasure'] = item.productSnapshotOnPlace.unitOfMeasure;
      item['name'] = item.productSnapshotOnPlace.name;
      item['type'] = 'product';
      item['children'] = children.length>1?children:null;
      item.latest > item.onProduce && ([item['quantityOnProduce'], item['maxOnProduce']] = [item.latest - item.onProduce, item.latest - item.onProduce]);
      return item;
    })
    return newOrderItemList;
  };


  /** 初始化表单 */
  const initialForm = (orderValues) => {
    form.setFieldsValue({
      shopName: orderValues.shopSnapshotOnPlace.name,
      tableNo: orderValues.tableNo,
      customerCount: orderValues.customerCount,
      comment: orderValues.comment,
      id: orderValues.id,
      version: orderValues.version,
      tenantId: orderValues.tenantId,
      userId: orderValues.auditing.createdBy
    })
  }

  /** 生成订单项的序号 */
  const getOrderItemNo = (orderItem, index) => {
    let itemNo = null;
    if(orderItem.type === 'product'){
      itemNo = index + 1;
    }
    orderItemList.map((item, index)=>{
      item.children != null && item.children.length > 0 && item.children.map((i, ind)=>{
        if(orderItem.productAccessoryId === i.productAccessoryId){
          itemNo = `${index+1}-${ind+1}`;
        }
      })
    })
    return itemNo;
  }

  //TODO:出餐为0时的验证
  const onFinish = (formValues) => {
    const requestBody = createRequestBody(formValues);
    console.log('结构体:', requestBody);
    orderProduceRequest(requestBody, formValues);
  }
  
  /** 出餐结构体 */
  const createRequestBody = (formValues) => {
    let newOrderItemList = [];
    orderItemList.map((item, index)=>{
      if(item.status === 'PREPARING'){
        let accessories = [];
        if(item.children !== null){
          item.children.map((i, ind)=>{
            console.log(item);
            if(i.status === 'PREPARING'){
              accessories.push(
                {
                  productAccessoryId: i.productAccessoryId,
                  quantityOnProduce: i.quantityOnProduce,
                  seqNo: `${index+1}-${ind+1}`,
                  version: i.version,
                }
              )
            }
          })
        }
        item.quantityOnProduce !== undefined && newOrderItemList.push(
          { 
            accessories: accessories,
            seqNo: index+1,
            version: item.version,
            quantityOnProduce: item.quantityOnProduce,
          }
        );
      }
      
    });
    const bodyObj = {
      items: newOrderItemList,
      version: formValues.version
    }
    return bodyObj;
  }

  /** 获取出餐数量更改 */
  const handleProduce = (value, orderItem) => {
    let newOrderItemList = [].concat(orderItemList);
    newOrderItemList.map((item, index)=>{
      orderItem.type === 'product' && orderItem.id === item.id && (item.quantityOnProduce = value);
      orderItem.type === 'accessory' && item.children != null && item.children.map((i, ind)=>{
        orderItem.productAccessoryId === i.productAccessoryId && (i.quantityOnProduce = value);
      })
    });
    setOrderItemList(newOrderItemList);
  }
  
    return (
        <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/order')}/></Col>
            <Col span={23}>出餐：{form.getFieldValue('id')}</Col>
            <Col>
              <UnorderedListOutlined />
            </Col>
          </Row>
        </div>
        <Form
        form={form}
        style={{margin: 20}}
        onFinish={onFinish}
        colon={false}
        >
        <Row className="create-shop-info">
          <Col>基本信息</Col>
        </Row>
        <Row>
          <Col span={12}>
            <Form.Item label='门店' name='shopName' style={{pointerEvents: "none"}}>
              <Input style={{borderRadius: 0}} readOnly/>
            </Form.Item>
          </Col>
          <Col span={11} offset={1}>
            <Form.Item label='座位号' name='tableNo' style={{pointerEvents: "none"}}>
              <Input readOnly/>
            </Form.Item>
          </Col>
        </Row>
        <Row>
            <Col span={12}>
                <Form.Item label='用餐人数' name='customerCount' style={{pointerEvents: "none"}}>
                  <InputNumber style={{width: '100%'}} readOnly/>
                </Form.Item>
            </Col>
            <Col span={11} offset={1}>
                <Form.Item label='备注' name='comment' style={{pointerEvents: "none"}}>
                  <Input readOnly/>
                </Form.Item>
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>菜品信息</Col>
          </Row>
          {/** 骨架屏和表格显示逻辑 */}
          {
            orderItemList!==undefined && orderItemList.length === 0?
                loaddingTime >= 1 ?
                  <Table
                  columns={columns}
                  dataSource={orderItemList}
                  pagination={false}
                  scroll={{y: 320}}
                  />
                  :
                  <Skeleton active paragraph={{ rows: 10 }}/>
                :
                <Table
                columns={columns}
                dataSource={orderItemList}
                pagination={false}
                scroll={{y: 320}}
                />
          }
          <Row className="create-shop-footer">
            <Col span={6} offset={12}>
              <Form.Item>
                <Button style={{background: 'white'}} onClick={()=>setInformationModalVisible(true)}>取消</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button htmlType="submit">确认</Button>
              </Form.Item> 
            </Col>
          </Row>

          <Form.Item name='id' hidden></Form.Item>
          <Form.Item name='userId'hidden></Form.Item>
          <Form.Item name='version' hidden></Form.Item>
          <Form.Item name='tenantId' hidden></Form.Item>
          </Form>

          <Modal
          title="确认丢失修改的内容"
          visible={informationModalVisible}
          footer={null}
          closable={false}
          >
            <Row style={{marginBottom: 10}}><Col offset={7}><span>所有修改均会丢失，请确认?</span></Col></Row>
            <div className="close-btn">
            <Row>
              <Col span={8} offset={4}>
              <Button onClick={()=>{setInformationModalVisible(false)}}>回到当前页面</Button>
              </Col>
              <Col span={11} offset={1}>
              <Button onClick={()=>{navigate('/order')}} style={{background: 'white'}}>返回主页面</Button>
              </Col>
            </Row>
            </div>
          </Modal>
        </>
    )
}