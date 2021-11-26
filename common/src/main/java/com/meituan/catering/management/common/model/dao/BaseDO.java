package com.meituan.catering.management.common.model.dao;

import lombok.Data;

import java.util.Date;

/**
 * 所有MyBatis的DO实例基类
 */
@Data
public abstract class BaseDO {

    /**
     * 物理ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 乐观锁版本号
     */
    private Integer version;

    /**
     * 创建者UserId
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 最后修改者UserId
     */
    private Long lastModifiedBy;

    /**
     * 最后修改时间
     */
    private Date lastModifiedAt;
}
