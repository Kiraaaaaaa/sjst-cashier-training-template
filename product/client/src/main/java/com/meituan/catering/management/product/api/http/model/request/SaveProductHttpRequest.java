package com.meituan.catering.management.product.api.http.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 保存商品的Http请求体
 */
@Data
@ApiModel("保存商品的Http请求体")
public class SaveProductHttpRequest {

    @NotBlank
    @Length(min = 1, max = 50)
    @ApiModelProperty("商品名称")
    private String name;

    @NotNull
    @Min(0)
    @Max(100000)
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @NotNull
    @Min(1)
    @Max(999)
    @ApiModelProperty("起售量")
    private BigDecimal minSalesQuantity;

    @NotNull
    @Min(1)
    @Max(999)
    @ApiModelProperty("增售量")
    private BigDecimal increaseSalesQuantity;

    @NotBlank
    @Length(min = 1, max = 10)
    @ApiModelProperty("计量单位")
    private String unitOfMeasure;

    @Length(min = 1, max = 200)
    @ApiModelProperty("描述")
    private String description;

    @Valid
    @ApiModelProperty("做法组")
    private final List<MethodGroup> methodGroups = new LinkedList<>();

    @Valid
    @ApiModelProperty("加料组")
    private final List<AccessoryGroup> accessoryGroups = new LinkedList<>();

    @Data
    @ApiModel("商品做法组")
    public static class MethodGroup {

        @NotBlank
        @Length(min = 1, max = 50)
        @ApiModelProperty("做法组名")
        private String name;

        @Valid
        @Size(min = 1, max = 20)
        @ApiModelProperty("做法项列表")
        private final List<Option> options = new LinkedList<>();

        @Data
        @ApiModel("做法项")
        public static class Option {

            @NotBlank
            @Length(min = 1, max = 50)
            @ApiModelProperty("名称")
            private String name;

        }
    }

    @Data
    @ApiModel("商品加料组")
    public static class AccessoryGroup {

        @NotBlank
        @Length(min = 1, max = 50)
        @ApiModelProperty("加料组名")
        private String name;

        @Valid
        @NotNull
        @Size(min = 1, max = 20)
        @ApiModelProperty("加料项列表")
        private final List<Option> options = new LinkedList<>();

        @Data
        @ApiModel("加料项")
        public static class Option {

            @NotBlank
            @Length(min = 1, max = 50)
            @ApiModelProperty("名称")
            private String name;

            @NotNull
            @Min(0)
            @Max(100000)
            @ApiModelProperty("单价")
            private BigDecimal unitPrice;

            @NotBlank
            @Length(min = 1, max = 10)
            @ApiModelProperty("计量单位")
            private String unitOfMeasure;

        }
    }
}