package com.meituan.catering.management.shop.biz.model.request;

import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import lombok.Data;

/**
 * @author mac
 */
@Data
public class SaveShopBizRequest {
    private String name;

    private SaveShopBizRequest.ContactBizModel contact;

    private BusinessTypeEnum businessType;

    private ManagementTypeEnum managementType;

    private SaveShopBizRequest.OpeningHoursTimeRange openingHours;

    private String businessArea;

    private String comment;


    /**
     * 营业时间段DTO
     *
     * @author dulinfeng
     */
    @Data
    public static class OpeningHoursTimeRange {
        private String openTime;

        private String closeTime;
    }

    @Data
    public static class ContactBizModel {
        private String telephone;

        private String cellphone;

        private String name;

        private String address;
    }
}
