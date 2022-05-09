package com.meituan.catering.management.shop.biz.service;

import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.request.SaveShopBizRequest;

/**
 * 门店管理服务
 * @author mac
 */
public interface ShopBizService {

    /**
     * 根据商户号查询门店
     * @param tenantId
     * @param userId
     * @param businessNo
     * @return
     */
    ShopBO findByBusinessNo(Long tenantId, Long userId, String businessNo);

    /**
     * 创建门店
     * @param tenantId
     * @param userId
     * @param saveShopBizRequest
     * @return
     */
    ShopBO create(Long tenantId, Long userId, SaveShopBizRequest saveShopBizRequest);
}
