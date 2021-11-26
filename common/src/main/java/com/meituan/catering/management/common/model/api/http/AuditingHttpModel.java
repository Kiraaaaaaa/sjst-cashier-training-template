package com.meituan.catering.management.common.model.api.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * 业务对象的审计信息DTO
 */
@Data
@ApiModel("审计信息的Http请求体")
public class AuditingHttpModel {

    @NotNull
    @Min(10000L)
    @Max(99999L)
    @ApiModelProperty("创建的用户ID")
    private Long createdBy;

    @NotNull
    @PastOrPresent
    @ApiModelProperty("创建时间")
    private Date createdAt;

    @Min(10000L)
    @Max(99999L)
    @ApiModelProperty("最后一次更新的用户ID")
    private Long lastModifiedBy;

    @PastOrPresent
    @ApiModelProperty("最后一次更新时间")
    private Date lastModifiedAt;
}
