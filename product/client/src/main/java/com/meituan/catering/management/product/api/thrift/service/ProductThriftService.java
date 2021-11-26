package com.meituan.catering.management.product.api.thrift.service;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.meituan.catering.management.common.api.thrift.service.ConsoleThriftService;
import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.product.api.thrift.model.response.ProductDetailThriftResponse;

import java.util.List;
import java.util.Set;

@ThriftService("ProductThriftService")
public interface ProductThriftService extends ConsoleThriftService {

    int PORT = 9201;

    @ThriftMethod
    ProductDetailThriftResponse findById(UserContextThriftRequest userContext, Long id);

    @ThriftMethod
    List<ProductDetailThriftResponse> findByIds(UserContextThriftRequest userContext, Set<Long> idList);
}