package com.meituan.catering.management.product.dao.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品加料DO定义
 */
@Data
public class ProductAccessoryDO {

    /**
     * 物理id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 用户id
     */
    private Long tenantId;

    /**
     * 加料名
     */
    private String name;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 单位金额
     */
    private BigDecimal unitPrice;

    /**
     * 单位
     */
    private String unitOfMeasure;

}
