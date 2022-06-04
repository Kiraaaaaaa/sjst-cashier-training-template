import { Form, Row, Col, Input, InputNumber, Tag, Table, Skeleton, Button, Modal, message } from "antd";
import {LeftOutlined, UnorderedListOutlined } from "@ant-design/icons";
import "../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import moment from 'moment';
const USER_INFO = {tenantId: 500, userId: 11000};
const [IS_MAKING, NOT_MAKING] = [true, false];
const testData = 
  {
    "status": {
      "code": 0,
      "msg": "success",
      "success": true,
      "failed": false
    },
    "data": {
      "id": 10020,
      "tenantId": 500,
      "version": 1,
      "auditing": {
        "createdBy": 11000,
        "createdAt": "2022-06-01 13:47:24",
        "lastModifiedBy": null,
        "lastModifiedAt": null
      },
      "status": "PLACED",
      "tableNo": "A05",
      "customerCount": 2,
      "totalPrice": 443.60,
      "comment": null,
      "shopSnapshotOnPlace": {
        "id": 102,
        "businessNo": "1234567892",
        "name": "皮特西餐馆"
      },
      "billing": {
        "promotion": null,
        "paid": null,
        "paymentChannel": null
      },
      "items": [{
        "id": 100076,
        "version": 1,
        "status": "PLACED",
        "seqNo": "1",
        "quantity": {
          "latest": 1.00,
          "onPlace": 1.00,
          "onProduce": 0.00
        },
        "productSnapshotOnPlace": {
          "id": 104,
          "name": "啤酒鸭",
          "unitPrice": 26.80,
          "unitOfMeasure": "元/份"
        },
        "productMethodSnapshotOnPlace": {
          "id": null,
          "name": null,
          "groupName": null
        },
        "accessories": []
      }, {
        "id": 100077,
        "version": 1,
        "status": "PLACED",
        "seqNo": "2",
        "quantity": {
          "latest": 1.00,
          "onPlace": 1.00,
          "onProduce": 0.00
        },
        "productSnapshotOnPlace": {
          "id": 102,
          "name": "松鼠鱼",
          "unitPrice": 128.80,
          "unitOfMeasure": "元/份"
        },
        "productMethodSnapshotOnPlace": {
          "id": null,
          "name": null,
          "groupName": null
        },
        "accessories": []
      }, {
        "id": 100078,
        "version": 1,
        "status": "PLACED",
        "seqNo": "3",
        "quantity": {
          "latest": 1.00,
          "onPlace": 1.00,
          "onProduce": 0.00
        },
        "productSnapshotOnPlace": {
          "id": 100,
          "name": "宫保鸡丁",
          "unitPrice": 288.00,
          "unitOfMeasure": "元/份"
        },
        "productMethodSnapshotOnPlace": {
          "id": null,
          "name": null,
          "groupName": null
        },
        "accessories": []
      }]
    }
 }

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

export default function OrderMake() {

    let navigate = useNavigate();
    const [form] = Form.useForm();
    const [loaddingTime, resetTime] = useCounter();
    const [orderItemList, setOrderItemList] = useState();
    const [informationModalVisible, setInformationModalVisible] = useState(false);
    useEffect(() => {
      // queryOrder(USER_INFO.tenantId, USER_INFO.userId, 100);
      const data =  configData(testData.data.items);
      setOrderItemList(data);
      // setOrderItemList(data);
      initialForm(testData.data);

    }, []);

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
            <Input 
              style={{
                borderRadius: 10,
                pointerEvents: "none",
                background: 'white',
                paddingLeft: '6%',
                marginLeft: (records.type!=='product'?"10%":""),
              }}
              readOnly
              placeholder={
                records.methodName !== null ?
                `${text}-${records.methodName}` : text
              }
            />
          )
      },
      {
          title: '当前数量',
          dataIndex: 'quantity_',
          key: 'quantity_',
          sorter: (a, b) => a.quantity - b.quantity
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
        title: '操作',
        render: (records, index) => {
          if(records.type === 'product'){
            if(records.status === NOT_MAKING){
              return (
                <div className="product-btn">
                  <Button style={{width: '100%'}} onClick={()=>handleMake(records, index)}>制作</Button>
                </div>
              )
            }else{
              return '制作中';
            }
          }else{
            return '';
          }
        }
      }
  ];
  function queryOrder(tenantId, userId, id){
    const instance = axios.create({
        headers:{tenantId:tenantId, userId:userId}
    });
    instance
        .get(`/order/catering/${id}`)
        .then((res)=>{
        })
  }
  function orderMakeRequest(request, formValues){
    const [tenantId, userId, id] = [formValues.tenantId, formValues.userId, formValues.id];
    const instance = axios.create({
        headers:{tenantId: tenantId, userId:userId}
    });
    instance
        .post(`/order/catering/${id}/prepare`, request)
        .then(response=>{
          const code = response.data.status.code;
          if(code===0){
            message.success('制作中');
            navigate('/order');
          }else{
            message.error(`操作失败，响应码${code}`);
          };
          console.log(response);
        }, error => {
          message.error(`操作失败，响应码${error.response.status}`);
          console.log(error);
        })
  }
  const configData = (orderValues) => {
    orderValues.map((item, index)=>{
      if(item.accessories.length > 0){
        item['children'] = item.accessories;
      }
      item['methodName'] = item.productMethodSnapshotOnPlace.name;
      item['quantity_'] = item.quantity.latest;
      item['unitOfMeasure'] = item.productSnapshotOnPlace.unitOfMeasure;
      item['name'] = item.productSnapshotOnPlace.name
      item['type'] = 'product';
      item['status'] = NOT_MAKING;
      return item;
    })
    return orderValues;
  }
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
        if(orderItem.id === i.id){
          itemNo = `${index+1}-${ind+1}`;
        }
      })
    })
    return itemNo;
  }
  const onFinish = (formValues) => {
    const requestBody = createRequestBody(formValues);
    orderMakeRequest(requestBody, formValues);
  }

  const createRequestBody = (formValues) => {
    let newOrderItemList = [];
    orderItemList.map((item, index)=>{
      item.status === IS_MAKING && newOrderItemList.push(
        {
          seqNo: index+1,
          version: item.version
        }
      )
    });
    const bodyObj = {
      items: newOrderItemList,
      version: formValues.version
    }
    return bodyObj;
  }
  const handleMakeAll = () => {
    setOrderItemList(
      [].concat(orderItemList).map((item)=>{
        item.status = IS_MAKING;
        return item;
      })
    )
  }

  const handleMake = (orderItem) => {
    let newOrderItemList = [].concat(orderItemList);
    newOrderItemList.map((item)=>{
      orderItem.id === item.id && (item.status = IS_MAKING);
      return item;
    })
    setOrderItemList(newOrderItemList);
  }
    return (
        <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/order')}/></Col>
            <Col span={23}>制作：{testData.data.id}</Col>
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
              <Col span={4} offset={18}>
                <div className="product-btn">
                  <Button style={{width: '100%'}} onClick={handleMakeAll}>全部制作</Button>
                </div>
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