import { Button, Form, Row, Col, Select, Input, TimePicker, message, Modal, InputNumber, Space, notification, Tag, Divider } from "antd";
import {LeftOutlined, UnorderedListOutlined, MinusCircleOutlined, PlusOutlined} from "@ant-design/icons";
import "../../css/createAndEditShop.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import moment from 'moment';

const HEADER = {tenantId: 500, userId: 11000};
const CURRENCY_UNIT = '元'; //统一价格单位;
const ACCESSORY_GROUP_NAME = '配菜'; //由于需求不需要加料组名，统一加料组为配菜;

/** 默认做法组 */
const defaultMethodGroup = [
  {
    key: 1,
    name: 1,
    items:[
      {
        name: 1,
        key: 1,
      },
      {
        name: 2,
        key: 2,
      },
      {
        name: 3,
        key: 3,
      }
    ]
  },
  {
    key: 2,
    items:[
      {
        name: 1,
        key: 1,
      },
      {
        name: 2,
        key: 2,
      },
      {
        name: 3,
        key: 3,
      }
    ]
  },
]

/** 默认加料数量 */
const defaultAccessoryGroup = [1, 2, 3];

export default function CreatProduct(){

  let toProduct = false;
  let navigate = useNavigate();
  const [form] = Form.useForm();
  const [modalVisible, setModalVisible] = useState(false);
  const [methodGroup, setMethodGroup] = useState(defaultMethodGroup);
  const [accessoryGroup, setAccessoryGroup] = useState(defaultAccessoryGroup);

  useEffect(() => {
    initialForm();
  }, [])

  const initialForm = () => {
    form.setFieldsValue({
      minSalesQuantity: 1,
      increaseSalesQuantity: 1,
    });
  }
  
  /** 提交表单 */
  const onFinish = (values) => {
    const requestBody = createRequestBody(values);
    creatAxiosRequest(requestBody, HEADER);
  }

  /** request结构体 */
  const createRequestBody = (formValues) => {
    
    const methodGroups = [];
    let accessoryGroups = [];
    const uniteOfMeasure =  CURRENCY_UNIT+'/'+formValues.unitOfMeasure;
    console.log(formValues);
    const valuesArr = Object.entries(formValues);

    /** 第一次遍历表单数据，初始化做法与加料数组结构 */
    valuesArr.map((item) => {

      /** 设置做法组 */
      if(item[0].indexOf('methodGroup_')!==-1){
        const name = item[1];
        methodGroups.push(
          {
            "name": name,
            "options":[]
          }
        );
      }

      /** 
       * 设置加料组
       *  
       * 加料组的数据组织方式稍有不同
       * (需要先设置加料组数据结构为数组才能进行依次添加，
       * 然后将加料组内部数组结构转为对象包装成请求体)
       * 
      */
      if(item[0].indexOf('accessory_')!==-1){

        const numbers = item[0].match(/\d+(\.\d+)?/g);
        const name = item[1];
        const id = numbers[1];
        if(accessoryGroups.length === 0){
          accessoryGroups.push(
            {
              "name": ACCESSORY_GROUP_NAME,
              "options": []
            }
          )
        };
        accessoryGroups[0].options.push(
          [
            ['id', id],
            ['name', name]
          ]
        )
      }
    })
    console.log(valuesArr);

    /**
     * 第二次再遍历出表单做法与加料项
     * 
     * 此时可以通过坐标设置目标值
     */
    valuesArr.map((item) => {

      /** 做法 */
      if(item[0].indexOf('method_')!==-1){
        const numbers = item[0].match(/\d+(\.\d+)?/g);
        const [groupId, name, id] = [numbers[0], item[1], numbers[2]];
        methodGroups[groupId].options.push(
          {
            "id": id,
            "name": name
          }
        )
      }

      /** 加料单价 */
      if(item[0].indexOf('unitAccessoryPrice_')!==-1){
        const numbers = item[0].match(/\d+(\.\d+)?/g);
        const [groupId, price] = [numbers[0], item[1]];
        accessoryGroups[0].options[groupId].push(
          ['unitPrice', price]
        )
      }

      /** 加料单位 */
      if(item[0].indexOf('unitOfAccessoryMesure_')!==-1){
        const numbers = item[0].match(/\d+(\.\d+)?/g);
        const [groupId, measure] = [numbers[0], item[1]];
        accessoryGroups[0].options[groupId].push(
          ['unitOfMeasure', measure]
        )
      }
    });

    if(accessoryGroups.length!==0){
      accessoryGroups = accessoryConvert(accessoryGroups);
    }

    const bodyObj = {
      "accessoryGroups": accessoryGroups,
      "description": formValues.description,
      "increaseSalesQuantity": formValues.increaseSalesQuantity,
      "methodGroups": methodGroups,
      "minSalesQuantity": formValues.minSalesQuantity,
      "name": formValues.name,
      "unitOfMeasure": uniteOfMeasure,
      "unitPrice": formValues.unitPrice
    }

    return bodyObj;
  }

  /** 将accessoryGroups数组转为对象 */
  const accessoryConvert = (accessoryGroups) => {
    let options = accessoryGroups[0].options.map((item, index)=>{
      item = Object.fromEntries(item);
      return item;
    })
    accessoryGroups[0].options = options; 
    return accessoryGroups;
  }

  /** 创建菜品request请求 */
  const creatAxiosRequest = (request, headerValues) => {
    const [tenantId, userId, producName] = [headerValues.tenantId, headerValues.userId, request.name];
    const instance = axios.create({
      headers:{tenantId, userId}
    });
    instance
      .post('/product/create', request)
      .then(response => {
        const code = response.data.status.code;
        if(code === 0){
          notification['success']({
            message: `菜品${producName}创建成功`,
            description:
            <Space direction="vertical">
              <div>
                <Tag color='green'>创建者</Tag>
                <span>{userId}</span>
              </div>
              <div>
                <Tag color='blue'>创建时间</Tag>
                <span>{moment().format('YYYY-MM-DD HH:mm:ss')}</span>
              </div>
            </Space>
          });
          if(toProduct){
            navigate('/product');
          }
        }else{
          notification['error']({
            message: `菜品${producName}创建失败`,
            description:
            <Space direction="vertical">
              <div>
                <Tag color='red'>响应码</Tag>
                <span>{code}</span>
              </div>
              <div>
                <Tag color='yellow'>详细信息</Tag>
                <span>请联系系统管理员</span>
              </div>
            </Space>
          });
        }
        console.log(response);
      }, error => {
        console.log(error);
      })
  }

  /** 保存并返回 */
  const saveToProduct = () =>{
    toProduct = true;
    form.submit();
  }

  /** 增加做法组 */
  const addMethodGroup = () =>{
    const startKey = methodGroup.length;
    const newGroup = [...methodGroup];
    newGroup.push(
      {
        key: startKey,
        items:[
          {
            name: 1,
            key: 1,
          },
          {
            name: 2,
            key: 2,
          },
          {
            name: 3,
            key: 3,
          }
        ]
      },
    )
    setMethodGroup(newGroup);
  }

  /** 增加做法 */
  const addMethod = (index) => {
    const newGroup = [...methodGroup];
    const len = newGroup[index].items.length;
    newGroup[index].items.push(
      {
        name: len+1,
        key: len+1,
      },
    )
    setMethodGroup(newGroup);
  }
  
  /** 减少做法组 */
  const decreaseMethodGroup = (index) => {
    let newGroup = [...methodGroup];
    newGroup.splice(index, 1);
    console.log(newGroup);
    setMethodGroup(newGroup);
  }

  /** 减少做法 */
  const decreaseMethod = (index, ind) => {
    let newGroup = [...methodGroup];
    newGroup[index].items.splice(ind, 1);
    console.log(newGroup);
    if(newGroup[index].items.length===0){
      newGroup.splice(index, 1);
    }
    setMethodGroup(newGroup);
  }

  /** 增加加料 */
  const addAccessory = () => {
    let newGroup = [...accessoryGroup];
    let len = newGroup.length;
    newGroup.push(len+1);
    setAccessoryGroup(newGroup);
  }

  /** 减少加料 */
  const decreaseAccessory = (index) => {
    let newGroup = [...accessoryGroup];
    newGroup.splice(index, 1);
    setAccessoryGroup(newGroup);
  }

    return (
      <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/product')}/></Col>
            <Col offset={11}>新菜品</Col>
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
              label="菜品名"
              name='name'
              rules={[
                {
                  required: true,
                },
                {
                  pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,10}$/,
                  message: '输入不能含特殊字符，且长度不大于10',
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
              label="菜品单价"
              name="unitPrice"
              rules={[
                {
                  required: true,
                  message: '输入不能为空',
                },
                {
                  validator(_, value) {
                    if (!value||(999.9 >= value && value >= 0.01)) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error('价格区间在0.01到999.9之间!'));
                  },
                },
              ]}
              >
                <InputNumber style={{width:'100%'}}/>
              </Form.Item> 
            </Col>
            <Col span={11} offset={1}>
                <Form.Item
                label="计量单位"
                name="unitOfMeasure"
                rules={[
                  {
                    required: true,
                  },
                  {
                    pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,5}$/,
                    message: '输入不能含特殊字符，且长度不大于5',
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
              name='minSalesQuantity'
              label='起售量'
              rules={[
                {
                  required: true,
                  message: '输入不能为空',
                },
                {
                  validator(_, value) {
                    if (!value||(100 >= value && value >= 1)) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error('输入区间在1~100!'));
                  },
                },
              ]}
              >
                <InputNumber min={1} style={{width:'100%'}}/>
              </Form.Item>
            </Col>
            <Col span={11} offset={1}>
              <Form.Item
              label='增售量'
              name='increaseSalesQuantity'
              rules={[
                {
                  required: true,
                  message: '输入不能为空',
                },
                {
                  validator(_, value) {
                    if (!value||(100 >= value && value >= 1)) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error('输入区间在1~100!'));
                  },
                },
              ]}
              >
                <InputNumber min={1} style={{width:'100%'}}/>
              </Form.Item> 
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <Form.Item
              label='描述'
              name='description'
              rules={[
                {
                  type: 'string',
                  required: true,
                  whitespace: true,
                  message: '输入不能为空'
                },
                {
                  max: 50,
                  message: '输入不能大于50个字符',
                },               
              ]}
              >
                <Input/>
              </Form.Item> 
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>做法</Col>
            <Col span={6} offset={17}>
              <div className="product-btn">
                <Button style={{width: '100%'}} onClick={addMethodGroup}>添加做法组</Button>
              </div>
            </Col> 
          </Row>
          {
            methodGroup.map((item, index)=>{
              return (
                <>
                {
                  item.items.map((i, ind)=>{
                    if(ind===0){
                      return (
                        <Row>
                          <Col span={9}>
                            <Form.Item 
                            label='做法组名' 
                            name={`methodGroup_${index}`}
                            rules={[
                              {
                                required: true,
                              },
                              {
                                pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,6}$/,
                                message: '输入不能含特殊字符，且长度不大于6',
                              },
                            ]}
                            >
                              <Input/>
                            </Form.Item>
                          </Col>
                          <Col span={1}>
                            <MinusCircleOutlined style={{fontSize: '200%', paddingTop: 2, color: "gray"}} onClick={decreaseMethodGroup.bind(this, index)}/>
                          </Col>
                          <Col span={13}>
                            <Form.Item 
                            label={`做法${ind+1}`} 
                            name={`method_${index}_${ind}`}
                            rules={[
                              {
                                required: true,
                              },
                              {
                                pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,6}$/,
                                message: '输入不能含特殊字符，且长度不大于6',
                              },
                            ]}
                            >
                              <Input/>
                            </Form.Item>
                          </Col>
                          <Col span={1}>
                            <MinusCircleOutlined style={{fontSize: '200%', paddingTop: 2, color: "gray"}} onClick={decreaseMethod.bind(this, index, ind)}/>
                          </Col>
                        </Row>
                      )
                    }else{
                      return (
                        <Row>
                        <Col span={10}>
                        </Col>
                        <Col span={13}>
                          <Form.Item 
                          label={`做法${ind+1}`} 
                          name={`method_${index}_${ind}`}
                          rules={[
                            {
                              required: true,
                            },
                            {
                              pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,6}$/,
                              message: '输入不能含特殊字符，且长度不大于6',
                            },
                          ]}
                          >
                            <Input/>
                          </Form.Item>
                        </Col>
                        <Col span={1}>
                          <MinusCircleOutlined style={{fontSize: '200%', paddingTop: 2, color: "gray"}} onClick={decreaseMethod.bind(this, index, ind)}/>
                        </Col>
                      </Row>
                      )
                    }
                  })
                }
                <Row>
                  <Col span={10}></Col>
                  <Col span={13}>
                    <div className="product-btn">
                      <Button style={{width: '100%', margin: '-10px 0px 10px', }} onClick={addMethod.bind(this, index)}>添加做法</Button>
                    </div>
                  </Col>
                </Row>
                <Divider/>
                </>
              )
            })
          }
          <Row className="create-shop-info">
            <Col>加料</Col>
          </Row>
          {
            accessoryGroup.map((item, index) => {
              return (
                <Row>
                  <Col span={9}>
                    <Form.Item 
                    label='加料名' 
                    name={`accessory_${index}`}
                    rules={[
                      {
                        required: true,
                      },
                      {
                        pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,12}$/,
                        message: '输入不能含特殊字符，且长度不大于12',
                      },
                    ]}
                    >
                      <Input/>
                    </Form.Item>
                  </Col>
                  <Col span={6} offset={1}>
                    <Form.Item 
                    label='单价' 
                    name={`unitAccessoryPrice_${index}`}
                    rules={[
                      {
                        required: true,
                        message: '输入不能为空',
                      },
                      {
                        validator(_, value) {
                          if (!value||(999.9 >= value && value >= 0.01)) {
                            return Promise.resolve();
                          }
                          return Promise.reject(new Error('单价最少大于0!'));
                        },
                      },
                    ]}
                    >
                      <InputNumber style={{width: '100%'}}/>
                    </Form.Item>
                  </Col>  
                  <Col span={6} offset={1}>
                    <Form.Item 
                    label='计量单位' 
                    name={`unitOfAccessoryMesure_${index}`}
                    rules={[
                      {
                        required: true,
                      },
                      {
                        pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,12}$/,
                        message: '输入不能含特殊字符，且长度不大于5',
                      },
                    ]}
                    >
                      <Input/>
                    </Form.Item>
                  </Col>
                  <Col span={1}>
                    <MinusCircleOutlined style={{fontSize: '200%', paddingTop: 2, color: "gray"}} onClick={decreaseAccessory.bind(this, index)}/>
                  </Col>
                </Row>
              )
            })
          }
          <Divider />
          <Row>
            <Col span={24}>
              <div className="product-btn">
                <Button style={{width: '100%', margin: '-10px 0px 10px', }} onClick={addAccessory}>添加加料</Button>
              </div>
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
                <Button onClick={saveToProduct}>保存并返回</Button>
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
            <Button onClick={()=>{navigate('/product')}} style={{background: 'white'}}>返回主页面</Button>
            </Col>
          </Row>
          </div>
        </Modal>
      </>
    )
}