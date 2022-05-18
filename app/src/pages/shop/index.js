import { Button, Form, PageHeader, Row, Col, Select, Input, Table, Tag } from "antd"
import {LeftOutlined, UnorderedListOutlined} from "@ant-design/icons"
import "../../css/shop.css";
import React, { useEffect } from "react";
import axios from "axios";
import { useNavigate, Link, useLocation, useParams, useMatch } from "react-router-dom";
const { Option } = Select
const data = [];
for (let i = 0; i < 50; i++) {
    data.push({
    businessNo: i,
    enabled: i%2===0?false:true,
    name: `门店 ${i}`,
    businessType: 'null',
    manageType: 'null',
    openingHours: {"openTime":"10:00", "closeTime":"22:00"},
    businessArea: 'null',
  });
}
const columns = [
    {
      title: '营业状态',
      dataIndex: 'enabled',
      key: 'enabled',
      render: (enabled) => <Tag style={{'fontSize':15}} color={enabled?'green':'red'}>{enabled ? "营业中" : "停业中"}</Tag>,
    },
    {
      title: '门店名',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '主营业态',
      dataIndex: 'businessType',
      key: 'businessType',
    },
    {
        title: '管理类型',
        dataIndex: 'manageType',
        key: 'manageType',
    },
    {
        title: '营业时间',
        dataIndex: 'openingHours',
        key: 'openingHours',
        render: (openingHours) => (
            openingHours.openTime+"~"+openingHours.closeTime
        )
    },
    {
        title: '营业面积',
        dataIndex: 'businessArea',
        key: 'businessArea',
    },
    {
        title: '操作',
        dataIndex: '',
        key: 'action',
        render: (text, recode, index) => (
            <div style={{margin: -15}}>
            <Button type="link" onClick={() => this.seeDetailFunc(text)}>
            编辑
            </Button>
            <span style={{color:'lightgray'}}>|</span>
            <Button type="link">
                {text.enabled?'停用':'启用'}
            </Button>
            </div>
        ),
    },
  ];
// function queryShops(){
//     const instance = axios.create({
//         headers:{tenantId:500, userId:11000}
//     });
//     instance
//     .get('/shop/1234567890')
//     .then((res)=>{
//         console.log(res.data);
//     })
// }
const test=()=>{
    this.props.navigation.push('Section',{id:'1'});
}
export default function Shop(){
    let navigate = useNavigate();
    // useEffect(()=>{
    //     queryShops();
    // }, []);
    return (
        <div>
            <div className="shop-head">
                <Row className="create-shop-btn">
                    <Col><LeftOutlined onClick={()=>navigate('/')}/></Col>
                    <Col offset={10}>门店管理</Col>
                    <Col offset={9} span={2}>
                        {/* <Button onClick={()=>navigate('/createShop')}>
                            创建新门店
                        </Button> */}
                        {/* <Button onClick={test()}>
                            test
                        </Button> */}
                        <Button>
                        <Link to={{ pathname: "/createShop", search: "?name=netflix&age=18" }}>
                            创建新门店
                        </Link>
                        </Button>
                    </Col>
                    <Col>
                        <UnorderedListOutlined />
                    </Col>
                </Row>
            </div>
            <Form style={{"margin-top": 20}}>
                <Row>
                    <Col span={7}>
                        <Form.Item label="营业状态" name={"enable"}>
                            <Select defaultValue="all">
                                <Option value="all">所有</Option>
                                <Option value="open">营业中</Option>
                                <Option value="close">停业中 </Option>
                            </Select>
                        </Form.Item> 
                    </Col>
                    <Col span={7} offset={1}>
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
                    <Col span={7} offset={1}>
                        <Form.Item label="管理类型" name={"managementType"}>
                            <Select defaultValue="all">
                            <Option value="all">所有</Option>
                            <Option value="directSales">直营</Option>
                            <Option value="alliance">加盟</Option>
                            </Select>
                        </Form.Item> 
                    </Col>
                </Row>
                <Row className="create-shop-btn">
                    <Col span={12}>
                        <Form.Item label="门店名" name={"name"}>
                            <Input/>
                        </Form.Item>
                    </Col>
                    <Col span={4} offset={1}>
                        <Form.Item>
                            <Button style={{width:'100%'}}>搜索</Button>
                        </Form.Item>
                    </Col>
                    <Col span={4} offset={1}>
                        <Form.Item>
                            <Button style={{width:'100%'}}>重置</Button>
                        </Form.Item>
                    </Col>
                </Row>

            </Form>
            <Table
                columns={columns}
                pagination={{
                    showSizeChanger: true,
                    showQuickJumper: true,
                    showTotal: (total) => `总共${total}项目`,
                }}
                dataSource={data}
            />
        </div>
    )
}