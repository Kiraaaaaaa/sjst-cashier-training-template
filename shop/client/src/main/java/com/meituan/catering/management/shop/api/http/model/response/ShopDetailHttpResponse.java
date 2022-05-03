package com.meituan.catering.management.shop.api.http.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门店详情的Http返回体
 */
@Data
@ApiModel("门店详情的Http返回体")
public class ShopDetailHttpResponse {

    @ApiModelProperty("物理ID")
    private Long id;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    @ApiModelProperty("审计信息")
    private AuditingHttpModel auditing;

    @ApiModelProperty("业务号")
    private String businessNo;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("管理类型")
    private ManagementTypeEnum managementType;

    @ApiModelProperty("业态类型")
    private BusinessTypeEnum businessType;

    @ApiModelProperty("联系方式")
    private ContactHttpModel contact;

    @ApiModelProperty("营业时间段")
    private OpeningHoursTimeRange openingHours;

    @ApiModelProperty("营业面积")
    private String businessArea;

    @ApiModelProperty("备注")
    private String comment;

    @ApiModelProperty("是否开放营业")
    private Boolean enabled;

    @ApiModelProperty("版本号")
    private Integer version;

    /**
     * 营业时间段
     *
     * @author dulinfeng
     */
    @Data
    @ApiModel("营业时间段")
    public static class OpeningHoursTimeRange {

        private String openTime;

        private String closeTime;

    }
}