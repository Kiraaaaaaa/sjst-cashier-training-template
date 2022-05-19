package com.meituan.catering.management.shop.dao.model.request;

import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * 搜索门店的查询条件
 */
@Data
public class SearchShopDataRequest {

    private Long tenantId;

    private Long userId;

    private Integer pageIndex;

    private Integer pageSize;

    private Condition condition = new Condition();

    private List<SortField> sortFields = new LinkedList<>();

    public Integer getSkip() {
        if (pageIndex == null || pageSize == null) {
            return null;
        }
        return (pageIndex - 1) * pageSize;
    }

    public Integer getLimit() {
        return pageSize;
    }


    @Data
    public static class Condition {

        private String keyword;

        private Set<ManagementTypeEnum> managementTypes = new LinkedHashSet<>();

        private Set<BusinessTypeEnum> businessTypes = new LinkedHashSet<>();

        private Integer enabled;

        public String getKeyWordLike() {
            if (keyword == null) {
                return null;
            }
            return "%" + keyword + "%";
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SortField {

        private String field;

        private String asc;

    }
}
