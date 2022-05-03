package com.meituan.catering.management.order.remote.model.converter;

import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import springfox.documentation.spring.web.json.Json;

public class ShopDetailRemoteResponseConverter {
    public static ShopDetailRemoteResponse toShopDetailRemoteResponse(ShopDetailThriftResponse shopDetailThriftResponse) {
        System.out.println(shopDetailThriftResponse);
        return null;
    }
}
