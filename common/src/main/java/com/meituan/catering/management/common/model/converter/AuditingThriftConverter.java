package com.meituan.catering.management.common.model.converter;

import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.biz.AuditingBO;

/**
 * 从BO向远程调用的Thrift模型的转换器 - 审计信息
 */
public abstract class AuditingThriftConverter {

    public static AuditingThriftModel fromBO(AuditingBO auditingBO) {
        if (auditingBO == null) {
            return null;
        }
        AuditingThriftModel thriftModel = new AuditingThriftModel();
        thriftModel.setCreatedBy(auditingBO.getCreatedBy());
        thriftModel.setCreatedAtTimestamp(auditingBO.getCreatedAt() == null ? 0L : auditingBO.getCreatedAt().getTime());
        thriftModel.setLastModifiedBy(auditingBO.getLastModifiedBy());
        thriftModel.setLastModifiedAtTimestamp(auditingBO.getLastModifiedAt() == null ? 0L : auditingBO.getLastModifiedAt().getTime());
        return thriftModel;
    }
}
