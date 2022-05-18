package com.meituan.catering.management.shop.biz.service;

import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.request.*;
import com.meituan.catering.management.shop.dao.model.request.CloseShopDataRequest;

import java.util.List;

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

    /**
     * 跟新门店信息
     * @param tenantId
     * @param userId
     * @param businessNo
     * @param updateShopBizRequest
     * @return
     */
    ShopBO update(Long tenantId, Long userId, String businessNo, UpdateShopBizRequest updateShopBizRequest);

    /**
     * 分页查找
     * @param tenantId
     * @param userId
     * @param searchShopBizRequest
     * @return
     */
    List<ShopBO> searchByConditional(Long tenantId, Long userId, SearchShopBizRequest searchShopBizRequest);

    /**
     * 打开一个门店
     * @param tenantId
     * @param userId
     * @param businessNo
     * @param openShopBizRequest
     * @return
     */
    ShopBO open(Long tenantId, Long userId, String businessNo, OpenShopBizRequest openShopBizRequest);

    /**
     * 关闭一个门店
     * @param tenantId
     * @param userId
     * @param businessNo
     * @param closeShopBizRequest
     * @return
     */
    ShopBO close(Long tenantId, Long userId, String businessNo, CloseShopBizRequest closeShopBizRequest);
}
