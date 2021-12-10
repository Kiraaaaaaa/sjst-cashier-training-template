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
 * 下新订单Http请求体
 *
 * @author dulinfeng
 */
@Data
@ApiModel("下新订单的Http请求体")
public class PlaceCateringOrderHttpRequest {

    @NotBlank
    @Length(min = 8, max = 20)
    @ApiModelProperty("门店业务号")
    private String shopBusinessNo;

    @NotBlank
    @Length(min = 1, max = 10)
    @ApiModelProperty("座位号")
    private String tableNo;

    @ApiModelProperty("用餐人数")
    private Integer customerCount;

    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;

    @Length(min = 1, max = 200)
    @ApiModelProperty("备注")
    private String comment;

    @Valid
    @NotEmpty
    @ApiModelProperty("订单子项实例列表")
    private final List<Item> items = new LinkedList<>();

    @Data
    @ApiModel("下新订单的Http请求体-订单子项实例")
    public static class Item {

        @NotBlank
        @Length(min = 1, max = 10)
        @ApiModelProperty("序号")
        private String seqNo;

        @NotNull
        @Min(1)
        @Max(100000)
        @ApiModelProperty("数量信息")
        private BigDecimal quantity;

        @NotNull
        @Min(1)
        @ApiModelProperty("商品ID")
        private Long productId;

        @Min(1)
        @ApiModelProperty("商品做法ID")
        private Long productMethodId;

        @Valid
        @ApiModelProperty("加料列表")
        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        @ApiModel("加料实例")
        public static class Accessory {

            @NotBlank
            @Length(min = 1, max = 10)
            @ApiModelProperty("序号")
            private String seqNo;

            @NotNull
            @Min(1)
            @Max(100000)
            @ApiModelProperty("数量信息")
            private BigDecimal quantity;

            @NotNull
            @Min(1)
            @ApiModelProperty("商品加料ID")
            private Long productAccessoryId;

        }
    }
}