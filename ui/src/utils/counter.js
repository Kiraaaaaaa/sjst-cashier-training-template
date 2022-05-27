import { useState, useEffect } from "react";
/** 骨架屏计时器(限制搜索过程中没有物品时骨架屏所显示时间1s) */
function useCounter() {
    const [count, setCount] = useState(0); // 计数
    useEffect(() => {
      const timer = setTimeout(() => {
        count<=1 && setCount(count + 1); // 时间小于2s，计数+1，否则暂停计数器
      }, 1000);
      return () => clearTimeout(timer); // 组件销毁前和更新前，清理 timer
    }, [count]); // 监听依赖列表
    // 重新搜索时重置计数
    const reset = () => setCount(0);

    return [count, reset]; // 把需要的暴露出去（API）
}
export default useCounter;