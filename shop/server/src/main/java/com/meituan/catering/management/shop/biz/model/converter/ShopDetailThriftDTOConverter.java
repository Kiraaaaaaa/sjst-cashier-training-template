package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.api.thrift.ContactThriftModel;
import com.meituan.catering.management.shop.api.thrift.model.dto.ShopDetailThriftDTO;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import com.meituan.catering.management.shop.biz.model.ShopBO;

/**
 * @author mac
 */
public class ShopDetailThriftDTOConverter {
    public static ShopDetailThriftDTO toShopDetailThriftDTO(ShopBO shopBO) {
        ShopDetailThriftDTO shopDetailThriftDTO = new ShopDetailThriftDTO();
        shopDetailThriftDTO.setId(shopBO.getId());
        shopDetailThriftDTO.setTenantId(shopBO.getTenantId());
        shopDetailThriftDTO.setAuditing(toAuditingThriftModel(shopBO));
        shopDetailThriftDTO.setBusinessNo(shopBO.getBusinessNo());
        shopDetailThriftDTO.setName(shopBO.getName());
        shopDetailThriftDTO.setBusinessType(shopBO.getBusinessType());
        shopDetailThriftDTO.setContact(toContactThriftModel(shopBO));
        shopDetailThriftDTO.setManagementType(shopBO.getManagementType());
        shopDetailThriftDTO.setOpeningHours(toOpeningHoursTimeRange(shopBO));
        shopDetailThriftDTO.setBusinessArea(shopBO.getBusinessArea());
        shopDetailThriftDTO.setComment(shopBO.getComment());
        shopDetailThriftDTO.setEnabled(shopBO.getEnabled());
        shopDetailThriftDTO.setVersion(shopBO.getVersion());
        return shopDetailThriftDTO;
    }

    private static ShopDetailThriftDTO.OpeningHoursTimeRange toOpeningHoursTimeRange(ShopBO shopBO) {
        ShopDetailThriftDTO.OpeningHoursTimeRange openingHoursTimeRange = new ShopDetailThriftDTO.OpeningHoursTimeRange();
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
