package com.meituan.catering.management.order.api.http.model.enumeration;

import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 订单子项状态
 */
@Getter
@RequiredArgsConstructor
public enum CateringOrderItemStatusEnum implements DescribableEnum {

    /**
     * 已下单
     */
    PLACED(100, "已下单"),
    /**
     * 制作中
     */
    PREPARING(200, "制作中"),
    /**
     * 已出餐
     */
    PREPARED(300, "已出餐"),
    /**
     * 已取消
     */
    CANCELLED(-100, "已取消"),

    ;

    private final int code;

    private final String name;

}
