import { Form, Row, Col, Input, InputNumber, Tag, Table, Skeleton, Button, Modal, message, Space, Image } from "antd";
import {LeftOutlined, UnorderedListOutlined } from "@ant-design/icons";
import "../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

const DEAFAULT_PROMOTION = 0; //默认折扣

/** 骨架屏与支付计时器(限制搜索过程中没有物品时骨架屏所显示时间1s, 并模拟支付时间) */
function useCounter(order) {
  let navigate = useNavigate();
  const [count, setCount] = useState(0); // 计数
  const [isPayReasult, setIsPayReasult] = useState(false);
  useEffect(() => {
    const timer = setTimeout(() => {
      count<=1 && setCount(count + 1); // 时间小于2s，计数+1，否则暂停计数器
      count > 1 && isPayReasult && navigate('/payReasult', {state: {data: order}, replace: true}); //付款时间大于一秒则跳转至结算成功
    }, 1000);
    return () => clearTimeout(timer); // 组件销毁前和更新前，清理 timer
  }, [count, isPayReasult]); // 监听依赖列表
  // 重新搜索时重置计数
  const isReasult = (status) => {setIsPayReasult(status); setCount(0)};
  return [count, isReasult]; 
}

export default function OrderBill() {
    
    let navigate = useNavigate();
    let location = useLocation();

    const [order, tenantId, userId, id] = [
      location.state.data,
      location.state.data.tenantId,
      location.state.data.auditing.createdBy,
      location.state.data.id,
    ];

    const [form] = Form.useForm();
    const [loaddingTime, isReasult] = useCounter(order);
    const [orderItemList, setOrderItemList] = useState();
    const [payModalVisible, setPayModalVisible] = useState(false);
    const [orderValues, setOrderValues] = useState();

    useEffect(() => {
      queryOrder(tenantId, userId, id);
    }, []);

    /** 订单模块列 */
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
          <span style={{color: 'gray'}}>{records.methodName !== null ? `${text} - ${records.methodName}` : text}</span>
        )
      },
      {
        title: '下单数量',
        dataIndex: 'onPlace',
        sorter: (a, b) => a.latest - b.latest
      },
      {
        title: '结账数量',
        dataIndex: 'latest'
      },
      {
        title: '单价',
        dataIndex: 'unitPrice'
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
        title: '总价',
        dataIndex: 'totalPrice',
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
        setOrderValues(data);
        initialForm(data, DEAFAULT_PROMOTION);
        setOrderItemList(configData(data.items));
      })
  }

  /** 结账请求 */
  function orderBillRequest(request, formValues){
    const [tenantId, userId, id] = [formValues.tenantId, formValues.userId, formValues.id];
    const instance = axios.create({
      headers:{tenantId: tenantId, userId:userId}
    });
    console.log(request, tenantId, userId, id)
    instance
      .post(`/order/catering/${id}/bill`, request)
      .then(response=>{
        const code = response.data.status.code;
        if(code!==0){
          message.error(`操作失败，响应码${code}`);
        }else{
          isReasult(true);
        }
        console.log(response);
      }, error => {
        message.error(`操作失败，响应码${error.response.status}`);
        console.log(error);
      })
  };

  /** 重组订单信息 */
  const configData = (orderValues) => {
    orderValues.map((item, index)=>{
      let children = [];
      item.accessories.length > 0 && item.accessories.map((i, ind)=>{
        children.push(
          {
            type: 'accessory',
            name: i.name,
          }
        )
      })
      item['methodName'] = item.productMethodSnapshotOnPlace.name;
      item['latest'] = item.quantity.latest;
      item['onProduce'] = item.quantity.onProduce;
      item['unitOfMeasure'] = item.productSnapshotOnPlace.unitOfMeasure;
      item['unitPrice'] = item.productSnapshotOnPlace.unitPrice;
      item['onPlace'] = item.quantity.onPlace;
      item['totalPrice'] = item.unitPrice * item.latest;
      item['name'] = item.productSnapshotOnPlace.name;
      item['type'] = 'product';
      item.latest > item.onProduce && ([item['quantityOnProduce'], item['maxOnProduce']] = [item.latest - item.onProduce, item.latest - item.onProduce]);
      return item;
    })
    return orderValues;
  };

  /** 初始化表单 */
  const initialForm = (orderValues, promotionValue) => {
    form.setFieldsValue({
      shopName: orderValues.shopSnapshotOnPlace.name,
      tableNo: orderValues.tableNo,
      customerCount: orderValues.customerCount,
      comment: orderValues.comment,
      id: orderValues.id,
      version: orderValues.version,
      tenantId: orderValues.tenantId,
      userId: orderValues.auditing.createdBy,
      totalPrice: orderValues.totalPrice,
      promotion: promotionValue,
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
        if(orderItem.id === i.id){
          itemNo = `${index+1}-${ind+1}`;
        }
      })
    })
    return itemNo;
  }

  const onFinish = (formValues) => {
    setPayModalVisible(true);
    const requestBody = createRequestBody(formValues, form.getFieldValue('promotion'));
    orderBillRequest(requestBody, formValues);
  }

  /** 结账结构体 */
  const createRequestBody = (formValues, promotion) => {
    const bodyObj = {
      paid: formValues.totalPrice - promotion,
      paymentChannel: 'ALIPAY',
      promotion: promotion,
      version: formValues.version,
    }
    return bodyObj;
  }

  /** 获取优惠变化 */
  const handlePromotion = (value) => {
    initialForm(orderValues, value);
  } 

  /** 取消支付 */
  const handlePayCannel = () => {
    isReasult(false);
    setPayModalVisible(false);  
  }

  /** 触发表单提交 */
  const submitForm = () => {
    form.submit();
    
  }
    return (
        <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/order')}/></Col>
            <Col span={23}>结账：{form.getFieldValue('id')}</Col>
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
            <Col offset={16} style={{fontSize: '100%' }}>
              <Space>
                <span>订单总价: {form.getFieldValue('totalPrice')}</span>
                <span>优惠金额:</span>
                <InputNumber
                min={0} 
                max={form.getFieldValue('totalPrice')}
                onChange={handlePromotion}
                value={console.log(form.getFieldValue('promotion'))}
                />
                <span>元</span>
              </Space>
            </Col>
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

          <Row className="bill-btn" style={{marginTop: 10}}>
            <Col span={24}>
              <Button style={{width: '100%'}} onClick={submitForm}>结账</Button>
            </Col>
          </Row>

            <Form.Item name='id' hidden></Form.Item>
            <Form.Item name='userId'hidden></Form.Item>
            <Form.Item name='version' hidden></Form.Item>
            <Form.Item name='tenantId' hidden></Form.Item>
            <Form.Item name='totalPrice' hidden></Form.Item>
          </Form>

          <Modal
          title={`请选择支付方式——(模拟支付中，请等待${2-loaddingTime}秒)`}
          visible={payModalVisible}
          footer={null}
          closable={false}
          >
            <Row>
              <Col offset={9}>
                <Space>
                  <span>待支付金额:</span>
                  <span>{form.getFieldValue('totalPrice')-form.getFieldValue('promotion')}</span>
                  <span>元</span>
                </Space>
              </Col>
            </Row>
            <Image.PreviewGroup>
              <Image width={'50%'} src="https://s-cd-325-t1asas.oss.dogecdn.com/shixun/ye_1242x1242.jpg" />
              <Image width={'50%'} src="https://s-cd-325-t1asas.oss.dogecdn.com/shixun/zhang_1242x1242.jpg" />
            </Image.PreviewGroup>
            <div className="bill-btn">
              <Button style={{width: '100%'}} onClick={handlePayCannel}>取消</Button>
            </div>
          </Modal>
        </>
    )
}