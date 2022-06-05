package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.order.api.http.model.request.PrepareCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.PrepareCateringOrderBizRequest;

import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/3 17:13
 * @ClassName: PrepareCateringOrderBizRequestConverter
 *
 */
public class PrepareCateringOrderRequestConverter {

    public static PrepareCateringOrderBizRequest toPrepareCateringOrderBizRequest(Long tenantId, Long orderId, PrepareCateringOrderHttpRequest request){
        PrepareCateringOrderBizRequest bizRequest = new PrepareCateringOrderBizRequest();
        bizRequest.setTenantId(tenantId);
        bizRequest.setOrderId(orderId);
        bizRequest.setVersion(request.getVersion());
        bizRequest.getItems().addAll(request.getItems().stream().map(PrepareCateringOrderRequestConverter::buildItem).collect(Collectors.toList()));

        return bizRequest;
    }

    private static PrepareCateringOrderBizRequest.Item buildItem(PrepareCateringOrderHttpRequest.Item item){
        PrepareCateringOrderBizRequest.Item bizItem = new PrepareCateringOrderBizRequest.Item();
        bizItem.setSeqNo(item.getSeqNo());
        bizItem.setVersion(item.getVersion());

        return bizItem;
    }
}
