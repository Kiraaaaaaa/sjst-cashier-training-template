package com.meituan.catering.management.product.biz.service;

import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SearchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.SwitchProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
import com.meituan.catering.management.product.biz.model.response.SearchProductBizResponse;
import com.meituan.catering.management.product.dao.model.ProductDO;

/**
 * 商品管理服务
 */
public interface ProductBizService {


    /**
     * 根据商品id查询
     * @param tenantId
     * @param id
     * @return
     */
    ProductBO findById(Long tenantId,Long id);

    /**
     *
     * @param tenantId
     * @param userId
     * @param request
     * @return 插入那条记录的id值
     */
    ProductBO insert(Long tenantId, Long userId, CreateProductBizRequest request);

    /**
     *
     * @param tenantId
     * @param userId
     * @param id
     * @param request
     * @return
     */
    ProductBO update(Long tenantId, Long userId, Long id, UpdateProductBizRequest request);

    /**
     *
     * @param tenantId
     * @param userId
     * @param id
     * @param request
     * @return
     */
    ProductBO enabled(Long tenantId, Long userId, Long id, SwitchProductBizRequest request);

    /**
     *
     * @param tenantId
     * @param userId
     * @param id
     * @param request
     * @return
     */
    ProductBO disabled(Long tenantId, Long userId, Long id, SwitchProductBizRequest request);

    /**
     *
     * @param request
     * @return
     */
    SearchProductBizResponse searchForPage(SearchProductBizRequest request);

}
