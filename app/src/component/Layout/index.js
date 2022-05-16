import { Button, Layout, Menu} from 'antd';
import MenuItem from 'antd/lib/menu/MenuItem';
import React from 'react';
import { Link, Route, Routes, useNavigate } from 'react-router-dom';
import Home from '../../pages/home';
import Shop from '../../pages/shop';
import Food from '../../pages/food';
import Order from '../../pages/order';
import CreateShop from '../../pages/createShop';
import Logo from '../../../../app/src/logo.png';
import './index.css';


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
        {/* <span><img src={Logo}/></span> */}
        <Menu mode="horizontal" defaultSelectedKeys={['1']} items={items1} />
      </Header>
    </div>
    <Layout>
      <Sider width={200} collapsible className="sider-li sider-footer">
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
              onClick: ()=>{navigate('/food');}
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
          padding: '20px',
          
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
              <Route path="/food" element={<Food/>}/>
              <Route path="/order" element={<Order/>}/>
              <Route path="/createShop" element={<CreateShop/>}/>
          </Routes>
        </Content>
      </Layout>
    </Layout>
  </Layout>
  )
};
export default LayoutHome;