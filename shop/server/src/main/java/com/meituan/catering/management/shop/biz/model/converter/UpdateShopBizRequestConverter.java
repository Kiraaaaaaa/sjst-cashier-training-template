package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.shop.api.http.model.request.UpdateShopHttpRequest;
import com.meituan.catering.management.shop.biz.model.request.UpdateShopBizRequest;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/12 17:13
 * @ClassName: UpdateShopBizRequestConverter
 */
public class UpdateShopBizRequestConverter {

    public static UpdateShopBizRequest toUpdateShopBizRequest(UpdateShopHttpRequest request) {
        UpdateShopBizRequest updateShopBizRequest = new UpdateShopBizRequest();
        updateShopBizRequest.setVersion(request.getVersion());
        updateShopBizRequest.setName(request.getName());
        updateShopBizRequest.setContact(buildContact(request.getContact()));
        updateShopBizRequest.setBusinessType(request.getBusinessType());
        updateShopBizRequest.setManagementType(request.getManagementType());
        updateShopBizRequest.setOpeningHours(buildOpeningHours(request.getOpeningHours()));
        updateShopBizRequest.setBusinessArea(request.getBusinessArea());
        updateShopBizRequest.setComment(request.getComment());

        return updateShopBizRequest;

    }

    private static UpdateShopBizRequest.OpeningHoursTimeRange buildOpeningHours(UpdateShopHttpRequest.OpeningHoursTimeRange openingHours) {
        UpdateShopBizRequest.OpeningHoursTimeRange openingHoursTimeRange = new UpdateShopBizRequest.OpeningHoursTimeRange();
        openingHoursTimeRange.setOpenTime(openingHours.getOpenTime());
        openingHoursTimeRange.setCloseTime(openingHours.getCloseTime());
        return openingHoursTimeRange;
    }


    private static UpdateShopBizRequest.ContactBizModel buildContact(ContactHttpModel contact) {
        UpdateShopBizRequest.ContactBizModel contactBizModel = new UpdateShopBizRequest.ContactBizModel();
        contactBizModel.setTelephone(contact.getTelephone());
        contactBizModel.setCellphone(contact.getCellphone());
        contactBizModel.setName(contact.getName());
        contactBizModel.setAddress(contact.getAddress());
        return contactBizModel;
    }
}
