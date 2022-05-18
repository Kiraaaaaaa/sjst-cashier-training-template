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
        if (request.getPageIndex() != null) {
            searchShopDataRequest.setPageIndex(request.getPageIndex());
        }
        if (request.getPageSize() != null) {
            searchShopDataRequest.setPageSize(request.getPageSize());
        }
        if (buildCondition(request.getCondition()) != null) {
            searchShopDataRequest.setCondition(buildCondition(request.getCondition()));
        }
        if (buildSortField(request.getSortFields()) != null) {
            searchShopDataRequest.setSortFields(buildSortField(request.getSortFields()));
        }
        return searchShopDataRequest;
    }

    private static SearchShopDataRequest.Condition buildCondition(SearchShopBizRequest.Condition request) {
        if (request == null) {
            return null;
        }
        SearchShopDataRequest.Condition condition = new SearchShopDataRequest.Condition();
        if (request.getKeyword() != null) {
            condition.setKeyword(request.getKeyword());
        }
        if (request.getManagementTypes() != null && request.getManagementTypes().size()!=0) {
            condition.setManagementTypes(request.getManagementTypes());
        }
        if (request.getBusinessTypes() != null && request.getBusinessTypes().size()!=0) {
            condition.setBusinessTypes(request.getBusinessTypes());
        }
        if (request.getEnabled() != null) {
            condition.setEnabled(request.getEnabled() ? 1 : 0);
        }

        return condition;
    }

    private static List<SearchShopDataRequest.SortField> buildSortField(List<SearchShopBizRequest.SortField> requests) {
        if (requests == null || requests.size() == 0) {
            return null;
        }
        List<SearchShopDataRequest.SortField> sortFields = new ArrayList<>();
        SearchShopDataRequest.SortField sortField = new SearchShopDataRequest.SortField();
        for (SearchShopBizRequest.SortField request : requests) {
            sortField.setField(request.getField());
            String asc = request.getAsc() ? "asc" : "desc";
            sortField.setAsc(asc);
            sortFields.add(sortField);
        }
        return sortFields;
    }
}
