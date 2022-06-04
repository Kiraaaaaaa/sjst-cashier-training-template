import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider, Empty } from "antd";
import cn from 'antd/es/locale/zh_CN';

const root = ReactDOM.createRoot(document.getElementById('root'));
const renderEmpty = () => (
  <Empty
      image="https://gw.alipayobjects.com/zos/antfincdn/ZHrcdLPrvN/empty.svg"
      imageStyle={{
          height: 256,
      }}
      description={
          <span>
              暂无数据，试试<a onClick={()=>{window.location.reload()}}>重新加载</a>本页面?
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
reportWebVitals();
