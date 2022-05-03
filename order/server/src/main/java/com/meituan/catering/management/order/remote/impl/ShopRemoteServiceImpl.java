package com.meituan.catering.management.order.remote.impl;

import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.common.remote.BaseThriftRemoteService;
import com.meituan.catering.management.order.remote.ShopRemoteService;
import com.meituan.catering.management.order.remote.model.converter.ShopDetailRemoteResponseConverter;
import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import com.meituan.catering.management.shop.api.thrift.service.ShopThriftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class ShopRemoteServiceImpl extends BaseThriftRemoteService implements ShopRemoteService {

    private ThreadLocal<ShopThriftService> shopThriftService = ThreadLocal.withInitial(
            () -> super.buildConsoleThriftService(ShopThriftService.class, ShopThriftService.PORT));


    @Override
    public ShopDetailRemoteResponse findByBusinessNo(Long tenantId,Long userId,String businessNo) {
        UserContextThriftRequest userContextThriftRequest=new UserContextThriftRequest();
        userContextThriftRequest.setUserId(userId);
        userContextThriftRequest.setTenantId(tenantId);
        ShopDetailThriftResponse shopDetailThriftResponse = shopThriftService.get().findByBusinessNo(userContextThriftRequest, businessNo);

        return ShopDetailRemoteResponseConverter.toShopDetailRemoteResponse(shopDetailThriftResponse);
    }
}
