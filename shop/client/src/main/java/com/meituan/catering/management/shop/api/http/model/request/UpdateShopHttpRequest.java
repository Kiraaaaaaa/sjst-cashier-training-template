package com.meituan.catering.management.shop.api.http.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 更新门店的Http请求体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("更新门店的Http请求体")
public class UpdateShopHttpRequest extends SaveShopHttpRequest {

    @NotNull
    @Min(1)
    @Max(10000)
    @ApiModelProperty("目标门店的版本号")
    private Integer version;
}