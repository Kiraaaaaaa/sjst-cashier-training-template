package com.meituan.catering.management.product.api.http.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 上架商品的Http请求体
 */
@Data
@ApiModel("上架商品的Http请求体")
public class EnableProductHttpRequest {

    @NotNull
    @Min(1)
    @Max(10000)
    @ApiModelProperty("目标商品的版本号")
    private Integer version;
}