package com.meituan.catering.management.order.api.http.model.enumeration;

import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 订单主状态
 */
@Getter
@RequiredArgsConstructor
public enum CateringOrderStatusEnum implements DescribableEnum {

    /**
     * 已取消
     */
    CANCELLED(-100, "已取消"),

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
     * 已结账
     */
    BILLED(400, "已结账"),


    /**
     * 待下单
     */
    DRAFT(500, "待下单"),
    ;

    private final int code;

    private final String name;

    public static CateringOrderStatusEnum getEnum(int code){
        for (CateringOrderStatusEnum value : CateringOrderStatusEnum.values()) {
            if (Objects.equals(value.getCode(),code)){
                return value;
            }
        }
        return null;
    }

}
