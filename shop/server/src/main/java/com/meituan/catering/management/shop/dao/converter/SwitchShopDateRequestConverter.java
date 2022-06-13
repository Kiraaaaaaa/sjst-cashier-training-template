package com.meituan.catering.management.shop.dao.converter;

import com.meituan.catering.management.shop.biz.model.request.CloseShopBizRequest;
import com.meituan.catering.management.shop.biz.model.request.OpenShopBizRequest;
import com.meituan.catering.management.shop.dao.model.ShopDO;
import com.meituan.catering.management.shop.dao.model.request.CloseShopDataRequest;
import com.meituan.catering.management.shop.dao.model.request.OpenShopDataRequest;

import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 15:17
 * @ClassName: SwitchShopDateRequestConverter
 */
public class SwitchShopDateRequestConverter {

    public static ShopDO toShopDO(Long tenantId, Long userId, String businessNo, OpenShopBizRequest request) {
        ShopDO shopDO = new ShopDO();
        shopDO.setVersion(request.getVersion());
        shopDO.setBusinessNo(businessNo);
        shopDO.setLastModifiedAt(new Date());
        shopDO.setLastModifiedBy(userId);
        shopDO.setTenantId(tenantId);
        shopDO.setEnabled(true);
        return shopDO;
    }


    public static ShopDO toShopDO(Long tenantId, Long userId, String businessNo, CloseShopBizRequest request) {
        ShopDO shopDO = new ShopDO();
        shopDO.setVersion(request.getVersion());
        shopDO.setBusinessNo(businessNo);
        shopDO.setTenantId(tenantId);
        shopDO.setLastModifiedAt(new Date());
        shopDO.setLastModifiedBy(userId);
        shopDO.setEnabled(false);
        return shopDO;
    }
}
