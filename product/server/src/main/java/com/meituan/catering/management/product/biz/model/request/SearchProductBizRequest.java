package com.meituan.catering.management.product.biz.model.request;

import com.meituan.catering.management.product.api.http.model.request.SearchProductHttpRequest;
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
 * @Author:zhangzhefeng 2022/5/24 11:40
 * @ClassName: SearchProductBizRequest
 */
@Data
public class SearchProductBizRequest {

    private Long tenantId;

    private Long userId;

    private Integer pageIndex;

    private Integer pageSize;

    private final Condition condition = new Condition();

    @Data
    public static class Condition {

        private String name;

        private final UnitPriceRange unitPrice = new UnitPriceRange();

        private String unitOfMeasure;

        private Boolean enabled;

    }

    private final List<SortField> sortFields = new LinkedList<>();

    @Data
    public static class SortField {

        private String field;

        private Boolean asc;

    }

    @Data
    public static class UnitPriceRange {

        private BigDecimal from;

        private BigDecimal to;

    }
}
