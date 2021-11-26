package com.meituan.catering.management.common.model.api.thrift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.Data;

/**
 * 联系方式DTO
 */
@Data
@ThriftStruct
public final class ContactThriftModel {

    @ThriftField(1)
    public String telephone;

    @ThriftField(2)
    public String cellphone;

    @ThriftField(3)
    public String name;

    @ThriftField(4)
    public String address;
}
