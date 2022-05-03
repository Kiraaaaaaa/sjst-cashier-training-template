package com.meituan.catering.management.shop.dao.model;

import com.meituan.catering.management.common.model.dao.BaseDO;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 门店DO定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShopDO extends BaseDO {
    /**
     * 商户号
     */
    private String businessNo;
    /**
     * 门店名
     */
    private String name;

    /**
     * 主营业态
     */
    private BusinessTypeEnum businessType;

    /**
     * 管理类型
     */
    private ManagementTypeEnum managementType;

    /**
     * 联系方式-座机
     */
    private String contactTelephone;

    /**
     * 联系方式-手机
     */
    private String contactCellphone;

    /**
     * 联系方式-联系人名称
     */
    private String contactName;

    /**
     * 联系方式-地址
     */
    private String contactAddress;

    /**
     * 营业时间-开始时间点
     */
    private String openingHoursOpenTime;

    /**
     * 营业时间-结束时间点
     */
    private String openingHoursCloseTime;

    /**
     * 营业时间-经营面积
     */
    private String businessArea;

    /**
     * 门店介绍
     */
    private String comment;

    /**
     * 门店启停状态
     */
    private Boolean enabled;
}