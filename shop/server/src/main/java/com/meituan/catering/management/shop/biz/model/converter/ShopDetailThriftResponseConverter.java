package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.api.thrift.ContactThriftModel;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import com.meituan.catering.management.shop.biz.model.ShopBO;

public class ShopDetailThriftResponseConverter {
    public static ShopDetailThriftResponse toShopDetailThriftResponse(ShopBO shopBO) {
        ShopDetailThriftResponse shopDetailThriftResponse = new ShopDetailThriftResponse();
        shopDetailThriftResponse.setId(shopBO.getId());
        shopDetailThriftResponse.setTenantId(shopBO.getTenantId());
        shopDetailThriftResponse.setAuditing(toAuditingThriftModel(shopBO));
        shopDetailThriftResponse.setBusinessNo(shopBO.getBusinessNo());
        shopDetailThriftResponse.setName(shopBO.getName());
        shopDetailThriftResponse.setBusinessType(shopBO.getBusinessType());
        shopDetailThriftResponse.setContact(toContactThriftModel(shopBO));
        shopDetailThriftResponse.setManagementType(shopBO.getManagementType());
        shopDetailThriftResponse.setOpeningHours(toOpeningHoursTimeRange(shopBO));
        shopDetailThriftResponse.setBusinessArea(shopBO.getBusinessArea());
        shopDetailThriftResponse.setComment(shopBO.getComment());
        shopDetailThriftResponse.setEnabled(shopBO.getEnabled());
        shopDetailThriftResponse.setVersion(shopBO.getVersion());
        return shopDetailThriftResponse;
    }

    private static ShopDetailThriftResponse.OpeningHoursTimeRange toOpeningHoursTimeRange(ShopBO shopBO) {
        ShopDetailThriftResponse.OpeningHoursTimeRange openingHoursTimeRange = new ShopDetailThriftResponse.OpeningHoursTimeRange();
        openingHoursTimeRange.setOpenTime(shopBO.getOpenTime());
        openingHoursTimeRange.setCloseTime(shopBO.getCloseTime());
        return openingHoursTimeRange;
    }

    private static ContactThriftModel toContactThriftModel(ShopBO shopBO) {
        ContactThriftModel contactThriftModel = new ContactThriftModel();
        contactThriftModel.setTelephone(shopBO.getTelephone());
        contactThriftModel.setCellphone(shopBO.getCellphone());
        contactThriftModel.setName(shopBO.getContactName());
        contactThriftModel.setAddress(shopBO.getContactAddress());
        return contactThriftModel;
    }

    private static AuditingThriftModel toAuditingThriftModel(ShopBO shopBO) {
        AuditingThriftModel auditingThriftModel = new AuditingThriftModel();
        auditingThriftModel.setCreatedBy(shopBO.getAuditing().getCreatedBy());
        auditingThriftModel.setCreatedAtTimestamp(shopBO.getAuditing().getCreatedAt().getTime());
        auditingThriftModel.setLastModifiedBy(shopBO.getAuditing().getLastModifiedBy());
        auditingThriftModel.setLastModifiedAtTimestamp(shopBO.getAuditing().getLastModifiedAt().getTime());
        return auditingThriftModel;
    }
}
