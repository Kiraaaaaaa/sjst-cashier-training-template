package com.meituan.catering.management.common.model.biz;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 操作用户的上下文BO
 */
@Data
public class UserContextBO {

    @NotNull
    @Min(100)
    @Max(999)
    private Long tenantId;

    @NotNull
    @Min(10000)
    @Max(99999)
    private Long userId;

}
