package com.meituan.catering.management.common.model.api.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.Data;

/**
 * 业务对象的审计信息DTO
 */
@Data
@ThriftStruct
public final class AuditingThriftModel {

    @ThriftField(1)
    public Long createdBy;

    @ThriftField(2)
    public Long createdAtTimestamp;

    @ThriftField(3)
    public Long lastModifiedBy;

    @ThriftField(4)
    public Long lastModifiedAtTimestamp;
}
