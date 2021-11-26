package com.meituan.catering.management.common.model.enumeration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 业态类型
 */
@Getter
@RequiredArgsConstructor
@ApiModel("门店业态类型")
public enum BusinessTypeEnum implements DescribableEnum {

    /**
     * 正餐
     */
    @ApiModelProperty("正餐")
    DINNER(10, "正餐"),

    /**
     * 快餐
     */
    @ApiModelProperty("快餐")
    FAST_FOOD(20, "快餐"),

    /**
     * 火锅
     */
    @ApiModelProperty("火锅")
    HOT_POT(30, "火锅"),

    /**
     * 烧烤
     */
    @ApiModelProperty("烧烤")
    BARBECUE(40, "烧烤"),

    /**
     * 西餐
     */
    @ApiModelProperty("西餐")
    WESTERN_FOOD(50, "西餐"),

    ;

    private final int code;

    private final String name;
}
