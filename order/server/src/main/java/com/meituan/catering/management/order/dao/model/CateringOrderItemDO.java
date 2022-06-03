package com.meituan.catering.management.order.dao.model;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单子项DO定义
 */
@Data
public class CateringOrderItemDO {

    /**
     * 物理id
     */
    private Long id;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 序号
     */
    private String seqNo;

    /**
     * 订单子项状态
     */
    private CateringOrderItemStatusEnum status;

    /**
     * 已下单数量
     */
    private BigDecimal placeQuantity;

    /**
     * 制作中数量
     */
    private BigDecimal produceQuantity;

    /**
     * 剩余数量
     */
    private BigDecimal latestQuantity;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 当前产品名
     */
    private String productNameOnPlace;

    /**
     * 当前产品单价
     */
    private BigDecimal productUnitPriceOnPlace;

    /**
     * 当前产品单位
     */
    private String productUnitOfMeasureOnPlace;

    /**
     * 制作方法id
     */
    private Long productMethodId;

    /**
     * 当前制作方法名
     */
    private String productMethodNameOnPlace;

    /**
     * 当前制作方法分组
     */
    private String productMethodGroupNameOnPlace;
}