package com.meituan.catering.management.common.model.api.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import lombok.Data;

/**
 * 可描述的枚举DTO
 */
@Data
@ThriftStruct
public final class DescribableEnumThriftModel {

    @ThriftField(1)
    public Integer code;

    @ThriftField(2)
    public String name;

    public void from(DescribableEnum describableEnum) {
        if (describableEnum == null) {
            return;
        }
        this.code = describableEnum.getCode();
        this.name = describableEnum.getName();
    }
}
