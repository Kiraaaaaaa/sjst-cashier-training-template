package com.meituan.catering.management.shop.dao.converter;

import com.meituan.catering.management.shop.biz.model.request.SaveShopBizRequest;
import com.meituan.catering.management.shop.biz.model.request.UpdateShopBizRequest;
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

    public static ShopDO toShopDO(Long tenantId, Long userId, String businessNo, UpdateShopBizRequest updateShopBizRequest){
        ShopDO shopDO = new ShopDO();
        shopDO.setBusinessNo(businessNo);
        shopDO.setName(updateShopBizRequest.getName());
        shopDO.setBusinessType(updateShopBizRequest.getBusinessType());
        shopDO.setManagementType(updateShopBizRequest.getManagementType());
        shopDO.setContactTelephone(updateShopBizRequest.getContact().getTelephone());
        shopDO.setContactCellphone(updateShopBizRequest.getContact().getCellphone());
        shopDO.setContactName(updateShopBizRequest.getContact().getName());
        shopDO.setContactAddress(updateShopBizRequest.getContact().getAddress());
        shopDO.setOpeningHoursOpenTime(updateShopBizRequest.getOpeningHours().getOpenTime());
        shopDO.setOpeningHoursCloseTime(updateShopBizRequest.getOpeningHours().getCloseTime());
        shopDO.setBusinessArea(updateShopBizRequest.getBusinessArea());
        shopDO.setComment(updateShopBizRequest.getComment());
        shopDO.setTenantId(tenantId);
        shopDO.setVersion(updateShopBizRequest.getVersion());
        shopDO.setLastModifiedBy(userId);
        shopDO.setLastModifiedAt(new Date());

        return shopDO;

    }
}
