package com.meituan.catering.management.common.model.biz;

import lombok.Data;

/**
 * 所有业务BO实例基类
 */
@Data
public abstract class BaseBO {

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
     * 审计信息BO
     */
    private AuditingBO auditing;
}
