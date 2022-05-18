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
    public static SearchShopBizRequest toSearchShopBizRequest(SearchShopHttpRequest request){
        SearchShopBizRequest searchShopBizRequest = new SearchShopBizRequest();

        if (request.getPageIndex()!=null){
            searchShopBizRequest.setPageIndex(request.getPageIndex());
        }
        if (request.getPageSize()!=null){
            searchShopBizRequest.setPageSize(request.getPageSize());
        }
        if (buildCondition(request.getCondition())!=null){
            searchShopBizRequest.setCondition(buildCondition(request.getCondition()));
        }
        if (buildSortField(request.getSortFields())!=null){
            searchShopBizRequest.setSortFields(buildSortField(request.getSortFields()));
        }


        return searchShopBizRequest;
    }

    private static SearchShopBizRequest.Condition buildCondition(SearchShopHttpRequest.Condition request){
        if (request==null){
            return null;
        }
        SearchShopBizRequest.Condition condition = new SearchShopBizRequest.Condition();
        if (request.getKeyword()!=null){
            condition.setKeyword(request.getKeyword());
        }
        if (request.getManagementTypes()!=null){
            condition.setManagementTypes(request.getManagementTypes());
        }
        if (request.getBusinessTypes()!=null){
            condition.setBusinessTypes(request.getBusinessTypes());
        }
        if (request.getEnabled()!=null){
            condition.setEnabled(request.getEnabled());
        }
        return condition;
    }

    private static List<SearchShopBizRequest.SortField> buildSortField(List<SearchShopHttpRequest.SortField> requests){
        if (requests==null || requests.size()==0){
            return null;
        }
        List<SearchShopBizRequest.SortField> sortFields = new ArrayList<>();
        SearchShopBizRequest.SortField sortField = new SearchShopBizRequest.SortField();
        for (SearchShopHttpRequest.SortField request : requests) {
            sortField.setField(request.getField());
            sortField.setAsc(request.getAsc());
            sortFields.add(sortField);
        }
        return sortFields;
    }
}
