package com.meituan.catering.management.product.biz.model.converter;

import com.meituan.catering.management.product.api.http.model.request.SearchProductHttpRequest;
import com.meituan.catering.management.product.biz.model.request.SearchProductBizRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 11:39
 * @ClassName: SearchProductBizRequestConverter
 */
public class SearchProductBizRequestConverter {
    public static SearchProductBizRequest toSearchProductBizRequest(Long tenantId,Long userId,SearchProductHttpRequest request){

        SearchProductBizRequest searchProductBizRequest = new SearchProductBizRequest();
        searchProductBizRequest.setTenantId(tenantId);
        searchProductBizRequest.setUserId(userId);
        searchProductBizRequest.setPageSize(request.getPageSize());
        searchProductBizRequest.setPageIndex(request.getPageIndex());

        searchProductBizRequest.getCondition().setName(request.getCondition().getName());
        searchProductBizRequest.getCondition().setEnabled(request.getCondition().getEnabled());
        searchProductBizRequest.getCondition().setUnitOfMeasure(request.getCondition().getUnitOfMeasure());
        searchProductBizRequest.getCondition().getUnitPrice().setFrom(request.getCondition().getUnitPrice().getFrom());
        searchProductBizRequest.getCondition().getUnitPrice().setTo(request.getCondition().getUnitPrice().getTo());

        List<SearchProductHttpRequest.SortField> sortFields = request.getSortFields();
        for (SearchProductHttpRequest.SortField sortField : sortFields) {
            SearchProductBizRequest.SortField sortFieldBiz = new SearchProductBizRequest.SortField();
            sortFieldBiz.setField(sortField.getField());
            sortFieldBiz.setAsc(sortField.getAsc());
            searchProductBizRequest.getSortFields().add(sortFieldBiz);
            sortFieldBiz = null;
        }
        return searchProductBizRequest;
    }
}
