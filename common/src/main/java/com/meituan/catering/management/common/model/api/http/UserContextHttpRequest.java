package com.meituan.catering.management.common.model.api.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 操作用户的上下文
 */
@Data
@Valid
@ApiModel("请求用户的上下文的Http请求体")
public class UserContextHttpRequest {

    @NotNull
    @Min(100)
    @Max(999)
    @ApiModelProperty("租户ID")
    private Long tenantId;

    @NotNull
    @Min(10000)
    @Max(99999)
    @ApiModelProperty("用户ID")
    private Long userId;
}
