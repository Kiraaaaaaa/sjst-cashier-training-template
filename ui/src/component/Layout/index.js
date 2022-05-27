import { Button, Col, Layout, Menu, Row, Badge} from 'antd';
import MenuItem from 'antd/lib/menu/MenuItem';
import React from 'react';
import { Link, Route, Routes, useNavigate } from 'react-router-dom';
import { QuestionCircleOutlined, BellOutlined } from "@ant-design/icons";
import Home from '../../pages/home';
import Shop from '../../pages/shop';
import Product from '../../pages/product';
import Order from '../../pages/order';
import CreateShop from '../../pages/createShop';
import UpdateShop from '../../pages/updateShop';
import Logo from '../../logo.png';
import '../../css/layout.css';
import CreatProduct from '../../pages/createProduct';
import UpdateProduct from '../../pages/updateProduct';

const { Header, Content, Sider } = Layout;
const items1 = ['运营中心', '营销中心', '会员中心', '库存管理', '报表中心'].map((key) => ({
  key: key,
  label: key,
}));

function LayoutHome (){
  let navigate = useNavigate();
  return (
    <Layout>
    <div className='header'>
      <Header>
        <span><img src={Logo} alt='美团管家' onClick={()=>navigate('/')} style={{cursor:'pointer'}}/></span>
        <Menu mode="horizontal" defaultSelectedKeys={['1']} items={items1} />
        <div style={{marginLeft: 300}}>
          <Badge dot>
            <BellOutlined style={{fontSize: '150%', cursor: 'pointer'}}/>
          </Badge>
          <QuestionCircleOutlined style={{width: 70, fontSize: '150%', cursor: 'pointer'}}/>
        </div>
      </Header>
    </div>
    <Layout>
      <Sider collapsible className="sider-li sider-footer">
        <Menu
          mode="inline"
          defaultSelectedKeys={['1']}
          style={{
            height: '100%',
            borderRight: 0,
          }}
          items={[
            {
              key: '1',
              label: `门店管理`,
              onClick: ()=>{navigate('/shop');}
            },
            {
              key: '2',
              label: `菜品管理`,
              onClick: ()=>{navigate('/product');}
            },
            {
              key: '3',
              label: `订单管理`,
              onClick: ()=>{navigate('/order');}
            },
          ]}
          />
      </Sider>
      <Layout
        style={{
          padding: '1%',
          marginLeft: '3%',
        }}
      >
        <Content
          className="site-layout-background"
          style={{
            background: "white",
            border: "2px solid DimGrey",
            padding: 20,
            margin: 0,
            minHeight: '100%',
          }}
        >
          <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/shop" element={<Shop/>}/>
              <Route path="/order" element={<Order/>}/>
              <Route path="/product" element={<Product/>}/>
              <Route path="/createShop" element={<CreateShop/>}/>
              <Route path="/updateShop" element={<UpdateShop/>}/>
              <Route path='/createProduct' element={<CreatProduct/>}/>
              <Route path='/updateProduct' element={<UpdateProduct/>}/>
          </Routes>
        </Content>
      </Layout>
    </Layout>
  </Layout>
  )
};
export default LayoutHome;