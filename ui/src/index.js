import React from "react";
import ReactDOM from "react-dom";
import { RouterGuard } from "react-router-guard";
import config from "./config";
import "antd/dist/antd.css";
// import { CustomLoading } from './components';

import "./styles.css";

function App() {
  return (
    <div className="App">
      {/* <RouterGuard config={config} loading={CustomLoading} /> */}
      <RouterGuard config={config} />
    </div>
  );
}

const rootElement = document.getElementById("root"); // eslint-disable-line
ReactDOM.render(<App />, rootElement);
