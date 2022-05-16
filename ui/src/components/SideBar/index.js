import React from 'react';
import { Menu, Icon } from 'antd';
import { Link } from 'react-router-guard';

const { SubMenu } = Menu;

function SideBar(props) {
  return (
    <Menu mode="inline" theme="dark">
      <SubMenu key="/component" title="组件管理">
        <Menu.Item key="/component/1">
          <Link to="/component/1">组件列表</Link>
        </Menu.Item>
      </SubMenu>

      <SubMenu key="/auths" title="权限管理">
        <Menu.Item key="/auths/1">
          <Link to="/auths/1">权限列表</Link>
        </Menu.Item>
      </SubMenu>
    </Menu>
  );
}

export default SideBar;
