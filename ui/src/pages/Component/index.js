import React, {useState}from "react";
import { Popconfirm, message, Button, Radio, Select, Drawer, List, Card, Breadcrumb, Layout, Form, Menu, Row, Col, Input, Switch} from "antd";
import { EditOutlined, CloseOutlined, PlusOutlined} from '@ant-design/icons';
import {
  getComponentList,
  getComponentType,
} from "../../api/component/index";
const { Meta } = Card;
const { Content } = Layout;
const {Option} = Select;
const componentType = getComponentType();
const componentList = getComponentList();
let newComponentList = [{}, ...componentList];

function Home() {
  /*
  menu: 选中菜单数组
  checkUnit: 当前选中组件
  addDrawl, editDrawl: 新建&编辑模态框可见性
  select: 当前界面所展示组件

  2022/04/09
  */
  const [menu, setMenu] = useState(['alltype']); 
  const [checkUnit, setCheckUnit] = useState([]);
  const [addDrawl, setAddDrawl] = useState(false); 
  const [editDrawl, setEditDrawl] = useState(false);
  const [select, setSelect] = useState(newComponentList);

  const [form] = Form.useForm();
  form.setFieldsValue({unitName:checkUnit.name});
  form.setFieldsValue({unitType:checkUnit.type});
  const addUnit=()=>{
    setAddDrawl(true);
  };
  const editUnit=(value)=>{
    setCheckUnit(value);
    
    setEditDrawl(true);
  }
  const deleteUnit=(value)=>{
    setCheckUnit(value);
  }
  const onClose = () => {
    setEditDrawl(false);
    setAddDrawl(false);
  };
  /*根据菜单栏筛选出符合条件组件*/
  const checkSelect = (selectMenu)=>{
    let allIndex = selectMenu.indexOf("alltype");
    if((selectMenu.indexOf('alltype')===0) && (selectMenu.length>1)){
      selectMenu.shift();
      setMenu(selectMenu);
      console.log(selectMenu);
      return filter(selectMenu);
    }
    if (selectMenu.length != 0 && allIndex === -1) {
      setMenu(selectMenu);
      console.log(selectMenu);
      return filter(selectMenu);
    }
    if(selectMenu.indexOf('alltype')>=1){
      setMenu(['alltype']);
      return newComponentList;
    }
    setMenu(['alltype']);
    return newComponentList;
  }
  const filter=(selectMenu)=>{
    const temp = [];
      for (let i = 0; i < newComponentList.length; i++) {
        if (selectMenu.indexOf(newComponentList[i].type) != -1) {
          temp.push(newComponentList[i]);
        }
      }
      return temp;
  }
  /*回车事件*/
  const onPressEnter=(e)=>{ 
    const keyWords = e.target.value;
    const temp = [];
    //无输入回车直接显示当前选中或所有组件
    if(keyWords==""){
      setSelect(newComponentList);
      return;
    }
    for (let i = 1; i < newComponentList.length; i++) {
      //不搜索关键字长度大于
      if (keyWords.length<=newComponentList[i].name.length) {
        if (newComponentList[i].name.indexOf(keyWords)>=0){
          temp.push(newComponentList[i]);
        }
      }
    }
    setSelect(temp);
  }
  /*选中获取选中菜单*/
  const onSelect=values=>{
    setSelect(checkSelect(values.selectedKeys));
  }
  /*取消选中获取选中菜单*/
  const onDeselect=values=>{
    if(values.length===0){
      setMenu(['alltype']);
      setSelect(newComponentList);
    }
    setMenu(values.selectedKeys);
    setSelect(checkSelect(values.selectedKeys));
    
  }
  /*添加组件*/
  const onFinishAdd=values=>{
    const key = 'add';
    let newUnit = {name:values.unitName, type:values.unitType, state:{text:'已启动'}};
    newComponentList.push(newUnit);
    console.log(newComponentList.find((c)=>c.name===values.unitName).state.text);
    setAddDrawl(false);
    message.loading({ content: '添加中...', key });
    setTimeout(() => {
      message.success({ content: '添加成功!', key, duration: 2 });
    }, 1000);
  }
  /*编辑组件*/
  const onFinishEdit=values=>{
    const temp = checkUnit;
    const key = 'edit';
    temp.name = values.unitName;
    editComponent(checkUnit.id, temp);
    setEditDrawl(false);
    message.loading({ content: '修改中...', key });
    setTimeout(() => {
      message.success({ content: '组件修改成功!', key, duration: 2 });
    }, 500);
  }
  const confirm=()=>{
    deleteComponent(checkUnit.id);
    setSelect(menu.length!=0?checkSelect(menu):newComponentList);
    message.success('组件删除成功');
  }
  const cancel=(e)=>{
    console.log(e);
    message.error('组件取消删除');
  }
  const editStatus=(value)=>{
    setCheckUnit(value);
  }
  /*修改启动状态*/
  const confirmState=()=>{
    for(let i=0; i<newComponentList.length; i++){
      if(newComponentList[i].name===checkUnit.name){
        const status = newComponentList[i].state.text;
        newComponentList[i].state.text = status==="已启动"?"已停止":"已启动";
      }
    }
    const temp = [].concat(menu.length!=0?checkSelect(menu):newComponentList);
    setSelect(temp);
  }
  const editComponent = function (id, detail) {
    const target = newComponentList.find((c) => c.id === id);
    target.name = detail.name;
    target.shareCluster = detail.shareCluster;
    target.state = detail.state;
  };
  const deleteComponent = function (id) {
    newComponentList = newComponentList.filter((c) => c.id !== id);
  };
  return (
    
    <div>
      <header style={{ marginBottom: "10px" }}>
        <Card style={{ height: "180px" }}>
          <Breadcrumb separator=">">
            <Breadcrumb.Item href="/home">角色管理</Breadcrumb.Item>
            <Breadcrumb.Item href="/home/1">组件管理</Breadcrumb.Item>
          </Breadcrumb>

          <Content style={{ margin: "15px 0" }}>
            <Form
              name="base"
              labelCol={{ span: 0 }}
              wrapperCol={{ span: 2 }}
              autoComplete="off"
            >
              <Form.Item label="组件类型">
                <Menu
                  multiple={true}
                  mode="horizontal"
                  overflowedIndicator={false}
                  style={{ marginTop: "-8px" }}
                  onSelect={onSelect}
                  onDeselect={onDeselect}
                  selectedKeys={menu}
                >
                  <Menu.Item key="alltype">全部</Menu.Item>
                  {componentType.map((item) => {
                    return <Menu.Item  key={item.id}>{item.name}</Menu.Item>;
                  })}
                </Menu>
              </Form.Item>
              <Row>
                <Col>
                  <Form.Item label="其他选项"></Form.Item>
                </Col>
                <Col span={5}>
                  <Form.Item label="配置名称" labelCol={{ span: 8 }}>
                    <Input onPressEnter={onPressEnter} style={{ width: "300px" }} />
                  </Form.Item>
                </Col>
              </Row>
            </Form>
          </Content>
        </Card>
      </header>
      <List
       grid={{ gutter: 16, column: 4 }}
    dataSource={select}
    renderItem={(item, index)=>{
      if(item.name===undefined){
        return (
          <List.Item>
            <Card style={{height:"192.56px", textAlign:"center"}} hoverable={true} onClick={addUnit}><div style={{margin:60, color:"gray"}}><PlusOutlined/>新增组件</div></Card>
          </List.Item>
        )
      }
      return (
        <List.Item>
          <Card 

          title={<><span style={{border:"2px #a1a1a1",
            marginRight: 10,
            padding:"5px 16px",
            background:"#dddddd",
            "border-radius":"100px"
            }}></span><span >{item.name}</span></>}
          actions={[
            <EditOutlined key="edit" onClick={editUnit.bind(this,item)}/>,
            
            <Popconfirm
              title="确定删除此组件?"
              onConfirm={confirm}
              onCancel={cancel}
              okText="删除"
              cancelText="取消"
              onClick={deleteUnit.bind(this,item)}
            >
              <CloseOutlined key="delete"/>
            </Popconfirm>
          ]}
          >
            <Row>
              <Col span={14}>
                <Meta
                  description="组件类型"
                />
              </Col>
              <Col>
                <Meta
                  description="启动状态"/>
              </Col>
            </Row>
            <Row>
              <Col span={14}>
                <Meta
                  description={item.type}
                />
              </Col>
              <Col>
                <Popconfirm
                title={item.state.text==="已启动"?"确认停止?":"确认启动?"}
                onConfirm={confirmState}
                onCancel={cancel}
                okText={item.state.text==="已启动"?"停止":"启动"}
                cancelText="取消"
                onClick={editStatus.bind(this, item)}
              >
                <Switch checked={item.state.text==="已启动"?true:false}/>
                </Popconfirm>
              </Col>
            </Row>
          </Card>
        </List.Item>
      )
    }}
  />
      <Drawer title="新建网络组件" placement="right" onClose={onClose} visible={addDrawl} width='600'>
        <Form
        onFinish={onFinishAdd}
        >
            <Form.Item
            name="unitName"
            label="组件名称"
            rules={[
              {
                min:1,
                required: true,
                message: '请输入组件名称！',
                whitespace: true,
              },
            ]}
          >
            <Input/>
          </Form.Item>
          <Form.Item
            name="unitType"
            label="组件类型"
            rules={[
              {
                min:1,
                required: true,
                message: '请输入组件名称！',
                whitespace: true,
              },
            ]}
          >
            <Select style={{ width: 120 }}>
              {componentType.map((item)=>{
                return(
                  <Option defaultValue={item.name} key={item.id}>{item.name}</Option>
                )
              })}
            </Select>
          </Form.Item>
          <Form.Item
          name="config"
          rules={[
            {
              required: true,
              message:'请选择配置！'
            }
          ]}
          label="集群">
            <Radio.Group>
              <Radio value={1}>共享配置</Radio>
              <Radio value={2}>独立配置</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item>
            <Button onClick={()=>setAddDrawl(false)}>
              关闭
            </Button>
            <Button type="primary" htmlType="submit">
              保存
            </Button>
          </Form.Item>
        </Form>
      </Drawer>

      <Drawer title="编辑网络组件" placement="right" onClose={onClose} visible={editDrawl} width='600'>
        <Form
        initialValues={{
          type:checkUnit.type,
        }}
        form={form}
        onFinish={onFinishEdit}
        >
            <Form.Item
            name="unitName"
            label="组件名称"
            required
            rules={[
              {
                min:1,
                message: '请输入组件名称！',
                whitespace: true,
              },
            ]}
          >
            <Input/>
          </Form.Item>
          <Form.Item
            name="unitType"
            label="组件类型"
            required
          >
            <Select style={{ width: "100%" }} disabled>
              <Option value={checkUnit.type}>{checkUnit.type}</Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Button onClick={()=>setEditDrawl(false)}>
              关闭
            </Button>
            <Button type="primary" htmlType="submit">
              保存
            </Button>
          </Form.Item>
        </Form>
      </Drawer>
    </div>
    
  );
}

export default Home;
