package com.meituan.catering.management.order.api.http.model.enumeration;

import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 订单支付方式
 */
@Getter
@RequiredArgsConstructor
public enum CateringOrderPaymentChannelEnum implements DescribableEnum {

    /**
     * 支付宝
     */
    ALIPAY(100, "支付宝"),
    /**
     * 微信支付
     */
    WECHAT(200, "微信支付"),

    ;

    private final int code;

    private final String name;

}
