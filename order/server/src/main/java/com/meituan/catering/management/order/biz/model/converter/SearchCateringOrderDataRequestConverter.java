package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.order.biz.model.request.SearchCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.request.SearchCateringOrderDataRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/30 16:40
 * @ClassName: SearchCateringOrderDataRequestConverter
 */
public class SearchCateringOrderDataRequestConverter {
    public static SearchCateringOrderDataRequest toSearchCateringOrderDataRequest(Long tenantId,SearchCateringOrderBizRequest request){
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
}
