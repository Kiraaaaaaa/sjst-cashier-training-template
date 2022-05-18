import { auths } from './data.js';

// 权限列表
const getAuthList = function () {
  return auths;
};

// 编辑权限
const editAuth = function (id, detail) {
  const copiedAuth = [...auths];
  const target = auths.findIndex((c) => c.id === id);
  if (copiedAuth[target]) {
    copiedAuth[target] = detail;
  }
};

// 新增权限
const addAuth = function (newAuth) {
  auths.unshift(newAuth);
};

export { getAuthList, editAuth, addAuth };
