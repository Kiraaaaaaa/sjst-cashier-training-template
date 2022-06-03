package com.meituan.catering.management.order.dao.model;

import com.meituan.catering.management.common.model.dao.BaseDO;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 订单DO定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CateringOrderDO extends BaseDO {

    /**
     * 订单状态
     */
    private CateringOrderStatusEnum status;

    /**
     * 门店id
     */
    private Long shopId;

    /**
     * 门店业务号
     */
    private String shopBusinessNo;

    /**
     * 就餐门店id
     */
    private String shopNameOnPlace;

    /**
     * 桌位号
     */
    private String tableNo;

    /**
     * 顾客数量
     */
    private String customerCount;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String comment;

    /**
     * 账单优惠
     */
    private BigDecimal billingPromotion;

    /**
     * 账单支付
     */
    private BigDecimal billingPaid;

    /**
     * 支付渠道
     */
    private CateringOrderPaymentChannelEnum billingPaymentChannel;

}