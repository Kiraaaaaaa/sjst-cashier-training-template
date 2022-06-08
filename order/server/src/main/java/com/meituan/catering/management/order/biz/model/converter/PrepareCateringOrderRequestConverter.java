package com.meituan.catering.management.order.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.PrepareCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.PrepareCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/3 17:13
 * @ClassName: PrepareCateringOrderBizRequestConverter
 */
public class PrepareCateringOrderRequestConverter {

    public static PrepareCateringOrderBizRequest toPrepareCateringOrderBizRequest(Long tenantId,Long userId, Long orderId, PrepareCateringOrderHttpRequest request) {
        PrepareCateringOrderBizRequest bizRequest = new PrepareCateringOrderBizRequest();
        bizRequest.setTenantId(tenantId);
        bizRequest.setUserId(userId);
        bizRequest.setOrderId(orderId);
        bizRequest.setVersion(request.getVersion());
        bizRequest.setLastModifiedAt(new Date());
        bizRequest.getItems().addAll(request.getItems().stream().map(PrepareCateringOrderRequestConverter::buildItem).collect(Collectors.toList()));
        return bizRequest;
    }

    public static CateringOrderDO toCateringOrderDO(PrepareCateringOrderBizRequest request) {

        CateringOrderDO cateringOrderDO = new CateringOrderDO();
        cateringOrderDO.setId(request.getOrderId());
        cateringOrderDO.setTenantId(request.getTenantId());
        cateringOrderDO.setVersion(request.getVersion());
        cateringOrderDO.setLastModifiedBy(request.getUserId());
        cateringOrderDO.setLastModifiedAt(request.getLastModifiedAt());
        cateringOrderDO.setStatus(CateringOrderStatusEnum.PREPARING);
        return cateringOrderDO;
    }

    public static List<CateringOrderItemDO> toCateringOrderItemDO(PrepareCateringOrderBizRequest request){
        LinkedList<CateringOrderItemDO> list = Lists.newLinkedList();
        for (PrepareCateringOrderBizRequest.Item item : request.getItems()) {
            CateringOrderItemDO itemDO = new CateringOrderItemDO();
            itemDO.setTenantId(request.getTenantId());
            itemDO.setVersion(item.getVersion());
            itemDO.setOrderId(request.getOrderId());
            itemDO.setSeqNo(item.getSeqNo());
            itemDO.setStatus(CateringOrderItemStatusEnum.PREPARING);
            list.add(itemDO);
            itemDO = null;
        }
        return list;
    }

    private static PrepareCateringOrderBizRequest.Item buildItem(PrepareCateringOrderHttpRequest.Item item) {
        PrepareCateringOrderBizRequest.Item bizItem = new PrepareCateringOrderBizRequest.Item();
        bizItem.setSeqNo(item.getSeqNo());
        bizItem.setVersion(item.getVersion());

        return bizItem;
    }
}
