package com.meituan.catering.management.shop.api.http.model.request;

import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 保存门店的Http请求体
 */
@Data
@ApiModel("保存门店的Http请求体")
public class SaveShopHttpRequest {

    @NotBlank
    @Length(min = 1, max = 50)
    @ApiModelProperty("门店名")
    private String name;

    @Valid
    @ApiModelProperty("联系方式")
    private final ContactHttpModel contact = new ContactHttpModel();

    @NotNull
    @ApiModelProperty("业态类型")
    private BusinessTypeEnum businessType;

    @NotNull
    @ApiModelProperty("管理类型")
    private ManagementTypeEnum managementType;

    @Valid
    @ApiModelProperty("营业时间段")
    private final OpeningHoursTimeRange openingHours = new OpeningHoursTimeRange();

    @Length(max = 20)
    @ApiModelProperty("营业面积")
    private String businessArea;

    @Length(max = 200)
    @ApiModelProperty("备注")
    private String comment;


    /**
     * 营业时间段DTO
     *
     * @author dulinfeng
     */
    @Data
    @ApiModel("营业时间段")
    public static class OpeningHoursTimeRange {

        @Length(max = 50)
        @ApiModelProperty("开始营业时间")
        private String openTime;

        @Length(max = 50)
        @ApiModelProperty("结束营业时间")
        private String closeTime;

    }
}