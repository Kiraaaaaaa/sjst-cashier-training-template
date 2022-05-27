package com.meituan.catering.management.product.dao.model;

import lombok.Data;

/**
 * 商品做法DO定义
 */
@Data
public class ProductMethodDO {
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

}
