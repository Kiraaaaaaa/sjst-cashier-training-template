import React from "react";
import { Row, Col } from "antd";
import { useNavigate } from "react-router-dom";

export default function Home(){
    
    let navigate = useNavigate();

    return(
        <>
        <p><strong>welcome</strong></p>
        <p>
            <button onClick={()=>{
                navigate("/shop")
            }}>跳转到门店</button>
            <button onClick={()=>{
                navigate("/product")
            }}>跳转到菜品</button>
            <button onClick={()=>{
                navigate("/order")
            }}>跳转到订单</button>
            
        </p>
        </>
    )
}