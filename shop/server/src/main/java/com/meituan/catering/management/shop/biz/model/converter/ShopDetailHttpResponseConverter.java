package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.biz.AuditingBO;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import com.meituan.catering.management.shop.api.http.model.response.ShopDetailHttpResponse;
import com.meituan.catering.management.shop.biz.model.ShopBO;

public class ShopDetailHttpResponseConverter extends ShopHttpVOConverter{

    public static ShopDetailHttpResponse toShopDetailHttpResponse(ShopBO shopBO) {
        ShopDetailHttpResponse shopDetailHttpResponse = new ShopDetailHttpResponse();
        shopDetailHttpResponse.setId(shopBO.getId());
        shopDetailHttpResponse.setTenantId(shopBO.getTenantId());
        shopDetailHttpResponse.setAuditing(toAuditingHttpModel(shopBO.getAuditing()));
        shopDetailHttpResponse.setBusinessNo(shopBO.getBusinessNo());
        shopDetailHttpResponse.setName(shopBO.getName());
        shopDetailHttpResponse.setManagementType(shopBO.getManagementType());
        shopDetailHttpResponse.setBusinessType(shopBO.getBusinessType());
        shopDetailHttpResponse.setContact(toContactHttpModel(shopBO));
        shopDetailHttpResponse.setOpeningHours(toOpeningHoursTimeRange(shopBO.getOpenTime(),shopBO.getCloseTime()));
        shopDetailHttpResponse.setBusinessArea(shopBO.getBusinessArea());
        shopDetailHttpResponse.setComment(shopBO.getComment());
        shopDetailHttpResponse.setEnabled(shopBO.getEnabled());
        shopDetailHttpResponse.setVersion(shopBO.getVersion());
        return shopDetailHttpResponse;


    }

    private static ShopDetailHttpResponse.OpeningHoursTimeRange toOpeningHoursTimeRange(String openTime, String closeTime) {
        ShopDetailHttpResponse.OpeningHoursTimeRange openingHoursTimeRange = new ShopDetailHttpResponse.OpeningHoursTimeRange();
        openingHoursTimeRange.setOpenTime(openTime);
        openingHoursTimeRange.setCloseTime(closeTime);
        return openingHoursTimeRange;
    }

    private static ContactHttpModel toContactHttpModel(ShopBO shopBO) {
        ContactHttpModel contactHttpModel = new ContactHttpModel();
        contactHttpModel.setTelephone(shopBO.getTelephone());
        contactHttpModel.setCellphone(shopBO.getCellphone());
        contactHttpModel.setName(shopBO.getContactName());
        contactHttpModel.setAddress(shopBO.getContactAddress());
        return contactHttpModel;
    }

    private static AuditingHttpModel toAuditingHttpModel(AuditingBO auditing) {
        AuditingHttpModel auditingHttpModel = new AuditingHttpModel();
        auditingHttpModel.setCreatedBy(auditing.getCreatedBy());
        auditingHttpModel.setCreatedAt(auditing.getCreatedAt());
        auditingHttpModel.setLastModifiedBy(auditing.getLastModifiedBy());
        auditingHttpModel.setLastModifiedAt(auditing.getLastModifiedAt());
        return auditingHttpModel;
    }
}
