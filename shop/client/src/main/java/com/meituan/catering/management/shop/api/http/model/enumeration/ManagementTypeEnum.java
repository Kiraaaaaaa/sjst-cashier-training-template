package com.meituan.catering.management.shop.api.http.model.enumeration;

import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 管理类型
 */
@Getter
@RequiredArgsConstructor
@ApiModel("门店管理类型")
public enum ManagementTypeEnum implements DescribableEnum {

    /**
     * 直营
     */
    @ApiModelProperty("直营")
    DIRECT_SALES(10, "直营"),

    /**
     * 加盟
     */
    @ApiModelProperty("加盟")
    ALLIANCE(20, "加盟"),

    ;

    private final int code;

    private final String name;
}
