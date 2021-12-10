package com.meituan.catering.management.order.api.http.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 订单出餐Http请求体
 *
 * @author dulinfeng
 */
@Data
@ApiModel("订单出餐Http请求体")
public class ProduceCateringOrderHttpRequest {

    @NotNull
    @Min(1)
    @Max(10000)
    @ApiModelProperty("目标订单的版本号")
    private Integer version;

    @Valid
    @NotEmpty
    @ApiModelProperty("订单子项实例列表")
    private final List<Item> items = new LinkedList<>();

    @Data
    @ApiModel("订单出餐Http请求体-订单子项实例")
    public static class Item {

        @NotBlank
        @Length(min = 1, max = 10)
        @ApiModelProperty("序号")
        private String seqNo;

        @NotNull
        @Min(1)
        @Max(10000)
        @ApiModelProperty("目标订单子项的版本号")
        private Integer version;

        @NotNull
        @Min(1)
        @Max(100000)
        @ApiModelProperty("当前出餐数量信息")
        private BigDecimal quantityOnProduce;

        @Valid
        @ApiModelProperty("加料列表")
        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        @ApiModel("订单子项加料实例")
        public static class Accessory {

            @NotBlank
            @Length(min = 1, max = 10)
            @ApiModelProperty("序号")
            private String seqNo;

            @NotNull
            @Min(1)
            @Max(10000)
            @ApiModelProperty("目标订单子项加料的版本号")
            private Integer version;

            @NotNull
            @Min(1)
            @Max(100000)
            @ApiModelProperty("当前出餐数量信息")
            private BigDecimal quantityOnProduce;

        }
    }
}