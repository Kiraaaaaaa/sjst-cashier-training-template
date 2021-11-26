package com.meituan.catering.management.common.model.converter;

import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.biz.AuditingBO;

import java.util.Date;

/**
 * 从远程调用的Thrift模型向BO的转换器 - 审计信息
 */
public abstract class AuditingRemoteConverter {

    public static AuditingBO fromRemoteResponse(AuditingThriftModel thriftModel) {
        if (thriftModel == null) {
            return null;
        }
        AuditingBO bo = new AuditingBO();
        bo.setCreatedBy(thriftModel.getCreatedBy());
        bo.setCreatedAt(new Date(thriftModel.getCreatedAtTimestamp()));
        bo.setLastModifiedBy(thriftModel.getLastModifiedBy());
        bo.setLastModifiedAt(new Date(thriftModel.getLastModifiedAtTimestamp()));
        return bo;
    }
}
