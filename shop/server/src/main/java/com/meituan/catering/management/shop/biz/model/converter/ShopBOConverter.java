package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.common.model.biz.AuditingBO;
import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.dao.model.ShopDO;

/**
 * 从其他数据模型向门店BO的转换器
 */
public class ShopBOConverter {
    public static ShopBO toShopBO(ShopDO shopDO){
        ShopBO shopBO = new ShopBO();
        shopBO.setBusinessNo(shopDO.getBusinessNo());
        shopBO.setName(shopDO.getName());
        shopBO.setManagementType(shopDO.getManagementType());
        shopBO.setBusinessType(shopDO.getBusinessType());
        shopBO.setTelephone(shopDO.getContactTelephone());
        shopBO.setCellphone(shopDO.getContactCellphone());
        shopBO.setContactName(shopDO.getContactName());
        shopBO.setContactAddress(shopDO.getContactAddress());
        shopBO.setOpenTime(shopDO.getOpeningHoursOpenTime());
        shopBO.setCloseTime(shopDO.getOpeningHoursCloseTime());
        shopBO.setBusinessArea(shopDO.getBusinessArea());
        shopBO.setComment(shopDO.getComment());
        shopBO.setEnabled(shopDO.getEnabled());
        shopBO.setId(shopDO.getId());
        shopBO.setTenantId(shopDO.getTenantId());
        shopBO.setVersion(shopDO.getVersion());
        shopBO.setAuditing(toAuditingBO(shopDO));
        return shopBO;
    }

    private static AuditingBO toAuditingBO(ShopDO shopDO) {
        AuditingBO auditingBO = new AuditingBO();
        auditingBO.setCreatedBy(shopDO.getCreatedBy());
        auditingBO.setCreatedAt(shopDO.getCreatedAt());
        auditingBO.setLastModifiedBy(shopDO.getLastModifiedBy());
        auditingBO.setLastModifiedAt(shopDO.getLastModifiedAt());
        return auditingBO;
    }

}
