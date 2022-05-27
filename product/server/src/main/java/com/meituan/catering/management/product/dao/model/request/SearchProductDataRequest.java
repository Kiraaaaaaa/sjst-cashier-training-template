package com.meituan.catering.management.product.dao.model.request;
import com.meituan.catering.management.common.utils.SearchLikeKeyWord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


/**
 * 搜索商品的查询条件
 */
@Data
public class SearchProductDataRequest {

    private Long tenantId;

    private Long userId;

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

        private String name;

        private final UnitPriceRange unitPrice = new UnitPriceRange();

        private String unitOfMeasure;

        private Boolean enabled;

        public String getNameLike() {
            if (name == null) {
                return null;
            }
            String newName = SearchLikeKeyWord.keyUtil(name);
            return "%" + newName + "%";
        }

    }

    private final List<SortField> sortFields = new LinkedList<>();

    @Data
    public static class SortField {

        private String field;

        private String asc;

    }

    @Data
    public static class UnitPriceRange {

        private BigDecimal from;

        private BigDecimal to;

    }

}
