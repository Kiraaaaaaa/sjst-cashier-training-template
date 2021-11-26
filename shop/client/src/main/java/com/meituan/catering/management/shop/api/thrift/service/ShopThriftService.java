package com.meituan.catering.management.shop.api.thrift.service;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.meituan.catering.management.common.api.thrift.service.ConsoleThriftService;
import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;

import java.util.List;
import java.util.Set;

@ThriftService("ShopThriftService")
public interface ShopThriftService extends ConsoleThriftService {

    int PORT = 9202;

    @ThriftMethod
    ShopDetailThriftResponse findByBusinessNo(UserContextThriftRequest userContext, String businessNo);

    @ThriftMethod
    List<ShopDetailThriftResponse> findByBusinessNoList(UserContextThriftRequest userContext, Set<String> businessNoList);
}