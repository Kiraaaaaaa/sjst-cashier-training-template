package com.meituan.catering.management.product.api.http.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Boolean.FALSE;

/**
 * 搜索商品的Http请求体
 */
@Data
@ApiModel("搜索商品的Http请求体")
public class SearchProductHttpRequest {

    @NotNull
    @Min(1)
    @Max(1000)
    @ApiModelProperty("分页码，从1开始计算，默认为1")
    private Integer pageIndex = 1;

    @NotNull
    @Min(1)
    @Max(100)
    @ApiModelProperty("分页大小，默认为10")
    private Integer pageSize = 10;

    @Valid
    @ApiModelProperty("搜索条件")
    private final Condition condition = new Condition();

    @Data
    @ApiModel("搜索商品的搜索条件")
    public static class Condition {

        @Length(max = 50)
        @ApiModelProperty("商品名，模糊匹配")
        private String name;

        @ApiModelProperty("商品单价区间")
        private final UnitPriceRange unitPrice = new UnitPriceRange();

        @Length(max = 10)
        @ApiModelProperty("计量单位，精确匹配")
        private String unitOfMeasure;

        @ApiModelProperty("是否上架")
        private Boolean enabled;

    }

    @ApiModelProperty("排序字段列表")
    private final List<SortField> sortFields = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("排序字段请求")
    public static class SortField {

        @NotBlank
        @Length(max = 50)
        @ApiModelProperty("排序字段名")
        private String field;

        @NotNull
        @ApiModelProperty("排序方向：true：升序，false：降序。默认为降序")
        private Boolean asc = FALSE;

    }

    @Data
    @ApiModel("商品单价区间")
    public static class UnitPriceRange {

        @Min(0)
        @Max(100000)
        @ApiModelProperty("最小价格")
        private BigDecimal from;

        @Min(0)
        @Max(100000)
        @ApiModelProperty("最大价格")
        private BigDecimal to;

    }
}