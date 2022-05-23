package com.meituan.catering.management.product.biz.service;

import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
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
    Long insert(Long tenantId, Long userId, CreateProductBizRequest request);

    Long update(Long tenantid, Long userId, Long id, UpdateProductBizRequest request);

}
