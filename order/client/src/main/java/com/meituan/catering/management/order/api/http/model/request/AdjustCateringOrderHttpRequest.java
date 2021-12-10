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
 * 订单加退菜Http请求体
 *
 * @author dulinfeng
 */
@Data
@ApiModel("订单加退菜Http请求体")
public class AdjustCateringOrderHttpRequest {

    @NotNull
    @Min(1)
    @Max(10000)
    @ApiModelProperty("目标订单的版本号")
    private Integer version;

    @NotEmpty
    @ApiModelProperty("订单子项列表")
    private final List<Item> items = new LinkedList<>();

    @Data
    @ApiModel("订单加退菜Http请求体-订单子项实例")
    public static class Item {

        @NotBlank
        @Length(min = 1, max = 20)
        @ApiModelProperty("序号")
        private String seqNo;

        @Min(1)
        @Max(10000)
        @ApiModelProperty("目标订单子项的版本号（当且仅当更新已有子项时需要附加）")
        private Integer version;

        @NotNull
        @Min(-10000)
        @Max(10000)
        @ApiModelProperty("调整数量（根据正负号来区分是加菜还是退菜）")
        private BigDecimal quantityOnAdjustment;

        @Min(1)
        @ApiModelProperty("商品ID（当且仅当新增子项时需要附加）")
        private Long productId;

        @Min(1)
        @ApiModelProperty("商品做法ID（当且仅当新增子项时需要附加）")
        private Long productMethodId;

        @Valid
        @ApiModelProperty("加料列表")
        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        @ApiModel("订单子项加料实例")
        public static class Accessory {

            @NotBlank
            @Length(min = 1, max = 20)
            @ApiModelProperty("序号")
            private String seqNo;

            @Min(1)
            @Max(10000)
            @ApiModelProperty("目标订单子项加料的版本号（当且仅当更新已有子项时需要附加）")
            private Integer version;

            @NotNull
            @Min(-10000)
            @Max(10000)
            @ApiModelProperty("调整数量（根据正负号来区分是增加还是减少）")
            private BigDecimal quantityOnAdjustment;

            @Min(1)
            @ApiModelProperty("商品加料ID（当且仅当新增子项加料时需要附加）")
            private Long productAccessoryId;

        }
    }
}