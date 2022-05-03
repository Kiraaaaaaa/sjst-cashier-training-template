package com.meituan.catering.management.shop.biz.model;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.biz.BaseBO;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import com.meituan.catering.management.shop.api.http.model.response.ShopDetailHttpResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

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