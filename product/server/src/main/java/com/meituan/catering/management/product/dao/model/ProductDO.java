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

    private String name;

    private BigDecimal unitPrice;

    private String unitOfMeasure;

    private BigDecimal minSaleQuantity;

    private BigDecimal increaseSalesQuantity;

    private String description;

    private Boolean enabled;


}