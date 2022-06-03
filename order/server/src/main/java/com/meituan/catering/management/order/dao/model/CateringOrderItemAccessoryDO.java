package com.meituan.catering.management.order.dao.model;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单子项加料DO定义
 */
@Data
public class CateringOrderItemAccessoryDO {

    /**
     * 物理id
     */
    private Long id;

    /**
     * 租户号
     */
    private Long tenantId;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 订单子项id
     */
    private Long orderItemId;

    /**
     * 序号
     */
    private String seqNo;

    /**
     * 状态
     */
    private CateringOrderItemAccessoryStatusEnum status;

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
     * 商品小料id
     */
    private Long productAccessoryId;

    /**
     * 商品小料名
     */
    private String productAccessoryNameOnPlace;

    /**
     * 商品小料组名
     */
    private String productAccessoryGroupNameOnPlace;

    /**
     * 商品小料单价
     */
    private BigDecimal productAccessoryUnitPriceOnPlace;

    /**
     * 商品小料单位
     */
    private String productAccessoryUnitOfMeasureOnPlace;

}