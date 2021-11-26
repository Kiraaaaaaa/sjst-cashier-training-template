package com.meituan.catering.management.common.model.api.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 操作用户的上下文
 */
@Data
@Valid
@ThriftStruct
public final class UserContextThriftRequest {

    @ThriftField(1)
    @NotNull
    @Min(100)
    @Max(999)
    public Long tenantId;

    @ThriftField(2)
    @NotNull
    @Min(10000)
    @Max(99999)
    public Long userId;
}
