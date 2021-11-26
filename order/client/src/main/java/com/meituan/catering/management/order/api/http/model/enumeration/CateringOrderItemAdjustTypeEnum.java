package com.meituan.catering.management.order.api.http.model.enumeration;

import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 订单子项调整类型
 */
@Getter
@RequiredArgsConstructor
public enum CateringOrderItemAdjustTypeEnum implements DescribableEnum {

    /**
     * 加菜
     */
    ADD(10, "加菜"),

    /**
     * 退菜
     */
    REMOVE(20, "退菜"),

    ;

    private final int code;

    private final String name;

}
