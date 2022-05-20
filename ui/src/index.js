import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider } from "antd";
import cn from 'antd/es/locale/zh_CN';
// window.onbeforeunload = function (e) {
//   if (localStorage.getItem("isUpload") === 'yes') { //刷新时提示的条件
//     localStorage.setItem("isUpload", 'no');
//     const dialogText = "重新加载网页，系统可能不会保存您做的更改";
//     e.returnValue = dialogText;
//     return dialogText;
//   }
// };

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ConfigProvider locale={cn}>
    <BrowserRouter>
    <App />
    </BrowserRouter>
    </ConfigProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
