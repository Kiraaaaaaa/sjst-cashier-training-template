package com.meituan.catering.management.product.api.thrift.model.response;

import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.product.api.thrift.model.dto.ProductDetailThriftDTO;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品详情的Thrift返回体定义
 * @author mac
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@ThriftStruct
public final class ProductDetailThriftResponse extends BaseResponse<ProductDetailThriftDTO> {

}