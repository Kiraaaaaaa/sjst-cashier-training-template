package com.meituan.catering.management.shop.api.thrift.model.response;

import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.shop.api.thrift.model.dto.ShopDetailThriftDTO;
import lombok.*;

/**
 * 门店详情的Thrift返回体
 * @author mac
 */



@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@ThriftStruct
public final class ShopDetailThriftResponse extends BaseResponse<ShopDetailThriftDTO> {
}