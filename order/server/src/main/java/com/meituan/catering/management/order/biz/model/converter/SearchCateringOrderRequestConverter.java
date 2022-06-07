package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.order.api.http.model.request.SearchCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.SearchCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.request.SearchCateringOrderDataRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/30 15:58
 * @ClassName: SearchCateringOrderBizRequestConverter
 */
public class SearchCateringOrderRequestConverter {
    public static SearchCateringOrderBizRequest toSearchCateringOrderBizRequest(SearchCateringOrderHttpRequest request){
        SearchCateringOrderBizRequest searchCateringOrderBizRequest = new SearchCateringOrderBizRequest();

        searchCateringOrderBizRequest.setPageIndex(request.getPageIndex());
        searchCateringOrderBizRequest.setPageSize(request.getPageSize());

        searchCateringOrderBizRequest.getCondition().setStatus(request.getCondition().getStatus());
        searchCateringOrderBizRequest.getCondition().setCustomerCount(request.getCondition().getCustomerCount());
        searchCateringOrderBizRequest.getCondition().setTableNo(request.getCondition().getTableNo());
        searchCateringOrderBizRequest.getCondition().getTotalPrice().setFrom(request.getCondition().getTotalPrice().getFrom());
        searchCateringOrderBizRequest.getCondition().getTotalPrice().setTo(request.getCondition().getTotalPrice().getTo());

        List<SearchCateringOrderBizRequest.SortField> sortFields = searchCateringOrderBizRequest.getSortFields();
        List<SearchCateringOrderHttpRequest.SortField> sortFieldsRequest = request.getSortFields();
        for (SearchCateringOrderHttpRequest.SortField sortField : sortFieldsRequest) {
            sortFields.add(buildSortField(sortField));
        }
        return searchCateringOrderBizRequest;
    }

    public static SearchCateringOrderDataRequest toSearchCateringOrderDataRequest(Long tenantId, SearchCateringOrderBizRequest request){
        SearchCateringOrderDataRequest searchCateringOrderDataRequest = new SearchCateringOrderDataRequest();
        searchCateringOrderDataRequest.setTenantId(tenantId);
        searchCateringOrderDataRequest.setPageIndex(request.getPageIndex());
        searchCateringOrderDataRequest.setPageSize(request.getPageSize());

        searchCateringOrderDataRequest.getCondition().setCustomerCount(request.getCondition().getCustomerCount());
        searchCateringOrderDataRequest.getCondition().setStatus(request.getCondition().getStatus());
        searchCateringOrderDataRequest.getCondition().setTableNo(request.getCondition().getTableNo());

        searchCateringOrderDataRequest.getCondition().getTotalPrice().setFrom(request.getCondition().getTotalPrice().getFrom());
        searchCateringOrderDataRequest.getCondition().getTotalPrice().setTo(request.getCondition().getTotalPrice().getTo());

        List<SearchCateringOrderDataRequest.SortField> sortFields = searchCateringOrderDataRequest.getSortFields();
        List<SearchCateringOrderBizRequest.SortField> sortFieldsRequest = request.getSortFields();
        for (SearchCateringOrderBizRequest.SortField sortField : sortFieldsRequest) {
            sortFields.add(buildSortField(sortField));
        }
        return searchCateringOrderDataRequest;

    }

    private static SearchCateringOrderDataRequest.SortField buildSortField(SearchCateringOrderBizRequest.SortField request){
        SearchCateringOrderDataRequest.SortField sortField = new SearchCateringOrderDataRequest.SortField();
        sortField.setField(request.getField());
        sortField.setAsc(request.getAsc()?"asc":"desc");

        return sortField;
    }
    public static SearchCateringOrderBizRequest.SortField buildSortField(SearchCateringOrderHttpRequest.SortField request){
        SearchCateringOrderBizRequest.SortField sortField = new SearchCateringOrderBizRequest.SortField();
        sortField.setField(request.getField());
        sortField.setAsc(request.getAsc());

        return sortField;
    }
}
