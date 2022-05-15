package com.meituan.catering.management.order.remote.model.converter;

import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import com.meituan.catering.management.shop.api.thrift.model.dto.ShopDetailThriftDTO;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import springfox.documentation.spring.web.json.Json;

/**
 * @author mac
 */
public class ShopDetailRemoteResponseConverter {
    public static ShopDetailRemoteResponse toShopDetailRemoteResponse(ShopDetailThriftDTO shopDetailThriftDTO) {
        System.out.println(shopDetailThriftDTO);
        return null;
    }
}
