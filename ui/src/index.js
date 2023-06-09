import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider, Empty, Button } from "antd";
import cn from 'antd/es/locale/zh_CN';

const root = ReactDOM.createRoot(document.getElementById('root'));
const renderEmpty = () => (
  <Empty
      image="https://gw.alipayobjects.com/zos/antfincdn/ZHrcdLPrvN/empty.svg"
      imageStyle={{
          height: 60,
      }}
      description={
          <span>
              暂无数据，试试<a onClick={()=>{window.location.reload()}}>重新加载</a>?
          </span>
      }
  >
  </Empty>
)
root.render(
  <React.StrictMode>
    <ConfigProvider locale={cn} renderEmpty={renderEmpty}>
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
