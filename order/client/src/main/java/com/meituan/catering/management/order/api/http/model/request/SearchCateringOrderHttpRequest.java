package com.meituan.catering.management.order.api.http.model.request;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
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
 * 搜索订单的Http请求体
 *
 * @author dulinfeng
 */
@Data
@ApiModel("搜索订单的Http请求体")
public class SearchCateringOrderHttpRequest {

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
    @ApiModel("搜索订单的Http请求体-搜索条件")
    public static class Condition {

        @ApiModelProperty("订单状态（精确匹配）")
        private CateringOrderStatusEnum status;

        @Length(min = 1, max = 10)
        @ApiModelProperty("座位号（精确匹配）")
        private String tableNo;

        @Min(1)
        @Max(100)
        @ApiModelProperty("用餐人数（精确匹配）")
        private Integer customerCount;

        @Valid
        @ApiModelProperty("订单总价区间（范围匹配）")
        private final TotalPriceRange totalPrice = new TotalPriceRange();

    }

    @Valid
    @ApiModelProperty("排序字段列表")
    private final List<SortField> sortFields = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("搜索订单的Http请求体-排序字段")
    public static class SortField {

        @NotBlank
        @Length(min = 1, max = 50)
        @ApiModelProperty("排序字段名")
        private String field;

        @NotNull
        @ApiModelProperty("排序方向：true：升序，false：降序。默认为降序")
        private Boolean asc = FALSE;

    }

    @Data
    @ApiModel("搜索订单的Http请求体-搜索条件-订单总价区间")
    public static class TotalPriceRange {

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