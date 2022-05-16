import React, {useState, useEffect}from 'react';
import {  Radio, Popconfirm, Typography, Table, Card, Breadcrumb, Layout, Form, Input, Row, Col, Button, Modal, Menu } from 'antd';
import { getAuthList } from '../../api/auth/index';
import { PlusOutlined } from '@ant-design/icons';
const { Content } = Layout;

function NewService1() {
  let Authlist = getAuthList();
  /*新增默认权限列表*/
  const defaultAuth = [
    {action:'query',name:'查询列表',describe:'查询列表'},
    {action:'get',name:'查询明细',describe:'查询明细'},
    {action:'add',name:'新增',describe:'新增'},
    {action:'update',name:'修改',describe:'修改'},
    {action:'delete',name:'删除',describe:'删除'},
    {action:'import',name:'导入',describe:'导入'},
    {action:'export',name:'导出',describe:'导出'},
  ]
  const [form]= Form.useForm();
  const [editForm] = Form.useForm();

  const isEditing = (record) => record.name === editingName;
  /*
  nowList: 所展示数据，根据搜索条件变化
  modalVisible: 模态框的可见性
  editDate: 正在被编辑的数据
  modalTitle: 动态显示模态框类型
  editingName: 权限选项是否可被点击
  */
  const [nowList, setNowList] = useState(Authlist);
  const [modalVisible, setModalVisible] = useState(false);
  const [editData, setEditData] = useState({});
  const [editAction, setEditAction] = useState({});
  const [modalTitle, setModalTitle] = useState('');
  const [editingName, setEditingName] = useState("");
  
  const columns=[
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: '名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (text)=>{
        return <p>{text===1?"启用":"禁用"}{console.log(text)}</p>
      }
    },
    {
      title: '操作',
      dataIndex: '',
      key: 'action',
      render: (text, recode, index) => (
        <div style={{margin: -15}}>
          <Button type="link" onClick={() => showAuthModal(text)}>
            编辑
          </Button>
          <span style={{color:'lightgray'}}>|</span>
          <Button
            type="link"
            onClick={() => {
              deletAuth(text.id);
            }}
          >
            删除
          </Button>
        </div>
      ),
    },
  ];
  /*权限修改输入框*/
  const EditableCell = ({
    editing,
    dataIndex,
    title,
    record,
    index,
    children,
    ...restProps
  }) => {
    return (
      <td {...restProps} >
        {editing ? (
          <Form.Item
            name={dataIndex}
            style={{
              margin: 0,
            }}
            rules={
              dataIndex !== "describe"
                ? [
                    {
                      required: true,
                      message: `请输入 ${title}!`,
                    },
                  ]
                : ""
            }
          >
            <Input />
          </Form.Item>
        ) : (
          children
        )}
      </td>
    );
  };
  /*获取编辑对象*/
  useEffect(() => {
    editForm.setFieldsValue({
      id: editData.id,
      name: editData.name,
      status: editData.status,
    });
  }, [editData]);
  const deleAction = (index) => {
    setEditAction(editAction.filter((Item, x) => x !== index));
  };
  
  const edit = (record) => {
    editForm.setFieldsValue({
      action: "",
      name: "",
      describe: "",
      ...record,
    });
    setEditingName(record.name);
  };
  const addEdit = () => {
    const temp = [{ action: "", name: "", describe: "" }, ...editAction];
    setEditAction(temp);
    edit({ action: "", name: "", describe: "" });
  };
  const configList=[
    {
      title: '操作类型',
      dataIndex: 'action',
      key: 'action',
      editable: true,
    },
    {
      title: '名称',
      dataIndex: 'name',
      key: 'name',
      editable: true,
    },
    {
      title: '描述',
      dataIndex: 'describe',
      key: 'describe',
      editable: true,
    },
    {
      title: (
        <>
          <span>操作</span> &nbsp;|
          <Button
            icon={<PlusOutlined />}
            style={{ border: "0px", background: "#fafafa" }}
            onClick={addEdit}
          />
        </>
      ),
      dataIndex: 'actions',
      key: 'actions',
      render: (_, record, index) => {
        const editable = isEditing(record);
        return editable ? (
          <span>
            <Typography.Link
              onClick={() => saveEdit(record.name)}
              style={{
                marginRight: 8,
              }}
            >
              保存
            </Typography.Link>
            <Popconfirm title="确认取消？" onConfirm={cancelEdit}>
              <a>取消</a>
            </Popconfirm>
          </span>
        ) : (
          <>
            <Typography.Link
              disabled={editingName !== ""}
              onClick={() => edit(record)}
            >
              编辑
            </Typography.Link>
            &nbsp;|&nbsp;
            <Typography.Link
              onClick={() => {
                deleAction(index);
              }}
            >
              删除
            </Typography.Link>
          </>
        );
      },
    },
  ];
  /*取消编辑*/
  const cancelEdit = () => {
    const t = editAction.filter((item) => item.name !== "");
    setEditAction(t);
    setEditingName("");
  };
  /*设置编辑模态框*/
  const showAuthModal = (value) => {
    setModalTitle("编辑权限");
    setEditData(value);
    setEditAction(value.actions);
    setModalVisible(true);
  };
  /*保存权限编辑内容*/
  const saveEdit = async (name) => {
    try {
      const row = await editForm.validateFields();
      console.log(row);
      const newData = [...editAction];
      const index = newData.findIndex((item) => name === item.name);
      const item = newData[index];
      newData.splice(index, 1, { ...item, ...row });
      setEditAction(newData);
      setEditingName("");
    } catch (errInfo) {
      console.log("Validate Failed:", errInfo);
    }
  };
  /*设置新建权限模态框*/
  const newAuth = () => {
    setModalTitle("新建权限");
    setEditAction(defaultAuth);
    setModalVisible(true);
  };
  /*混合显示*/
  const mergedColumns = configList.map((col) => {
    if (!col.editable) {
      return col;
    }
    return {
      ...col,
      onCell: (record) => ({
        record,
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    };
  });
  const reset=()=>{
    form.setFieldsValue({id:undefined, name:undefined});
  }
  /*模糊查询*/
  const onSearch = React.useCallback((values)=>{
    let [id, name]= [values.id, values.name];
    let temp = [];
    if(!id && !name){
      setNowList(Authlist);
      return;
    }
    if(id && name){
      for (let i = 0; i < Authlist.length; i++) {
        if ((id.length<=Authlist[i].id.length)&&(name.length<=Authlist[i].name.length)) {
          if ((Authlist[i].id.indexOf(id)>=0&&(Authlist[i].name.indexOf(name)>=0))){
            temp.push(Authlist[i]);
          }
        }
      }
    }
    if(id && !name){
      for (let i = 0; i < Authlist.length; i++) {
        if (id.length<=Authlist[i].id.length) {
          if (Authlist[i].id.indexOf(id)>=0){
            temp.push(Authlist[i]);
          }
        }
      }
    }else if(!id && name){
      for (let i = 0; i < Authlist.length; i++) {
        if (name.length<=Authlist[i].name.length) {
          if (Authlist[i].name.indexOf(name)>=0){
            temp.push(Authlist[i]);
          }
        }
      }
    }
    setNowList(temp);
  }, [nowList]);
  /*新建权限保存后执行*/
  const onEditFinish=(value)=>{
    const temp = value;
    temp.actions = editAction;
    if (modalTitle === "新建权限") {
      const data = [temp, ...nowList];
      setNowList(data);
    } else {
      const data = [...nowList];
      const index = data.findIndex((c) => c.id === temp.id);
      data[index] = temp;
      console.log(data[index]);
      setNowList(data);
    }
    setEditData({});
    setModalVisible(false);
  }
  const handleEditCancel = () => {
    setEditAction({});
    setEditData({});
    setModalVisible(false);
  };
  const deletAuth = (id) => {
    setNowList(nowList.filter((c) => c.id !== id));
  };

  return (
    <Card>
      <Breadcrumb separator=">">
        <Breadcrumb.Item href="/services">角色管理</Breadcrumb.Item>
        <Breadcrumb.Item href="/services/1">权限管理</Breadcrumb.Item>
      </Breadcrumb>

      <Content style={{ margin: '15px 0' }}>
        <Form
        form={form}
        onFinish={onSearch}
        >
          <Row>
            <Col>
              <Form.Item
              name="id"
              label="ID:"
              >
                <Input style={{width:400}} placeholder="请输入ID"/>
              </Form.Item>
            </Col>
            <Col offset={1}>
              <Form.Item
              name="name"
              label="名称:">
                <Input style={{width:400}} placeholder="请输入名称"/>
              </Form.Item>
            </Col>
            <Col offset={3}>
              <Form.Item>
                <Button type='primary' htmlType='submit'>查询</Button>
                <Button onClick={reset}>重置</Button>
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Button type='primary' onClick={newAuth}><PlusOutlined/>新建</Button>
          </Row>
        </Form>
        <Table columns={columns} dataSource={nowList} pagination={{
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total) => `总共${total}项`,
          }}/>
  
        <Modal
        centered
        title={modalTitle} 
        visible={modalVisible} 
        width={900}
        onOk={() => editForm.submit()}
        onCancel={handleEditCancel} 
        okText="确定"
        cancelText="取消"
        >
          <Menu
          multiple={true}
          mode="horizontal"
          overflowedIndicator={false}
          selectedKeys={['index']}
          >
            <Menu.Item key={'index'}>基本信息</Menu.Item>
          </Menu>
          <Form
          size='small'
          form={editForm}
          onFinish={onEditFinish}
          >
            <Row>
              <Col offset={2}>
                <Form.Item
                name="id"
                label="权限标识(ID):"
                rules={[
                  { required: true, message: "请输入权限标识！" },
                ]}
                >
                  <Input/>
                </Form.Item>
              </Col>
              <Col offset={1}>
                <Form.Item
                name="name"
                label="权限名称:"
                rules={[{ required: true, message: "请输入权限名称！" }]}
                >
                  <Input width={30}/>
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col span={4} offset={8}>
              <Form.Item
                  label="状态"
                  name="status"
                  rules={[{ required: true, message: "请选择状态！" }]}
                >
                  <Radio.Group buttonStyle="solid">
                    <Radio.Button value={1}>启动</Radio.Button>
                    <Radio.Button value={0}>禁止</Radio.Button>
                  </Radio.Group>
                </Form.Item>
              </Col>
            </Row>
            <Table
            title={() => '操作配置'}
            components={{
              body: {
                cell: EditableCell,
              },
            }}
            dataSource={editAction}
            columns={mergedColumns}
            pagination={false}
            bordered
                >
            </Table>
          </Form>
        </Modal>
      </Content>
    </Card>
  );
}

export default NewService1;
