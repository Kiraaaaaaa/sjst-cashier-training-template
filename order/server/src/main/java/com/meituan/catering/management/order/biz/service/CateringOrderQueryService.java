package com.meituan.catering.management.order.biz.service;

import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.request.PlaceCateringOrderBizRequest;
import com.meituan.catering.management.order.biz.model.request.SearchCateringOrderBizRequest;
import com.meituan.catering.management.order.biz.model.response.SearchCateringOrderBizResponse;


/**
 * 订单查询服务
 */
public interface CateringOrderQueryService {

    CateringOrderBO findById(Long tenantId,Long orderId);

    SearchCateringOrderBizResponse searchForPage(Long tenantId, SearchCateringOrderBizRequest request);
}
