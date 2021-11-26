package com.meituan.catering.management.shop.api.http.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 开放门店的Http请求体
 */
@Data
@ApiModel("开放门店的Http请求体")
public class OpenShopHttpRequest {

    @NotNull
    @Min(1)
    @Max(10000)
    @ApiModelProperty("目标门店的版本号")
    private Integer version;
}