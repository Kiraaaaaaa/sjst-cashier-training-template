package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.shop.api.http.model.request.SearchShopHttpRequest;
import com.meituan.catering.management.shop.biz.model.request.SearchShopBizRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/16 16:06
 * @ClassName: SearchShopBizRequestConverter
 */
public class SearchShopBizRequestConverter {
    public static SearchShopBizRequest toSearchShopBizRequest(SearchShopHttpRequest request) {
        SearchShopBizRequest searchShopBizRequest = new SearchShopBizRequest();
        searchShopBizRequest.setPageIndex(request.getPageIndex());
        searchShopBizRequest.setPageSize(request.getPageSize());
        searchShopBizRequest.setCondition(buildCondition(request.getCondition()));
        searchShopBizRequest.setSortFields(buildSortField(request.getSortFields()));
        return searchShopBizRequest;
    }

    private static SearchShopBizRequest.Condition buildCondition(SearchShopHttpRequest.Condition request) {
        SearchShopBizRequest.Condition condition = new SearchShopBizRequest.Condition();
        condition.setKeyword(request.getKeyword());
        condition.setManagementTypes(request.getManagementTypes());
        condition.setBusinessTypes(request.getBusinessTypes());
        condition.setEnabled(request.getEnabled());
        return condition;
    }

    private static List<SearchShopBizRequest.SortField> buildSortField(List<SearchShopHttpRequest.SortField> requests) {
        List<SearchShopBizRequest.SortField> sortFields = new ArrayList<>();
        for (SearchShopHttpRequest.SortField request : requests) {
            SearchShopBizRequest.SortField sortField =
                    new SearchShopBizRequest.SortField(request.getField(), request.getAsc());
            sortFields.add(sortField);
            sortField = null;
        }
        return sortFields;
    }
}
