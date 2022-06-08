import { Form, Row, Col, Input, InputNumber, Tag, Table, Skeleton, Button, Modal, message, Space, Image } from "antd";
import {LeftOutlined, UnorderedListOutlined } from "@ant-design/icons";
import "../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
const PAY_CHANNEL = new Map([['ALIPAY', '支付宝'], ['WECHAT', '微信']]);
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

export default function OrderCheck() {
    
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
    useEffect(() => {
      queryOrder(tenantId, userId, id);
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

  function queryOrder(tenantId, userId, id){
    const instance = axios.create({
      headers:{tenantId:tenantId, userId:userId}
    });
    instance
      .get(`/order/catering/${id}`)
      .then((res)=>{
        const data = res.data.data;
        initialForm(data, DEAFAULT_PROMOTION);
        setOrderItemList(configData(data.items));
      })
  }

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

  const initialForm = (orderValues) => {
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
      promotion: orderValues.billing.promotion,
      paid: orderValues.billing.paid,
      channel: orderValues.billing.paymentChannel,
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
    return (
        <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/order')}/></Col>
            <Col span={23}>已完成订单：{form.getFieldValue('id')}</Col>
            <Col>
              <UnorderedListOutlined />
            </Col>
          </Row>
        </div>
        <Form
        form={form}
        style={{margin: 20}}
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
                <span>{form.getFieldValue('promotion')}</span>
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

            <Row>
              <Col>
                <Space>
                  <span>{`已${PAY_CHANNEL.get(form.getFieldValue('channel'))}支付:`}</span>
                  <span>{form.getFieldValue('paid')}</span>
                  <span>元</span>
                </Space>
              </Col>
            </Row>
            <Form.Item name='id' hidden></Form.Item>
            <Form.Item name='userId'hidden></Form.Item>
            <Form.Item name='version' hidden></Form.Item>
            <Form.Item name='tenantId' hidden></Form.Item>
            <Form.Item name='totalPrice' hidden></Form.Item>
          </Form>
        </>
    )
}