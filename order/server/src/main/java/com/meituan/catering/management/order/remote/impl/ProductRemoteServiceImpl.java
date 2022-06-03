package com.meituan.catering.management.order.remote.impl;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.common.remote.BaseThriftRemoteService;
import com.meituan.catering.management.order.remote.ProductRemoteService;
import com.meituan.catering.management.order.remote.model.converter.ProductDetailRemoteResponseConverter;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;
import com.meituan.catering.management.product.api.thrift.model.response.ProductDetailListThriftResponse;
import com.meituan.catering.management.product.api.thrift.service.ProductThriftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Slf4j
@Service
public class ProductRemoteServiceImpl extends BaseThriftRemoteService implements ProductRemoteService {

    private ThreadLocal<ProductThriftService> productThriftService = ThreadLocal.withInitial(
            () -> super.buildConsoleThriftService(ProductThriftService.class, ProductThriftService.PORT));

    @Override
    public List<ProductDetailRemoteResponse> findByIds(Long tenantId, Long userId, Set<Long> ids) {
        UserContextThriftRequest userContextThriftRequest = new UserContextThriftRequest();
        userContextThriftRequest.setUserId(userId);
        userContextThriftRequest.setTenantId(tenantId);
        ProductDetailListThriftResponse response = productThriftService.get().findByIds(userContextThriftRequest, ids);
        if (response.getStatus().isFailed()) {
            throw new BizException(ErrorCode.SYSTEM_ERROR);
        }
        return ProductDetailRemoteResponseConverter.toProductDetailRemoteResponse(response.getData());
    }
}
