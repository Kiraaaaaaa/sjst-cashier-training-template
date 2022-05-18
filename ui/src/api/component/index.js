import { componentType, componentList } from './data.js';

// 组件分类
const getComponentType = function () {
  return componentType;
};

// 组件列表
const getComponentList = function () {
  return componentList;
};

// 新增组件
const addComponent = function (data) {
  componentList.push(data);
};

// 编辑组件
const editComponent = function (id, detail) {
  const target = componentList.find((c) => c.id === id);
  target.name = detail.name;
  target.shareCluster = detail.shareCluster;
  target.state = detail.state;
};

// 删除组件
const deleteComponent = function (id) {
  componentList = componentList.filter((c) => c.id !== id);
};

// getComponentType: 组件管理 => 组件类型
// getComponentType: 组件管理 => 组件列表

export {
  getComponentList,
  deleteComponent,
  getComponentType,
  addComponent,
  editComponent,
};
