package com.meituan.catering.management.order.biz.model.request;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.SearchCateringOrderHttpRequest;
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
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/30 11:20
 * @ClassName: CateringOrderDetailBizRequest
 */
@Data
public class SearchCateringOrderBizRequest {

    private Integer pageIndex;

    private Integer pageSize;

    private final Condition condition = new Condition();

    @Data
    public static class Condition {

        private CateringOrderStatusEnum status;

        private String tableNo;

        private Integer customerCount;

        private final TotalPriceRange totalPrice = new TotalPriceRange();

    }

    private final List<SortField> sortFields = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SortField {

        private String field;

        private Boolean asc = FALSE;

    }

    @Data
    public static class TotalPriceRange {

        private BigDecimal from;

        private BigDecimal to;

    }
}
