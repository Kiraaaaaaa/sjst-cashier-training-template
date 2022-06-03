package com.meituan.catering.management.shop.biz.model;

import com.meituan.catering.management.common.model.biz.BaseBO;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.common.model.enumeration.ManagementTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 门店BO定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShopBO extends BaseBO {
    private String businessNo;

    private String name;

    private ManagementTypeEnum managementType;

    private BusinessTypeEnum businessType;

    private String telephone;

    private String cellphone;

    private String contactName;

    private String contactAddress;

    private String openTime;

    private String closeTime;

    private String businessArea;

    private String comment;

    private Boolean enabled;
}