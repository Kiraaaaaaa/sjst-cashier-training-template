package com.meituan.catering.management.shop.api.thrift.service;

import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ShopThriftServiceImpl implements ShopThriftService {

    @Override
    public ShopDetailThriftResponse findByBusinessNo(
            UserContextThriftRequest userContext, String businessNo) {
        return null;
    }

    @Override
    public List<ShopDetailThriftResponse> findByBusinessNoList(UserContextThriftRequest userContext, Set<String> businessNoList) {
        return null;
    }
}
