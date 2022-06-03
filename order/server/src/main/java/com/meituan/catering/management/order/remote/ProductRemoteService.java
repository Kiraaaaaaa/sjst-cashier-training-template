package com.meituan.catering.management.order.remote;

import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;

import java.util.List;
import java.util.Set;

public interface ProductRemoteService {

    List<ProductDetailRemoteResponse> findByIds(Long tenantId, Long userId, Set<Long> ids);

}
