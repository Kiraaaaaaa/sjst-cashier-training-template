package com.meituan.catering.management.product.dao.converter;

import com.meituan.catering.management.product.biz.model.request.SearchProductBizRequest;
import com.meituan.catering.management.product.dao.model.request.SearchProductDataRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 14:10
 * @ClassName: SearchProductDataRequestConverter
 */
public class SearchProductDataRequestConverter {

    public static SearchProductDataRequest toSearchProductDataRequest(SearchProductBizRequest request){
        SearchProductDataRequest searchProductDataRequest = new SearchProductDataRequest();
        searchProductDataRequest.setPageSize(request.getPageSize());
        searchProductDataRequest.setTenantId(request.getTenantId());
        searchProductDataRequest.setPageIndex(request.getPageIndex());
        searchProductDataRequest.setUserId(request.getUserId());

        searchProductDataRequest.getCondition().setEnabled(request.getCondition().getEnabled());
        searchProductDataRequest.getCondition().setName(request.getCondition().getName());
        searchProductDataRequest.getCondition().setUnitOfMeasure(request.getCondition().getUnitOfMeasure());
        searchProductDataRequest.getCondition().getUnitPrice().setFrom(request.getCondition().getUnitPrice().getFrom());
        searchProductDataRequest.getCondition().getUnitPrice().setTo(request.getCondition().getUnitPrice().getTo());

        List<SearchProductBizRequest.SortField> sortFields = request.getSortFields();
        for (SearchProductBizRequest.SortField sortField : sortFields) {
            SearchProductDataRequest.SortField sortFieldData = new SearchProductDataRequest.SortField();
            sortFieldData.setField(sortField.getField());
            sortFieldData.setAsc(sortField.getAsc()?"asc":"desc");
            searchProductDataRequest.getSortFields().add(sortFieldData);
            sortFieldData = null;
        }
        return searchProductDataRequest;
    }
}
