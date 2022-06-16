import { Form, Result, Button, Col, Row } from "antd";
import {LeftOutlined, UnorderedListOutlined } from "@ant-design/icons";
import "../../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import moment from 'moment';
import { useNavigate, useLocation } from "react-router-dom";

/**
 * 支付成功页面
 * @returns 
 */

export default function PayResult() {
    let navigate = useNavigate();
    let location = useLocation();
    const order = location.state.data;
    return (
      <Result
      style={{height: 500}}
      status="success"
      title="订单支付成功!"
      subTitle={
        <>
        <Row>
          <Col span={2} offset={10}>订单编号:</Col>
          <span>{order.id}</span>
        </Row>
        <Row>
          <Col span={2} offset={10}>支付时间:</Col>
          <span>{moment().format('YYYY-MM-DD HH:mm:ss')}</span>
        </Row>
        <Row>
          <Col span={2} offset={10}>支付金额:</Col>
          <span>{order.totalPrice}</span>
          <span>元</span>
        </Row>
        </>
      }
      extra={[
        <Button key="console" onClick={()=>navigate('/order')}>
          返回主页面
        </Button>,
        <Button key="buy" onClick={()=>navigate('/orderCheck', {state: {data: order}, replace: true})}>查看订单</Button>,
      ]}
    />
    )
}