import { Button, Form, Row, Col, Select, Input, TimePicker, message, Modal} from "antd";
import {LeftOutlined, UnorderedListOutlined} from "@ant-design/icons";
import "../../css/createAndEditShop.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import moment from 'moment';
const { Option } = Select;

const HEADER = {tenantId: 500, userId: 11000};
const SHOP_VERSION = {version: 1};

export default function CreatShop(){

  let toShop = false;
  let navigate = useNavigate();
  const [form] = Form.useForm();
  const [modalVisible, setModalVisible] = useState(false);

  useEffect(()=>{
    initialForm();
  }, []);

  const initialForm = () =>{
    form.setFieldsValue({
      businessType: 'DINNER',
      managementType: 'DIRECT_SALES',
    })
  }

  /** 创建门店request请求 */
  const creatAxiosRequest = (request, headerValues) => {
    const [tenantId, userId, name] = [headerValues.tenantId, headerValues.userId, request.name];
    const instance = axios.create({
      headers:{tenantId, userId}
    });
    instance
      .post('/shop', request)
      .then(response => {
        const code = response.data.status.code;
        if(code === 0){
          message.success(`门店${name}创建成功`);
          if(toShop){
            navigate('/shop');
          }
        }else{
          message.error(`门店${name}创建失败，响应码${code}`);
        }
        console.log(response);
      }, error => {
        console.log(error);
      })
  }

  /** request结构体 */
  const createRequestBody = (formValues) => {
    const bodyObj = {
      "businessArea": formValues.businessArea,
      "businessType": formValues.businessType,
      "comment": formValues.comment,
      "contact": {
        "address": formValues.address,
        "cellphone": formValues.cellphone,
        "name": formValues.contactName,
        "telephone": formValues.telephone,
      },
      "managementType": formValues.managementType,
      "name": formValues.name,
      "openingHours": {
        "closeTime": moment(formValues.openingHours[1]._d.getTime()).format("HH:mm"),
        "openTime": moment(formValues.openingHours[0]._d.getTime()).format("HH:mm"),
      },
    }

    const bodyMap = new Map(Object.entries(bodyObj));
    bodyMap.set(SHOP_VERSION);
    const requestBody = Object.fromEntries(bodyMap);

    return requestBody;
  }

  const onFinish = (values) =>{
    const requestBody = createRequestBody(values);
    creatAxiosRequest(requestBody, HEADER);
  }

  /** 保存并返回 */
  const saveToShop = () =>{
    toShop = true;
    form.submit();
  }

    return (
      <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/shop')}/></Col>
            <Col offset={11}>新门店</Col>
            <Col offset={10}>
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
            <Col>通用信息</Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item
              label="门店名"
              name='name'
              rules={[
                {
                  type:'string',
                  required: true,
                  whitespace: true,
                  message: '输入不能为空',
                },
                {
                  max: 20,
                  message: '门店名的最大长度为20字符',
                },
              ]}
              >
                <Input/>
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={12}>
              <Form.Item
              label="主营业态"
              name="businessType"
              required
              >
                <Select>
                  <Option value="DINNER">正餐</Option>
                  <Option value="FAST_FOOD">快餐</Option>
                  <Option value="HOT_POT">火锅</Option>
                  <Option value="BARBECUE">烧烤</Option>
                  <Option value="WESTERN_FOOD">西餐</Option>
                </Select>
              </Form.Item> 
            </Col>
            <Col span={11} offset={1}>
                <Form.Item label="管理类型" name="managementType" required>
                  <Select>
                    <Option value="DIRECT_SALES">直营</Option>
                    <Option value="ALLIANCE">加盟</Option>
                  </Select>
                </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>联系方式</Col>
          </Row>
          <Row>
            <Col span={7}>
              <Form.Item
              label='座机号'
              name='telephone'
              rules={[
                {
                  pattern: /^(0\d{2,3}-\d{7,8}|\(?0)\d{2,3}[)-]?\d{7,8}|\(?0\d{2,3}[)-]*\d{7,8}/, 
                  message: '输入正确的座机号' 
                }
              ]}
              >
                <Input/>
              </Form.Item> 
            </Col>
            <Col span={7} offset={1}>
              <Form.Item 
              label='手机号'
              name='cellphone'
              rules={[
                {
                  pattern: /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/,
                  message: '输入正确的手机号'
                }
              ]}
              >
                <Input/>
              </Form.Item> 
              </Col>
            <Col span={7} offset={1}>
              <Form.Item
              label='联系人'
              name='contactName'
              rules={[
                {
                  type: 'string',
                  required: true,
                  whitespace: true,
                  message: '输入不能为空'
                },
                {
                  max: 8,
                  message: '长度不能大于8个字符',
                },               
              ]}
              >
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item
              label='地址'
              name='address'
              rules={[
                {
                  type: 'string',
                  required: true,
                  whitespace: true,
                  message: '输入不能为空'
                },
                {
                  max: 30,
                  message: '长度不能大于30个字符',
                },               
              ]}
              >
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>经营信息</Col>
          </Row>
          <Row>
            <Col span={12}>
              <Form.Item
              label='营业时间'
              name='openingHours'
              rules={[
                {
                  type: 'array',
                  required: true,
                  message: '请选择时间',
                }
              ]}
              >
                <TimePicker.RangePicker
                style={{width:"100%"}}
                placeholder={['开始时间', '结束时间']}
                format='HH:mm'
                />
              </Form.Item> 
            </Col>
            <Col span={10} offset={1}>
              <Form.Item
              label='门店面积'
              name='businessArea'
              rules={[
                {
                  type: 'string',
                  whitespace: true,
                  message: '不能输入空格',
                },
                {
                  max: 10,
                  message: '长度不能大于10个字符',
                },               
              ]}
              >
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item 
              label='门店介绍' 
              name='comment'
              rules={[
                {
                  type: 'string',
                  whitespace: true,
                  message: '不能输入空格',
                },
                {
                  max: 100,
                  message: '长度不能大于100个字符',
                },               
              ]}
              >
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-footer">
            <Col span={6} offset={6}>
              <Form.Item>
                <Button style={{background: 'white'}} onClick={()=>setModalVisible(true)}>取消</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button onClick={saveToShop}>保存并返回</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button htmlType="submit">保存</Button>
              </Form.Item> 
            </Col>
          </Row>
        </Form>
        <Modal
        title="确认丢失修改的内容"
        visible={modalVisible}
        footer={null}
        closable={false}
        >
          <Row style={{marginBottom: 10}}><Col offset={7}><span>所有修改均会丢失，请确认?</span></Col></Row>
          <div className="close-btn">
          <Row>
            <Col span={8} offset={4}>
            <Button onClick={()=>{setModalVisible(false)}}>回到当前页面</Button>
            </Col>
            <Col span={11} offset={1}>
            <Button onClick={()=>{navigate('/shop')}} style={{background: 'white'}}>返回主页面</Button>
            </Col>
          </Row>
          </div>
        </Modal>
      </>
    )
}