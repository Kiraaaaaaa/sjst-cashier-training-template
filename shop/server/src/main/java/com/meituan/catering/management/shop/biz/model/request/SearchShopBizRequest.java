package com.meituan.catering.management.shop.biz.model.request;

import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.common.model.enumeration.ManagementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/16 15:46
 * @ClassName: SearchShopBizRequest
 */
@Data
public class SearchShopBizRequest {

    private Integer pageIndex;

    private Integer pageSize;

    private Condition condition = new Condition();

    @Data
    public static class Condition {

        private String keyword;

        private Set<ManagementTypeEnum> managementTypes = new LinkedHashSet<>();

        private Set<BusinessTypeEnum> businessTypes = new LinkedHashSet<>();

        private Boolean enabled;

    }

    private List<SortField> sortFields = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SortField {

        private String field;

        private Boolean asc;

    }

}
