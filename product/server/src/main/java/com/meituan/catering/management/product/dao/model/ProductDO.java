package com.meituan.catering.management.product.dao.model;

import com.meituan.catering.management.common.model.dao.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 商品DO定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDO extends BaseDO {

    /**
     * 商品名
     */
    private String name;

    /**
     * 单位金额
     */
    private BigDecimal unitPrice;

    /**
     * 单位
     */
    private String unitOfMeasure;

    /**
     * 最小销售数量
     */
    private BigDecimal minSalesQuantity;

    /**
     * 每次增加数量
     */
    private BigDecimal increaseSalesQuantity;

    /**
     * 商品介绍
     */
    private String description;

    /**
     * 商品启停状态
     */
    private Boolean enabled;


}