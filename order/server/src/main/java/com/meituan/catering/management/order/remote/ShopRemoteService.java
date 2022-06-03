package com.meituan.catering.management.order.remote;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.order.remote.model.request.ShopPageRemoteRequest;
import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import com.meituan.catering.management.order.remote.model.response.ShopPageRemoteResponse;

public interface ShopRemoteService {
    ShopDetailRemoteResponse findByBusinessNo(Long tenantId,Long userId,String businessNo) throws BizException;
}
