package com.meituan.catering.management.common.model.biz;

import lombok.Data;

import java.util.Date;

/**
 * 业务对象的审计信息
 */
@Data
public class AuditingBO {

    /**
     * 创建者ID
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 最后一次修改者ID
     */
    private Long lastModifiedBy;

    /**
     * 最后一次修改时间
     */
    private Date lastModifiedAt;
}
