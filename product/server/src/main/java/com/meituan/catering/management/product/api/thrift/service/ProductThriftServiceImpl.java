package com.meituan.catering.management.product.api.thrift.service;

import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.product.api.thrift.model.response.ProductDetailThriftResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductThriftServiceImpl implements ProductThriftService {

    @Override
    public ProductDetailThriftResponse findById(UserContextThriftRequest userContext, Long id) {
        return null;
    }

    @Override
    public List<ProductDetailThriftResponse> findByIds(UserContextThriftRequest userContext, Set<Long> idList) {
        return null;
    }
}
