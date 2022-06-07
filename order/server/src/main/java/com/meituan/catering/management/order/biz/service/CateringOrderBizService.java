package com.meituan.catering.management.order.biz.service;

import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.request.*;


/**
 * 订单管理服务
 */
public interface CateringOrderBizService {

    CateringOrderBO insert(Long tenantId, Long userId, PlaceCateringOrderBizRequest request);

    CateringOrderBO prepare(PrepareCateringOrderBizRequest request);

    CateringOrderBO produce(ProduceCateringOrderBizRequest request);

    CateringOrderBO bill(BillCateringOrderBizRequest request);

    CateringOrderBO adjust(AdjustCateringOrderBizRequest request);
}
