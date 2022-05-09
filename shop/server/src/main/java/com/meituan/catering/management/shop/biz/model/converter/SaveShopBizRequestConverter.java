package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.shop.api.http.model.request.CreateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.SaveShopHttpRequest;
import com.meituan.catering.management.shop.biz.model.request.SaveShopBizRequest;

/**
 * @author mac
 */
public class SaveShopBizRequestConverter {
    public static SaveShopBizRequest toSaveShopBizRequest(CreateShopHttpRequest request) {
        SaveShopBizRequest saveShopBizRequest = new SaveShopBizRequest();
        saveShopBizRequest.setName(request.getName());
        saveShopBizRequest.setContact(buildContact(request.getContact()));
        saveShopBizRequest.setBusinessType(request.getBusinessType());
        saveShopBizRequest.setManagementType(request.getManagementType());
        saveShopBizRequest.setOpeningHours(buildOpeningHours(request.getOpeningHours()));
        saveShopBizRequest.setBusinessArea(request.getBusinessArea());
        saveShopBizRequest.setComment(request.getComment());
        return saveShopBizRequest;
    }

    private static SaveShopBizRequest.OpeningHoursTimeRange buildOpeningHours(SaveShopHttpRequest.OpeningHoursTimeRange openingHours) {
        SaveShopBizRequest.OpeningHoursTimeRange openingHoursTimeRange = new SaveShopBizRequest.OpeningHoursTimeRange();
        openingHoursTimeRange.setOpenTime(openingHours.getOpenTime());
        openingHoursTimeRange.setCloseTime(openingHours.getCloseTime());
        return openingHoursTimeRange;
    }


    private static SaveShopBizRequest.ContactBizModel buildContact(ContactHttpModel contact) {
        SaveShopBizRequest.ContactBizModel contactBizModel = new SaveShopBizRequest.ContactBizModel();
        contactBizModel.setTelephone(contact.getTelephone());
        contactBizModel.setCellphone(contact.getCellphone());
        contactBizModel.setName(contact.getName());
        contactBizModel.setAddress(contact.getAddress());
        return contactBizModel;
    }
}
