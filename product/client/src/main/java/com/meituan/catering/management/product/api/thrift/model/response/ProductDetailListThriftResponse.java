package com.meituan.catering.management.product.api.thrift.model.response;

import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.product.api.thrift.model.dto.ProductDetailThriftDTO;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/1 9:51
 * @ClassName: ProductDetailListThriftResponse
 */

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@ThriftStruct
public final class ProductDetailListThriftResponse extends BaseResponse<List<ProductDetailThriftDTO>> {
}
