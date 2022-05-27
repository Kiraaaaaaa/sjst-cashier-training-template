package com.meituan.catering.management.product.api.thrift.service;

import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.product.api.thrift.model.dto.ProductDetailThriftDTO;
import com.meituan.catering.management.product.api.thrift.model.response.ProductDetailThriftResponse;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.converter.ProductDetailThriftDTOConverter;
import com.meituan.catering.management.product.biz.service.ProductBizService;
import com.meituan.catering.management.product.biz.service.impl.ProductBizServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class ProductThriftServiceImpl implements ProductThriftService {

    @Resource
    private ProductBizService productBizService;

    @Override
    public ProductDetailThriftResponse findById(UserContextThriftRequest userContext, Long id) {

        ProductBO productBO = productBizService.findById(userContext.getTenantId(), id);
        ProductDetailThriftDTO productDetailThriftDTO = ProductDetailThriftDTOConverter.toProductDetailThriftDTO(productBO);
        ProductDetailThriftResponse response = new ProductDetailThriftResponse();
        response.setStatus(StatusHelper.success());
        response.setData(productDetailThriftDTO);
        return response;
    }

    @Override
    public List<ProductDetailThriftResponse> findByIds(UserContextThriftRequest userContext, Set<Long> idList) {
        return null;
    }
}
