import { Form, Result, Button, Col, Row } from "antd";
import {LeftOutlined, UnorderedListOutlined } from "@ant-design/icons";
import "../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function PayResult() {
    let navigate = useNavigate();
    const [form] = Form.useForm();
    
    return (
      <Result
      style={{height: 500}}
      status="success"
      title="订单支付成功!"
      subTitle={
        <>
        <Row>
          <Col span={2} offset={10}>订单编号:</Col>
          <span>1221243412</span>
        </Row>
        <Row>
          <Col span={2} offset={10}>支付时间:</Col>
          <span>23123231</span>
        </Row>
        <Row>
          <Col span={2} offset={10}>支付金额:</Col>
          <span>231</span>
        </Row>
        </>
      }
      extra={[
        <Button key="console" onClick={()=>navigate('/order')}>
          返回主页面
        </Button>,
        <Button key="buy" onClick={()=>{}}>查看订单</Button>,
      ]}
    />
    )
}