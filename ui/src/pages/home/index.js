import React from "react";
import { Row, Col, Button } from "antd";
import { useNavigate } from "react-router-dom";

export default function Home(){
    
    let navigate = useNavigate();

    return(
        <>
        <p><strong>welcome</strong></p>
        <p>
            <Button onClick={()=>{
                navigate("/shop")
            }}>跳转到门店</Button>
            <Button onClick={()=>{
                navigate("/product")
            }}>跳转到菜品</Button>
            <Button onClick={()=>{
                navigate("/order")
            }}>跳转到订单</Button>
            
        </p>
        </>
    )
}