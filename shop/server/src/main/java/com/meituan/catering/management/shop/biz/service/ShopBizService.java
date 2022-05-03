package com.meituan.catering.management.shop.biz.service;

import com.meituan.catering.management.shop.biz.model.ShopBO;

/**
 * 门店管理服务
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
}
