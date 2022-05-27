import { useNavigate } from "react-router-dom";

export default function Home(){
    let navigate = useNavigate();

    return(
        <p>
            <button onClick={()=>{
                navigate("/shop")
            }}>跳转到门店</button>
            <button onClick={()=>{
                navigate("/food")
            }}>跳转到菜品</button>
            <button onClick={()=>{
                navigate("/order")
            }}>跳转到订单</button>
            
        </p>
    )
}