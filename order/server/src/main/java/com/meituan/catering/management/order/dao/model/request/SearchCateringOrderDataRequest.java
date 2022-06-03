package com.meituan.catering.management.order.dao.model.request;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.biz.model.request.SearchCateringOrderBizRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Boolean.FALSE;

/**
 * 搜索订单的查询条件
 */
@Data
public class SearchCateringOrderDataRequest {

    private Long tenantId;

    private Integer pageIndex;

    private Integer pageSize;

    private final Condition condition = new Condition();

    public Integer getSkip(){
        if (pageIndex==null || pageSize==null){
            return null;
        }
        return (pageIndex-1)*pageSize;
    }
    public Integer getLimit() {
        return pageSize;
    }

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

        private String asc;

    }

    @Data
    public static class TotalPriceRange {

        private BigDecimal from;

        private BigDecimal to;

    }
}
