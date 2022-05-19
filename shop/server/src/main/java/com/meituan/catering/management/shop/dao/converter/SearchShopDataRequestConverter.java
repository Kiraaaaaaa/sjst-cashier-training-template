package com.meituan.catering.management.shop.dao.converter;

import com.meituan.catering.management.shop.biz.model.request.SearchShopBizRequest;
import com.meituan.catering.management.shop.dao.model.request.SearchShopDataRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/16 16:55
 * @ClassName: SearchDOConverter
 */
public class SearchShopDataRequestConverter {

    public static SearchShopDataRequest toSearchShopDataRequest(Long tenantId, Long userId, SearchShopBizRequest request) {
        SearchShopDataRequest searchShopDataRequest = new SearchShopDataRequest();
        searchShopDataRequest.setTenantId(tenantId);
        searchShopDataRequest.setUserId(userId);
        searchShopDataRequest.setPageIndex(request.getPageIndex());
        searchShopDataRequest.setPageSize(request.getPageSize());
        searchShopDataRequest.setCondition(buildCondition(request.getCondition()));
        searchShopDataRequest.setSortFields(buildSortField(request.getSortFields()));
        return searchShopDataRequest;
    }

    private static SearchShopDataRequest.Condition buildCondition(SearchShopBizRequest.Condition request) {

        SearchShopDataRequest.Condition condition = new SearchShopDataRequest.Condition();
        condition.setKeyword(request.getKeyword());
        condition.setManagementTypes(request.getManagementTypes());
        condition.setBusinessTypes(request.getBusinessTypes());
        if (request.getEnabled() != null) {
            condition.setEnabled(request.getEnabled() ? 1 : 0);
        }
        return condition;
    }

    private static List<SearchShopDataRequest.SortField> buildSortField(List<SearchShopBizRequest.SortField> requests) {
        List<SearchShopDataRequest.SortField> sortFields = new ArrayList<>();
        if (requests != null) {
            for (SearchShopBizRequest.SortField request : requests) {
                SearchShopDataRequest.SortField sortField = new SearchShopDataRequest.SortField();
                sortField.setField(request.getField());
                String asc = request.getAsc() ? "asc" : "desc";
                sortField.setAsc(asc);
                sortFields.add(sortField);
                sortField = null;
            }
        }
        return sortFields;
    }
}
