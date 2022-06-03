package com.meituan.catering.management.order.remote.model.converter;

import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import com.meituan.catering.management.shop.api.thrift.model.dto.ShopDetailThriftDTO;

/**
 * @author mac
 */
public class ShopDetailRemoteResponseConverter {
    public static ShopDetailRemoteResponse toShopDetailRemoteResponse(ShopDetailThriftDTO shopDetailThriftDTO) {
        System.out.println(shopDetailThriftDTO);
        if (shopDetailThriftDTO == null){
            return null;
        }

        ShopDetailRemoteResponse response = new ShopDetailRemoteResponse();
        response.setId(shopDetailThriftDTO.getId());
        response.setTenantId(shopDetailThriftDTO.getTenantId());
        response.setBusinessNo(shopDetailThriftDTO.getBusinessNo());
        response.setName(shopDetailThriftDTO.getName());
        response.setBusinessType(shopDetailThriftDTO.getBusinessType());
        response.setContact(shopDetailThriftDTO.getContact());
        response.setManagementType(shopDetailThriftDTO.getManagementType());
        response.setBusinessArea(shopDetailThriftDTO.getBusinessArea());
        response.setComment(shopDetailThriftDTO.getComment());
        response.setEnabled(shopDetailThriftDTO.getEnabled());
        response.setVersion(shopDetailThriftDTO.getVersion());

        response.getAuditing().setLastModifiedAtTimestamp(shopDetailThriftDTO.getAuditing().getLastModifiedAtTimestamp());
        response.getAuditing().setCreatedAtTimestamp(shopDetailThriftDTO.getAuditing().getCreatedAtTimestamp());
        response.getAuditing().setCreatedBy(shopDetailThriftDTO.getAuditing().getCreatedBy());
        response.getAuditing().setLastModifiedBy(shopDetailThriftDTO.getAuditing().getLastModifiedBy());

        response.getOpeningHours().setOpenTime(shopDetailThriftDTO.getOpeningHours().getOpenTime());
        response.getOpeningHours().setCloseTime(shopDetailThriftDTO.getOpeningHours().getCloseTime());

        return response;
    }
}
