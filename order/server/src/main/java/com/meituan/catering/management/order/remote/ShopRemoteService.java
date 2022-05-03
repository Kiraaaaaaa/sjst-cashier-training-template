package com.meituan.catering.management.order.remote;

import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;

public interface ShopRemoteService {
    ShopDetailRemoteResponse findByBusinessNo(Long tenantId,Long userId,String businessNo);
}
