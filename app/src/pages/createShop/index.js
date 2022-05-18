import { Button, Form, Row, Col, Select, Input, DatePicker} from "antd"
import {LeftOutlined, UnorderedListOutlined} from "@ant-design/icons"
import "../../css/createShop.css";
import React, { useEffect } from "react";
import axios from "axios";
import { useNavigate, Link, useLocation, useParams, useMatch } from "react-router-dom";
import moment from 'moment';
const { Option } = Select;
const { RangePicker } = DatePicker;
export default function CreatShop(props){
  let location = useLocation();
  let navigate = useNavigate();
  console.log(props);
    return (
      <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate(-1)}/></Col>
            <Col offset={11}>新门店</Col>
            <Col offset={10}>
              <UnorderedListOutlined />
            </Col>
          </Row>
        </div>
        <Form style={{margin: 20}}>
          <Row className="create-shop-info">
            <Col>通用信息</Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item label="门店名" name={'name'}>
                <Input/>
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={12}>
              <Form.Item label="主营业态" name={"businessType"}>
                <Select defaultValue="all">
                  <Option value="all">所有</Option>
                  <Option value="dinner">正餐</Option>
                  <Option value="fastFood">快餐</Option>
                  <Option value="hotpot">火锅</Option>
                  <Option value="barbeque">烧烤</Option>
                  <Option value="westurnFood">西餐</Option>
                </Select>
              </Form.Item> 
            </Col>
            <Col span={11} offset={1}>
                <Form.Item label="管理类型" name={"managementType"}>
                  <Select defaultValue="all">
                    <Option value="all">所有</Option>
                    <Option value="directSales">直营</Option>
                    <Option value="alliance">加盟</Option>
                  </Select>
                </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>联系方式</Col>
          </Row>
          <Row>
            <Col span={7}>
              <Form.Item label='座机号' name={'telephone'}>
                <Input/>
              </Form.Item> 
            </Col>
            <Col span={7} offset={1}>
              <Form.Item label='手机号' name={'cellphone'}>
                <Input/>
              </Form.Item> 
              </Col>
            <Col span={7} offset={1}>
              <Form.Item label='联系人' name={'contactName'}>
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item label='地址' name={'address'}>
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>经营信息</Col>
          </Row>
          <Row>
            <Col span={12}>
              <Form.Item label='营业时间' name={'openingHours'}>
              <RangePicker
              placeholder={['开始时间', '结束时间']}
              ranges={{
                '今日': [moment(), moment()],
                '这个月': [moment().startOf('month'), moment().endOf('month')],
              }}
              showTime
              format="YYYY/MM/DD HH:mm:ss"
              />
              </Form.Item> 
            </Col>
            <Col span={10}>
              <Form.Item label='门店面积' name={'businessArea'}>
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item label='门店介绍' name={'comment'}>
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-footer">
            <Col span={6} offset={6}>
              <Form.Item>
                <Button>取消</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button>保存并返回</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button>保存</Button>
              </Form.Item> 
            </Col>
          </Row>
        </Form>
      </>
    )
}