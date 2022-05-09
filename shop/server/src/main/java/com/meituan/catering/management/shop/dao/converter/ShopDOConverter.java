package com.meituan.catering.management.shop.dao.converter;

import com.meituan.catering.management.shop.biz.model.request.SaveShopBizRequest;
import com.meituan.catering.management.shop.dao.model.ShopDO;

import java.util.Date;

/**
 * @author mac
 */
public class ShopDOConverter {
    public static ShopDO toShopDO(Long tenantId, Long userId, SaveShopBizRequest saveShopBizRequest) {
        ShopDO shopDO = new ShopDO();
        shopDO.setBusinessNo(String.valueOf(System.currentTimeMillis()));
        shopDO.setName(saveShopBizRequest.getName());
        shopDO.setBusinessType(saveShopBizRequest.getBusinessType());
        shopDO.setManagementType(saveShopBizRequest.getManagementType());
        shopDO.setContactTelephone(saveShopBizRequest.getContact().getTelephone());
        shopDO.setContactCellphone(saveShopBizRequest.getContact().getCellphone());
        shopDO.setContactName(saveShopBizRequest.getContact().getName());
        shopDO.setContactAddress(saveShopBizRequest.getContact().getAddress());
        shopDO.setOpeningHoursOpenTime(saveShopBizRequest.getOpeningHours().getOpenTime());
        shopDO.setOpeningHoursCloseTime(saveShopBizRequest.getOpeningHours().getCloseTime());
        shopDO.setBusinessArea(saveShopBizRequest.getBusinessArea());
        shopDO.setComment(saveShopBizRequest.getComment());
        shopDO.setEnabled(Boolean.TRUE);
        shopDO.setTenantId(tenantId);
        shopDO.setCreatedBy(userId);
        shopDO.setCreatedAt(new Date());
        shopDO.setLastModifiedBy(userId);
        shopDO.setLastModifiedAt(new Date());
        return shopDO;
    }
}
