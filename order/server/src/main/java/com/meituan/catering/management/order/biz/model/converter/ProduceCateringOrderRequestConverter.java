package com.meituan.catering.management.order.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.ProduceCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.ProduceCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/5 15:07
 * @ClassName: ProduceCateringOrderRequestConverter
 */
public class ProduceCateringOrderRequestConverter {

    public static ProduceCateringOrderBizRequest toProduceCateringOrderBizRequest(
            Long tenantId, Long userId, Long orderId, ProduceCateringOrderHttpRequest request) {

        ProduceCateringOrderBizRequest bizRequest = new ProduceCateringOrderBizRequest();
        bizRequest.setTenantId(tenantId);
        bizRequest.setUserId(userId);
        bizRequest.setLastModifiedAt(new Date());
        bizRequest.setOrderId(orderId);
        bizRequest.setVersion(request.getVersion());
        bizRequest.getItems().addAll(request.getItems().stream().map(ProduceCateringOrderRequestConverter::buildItem).collect(Collectors.toList()));

        return bizRequest;
    }

    public static CateringOrderDO toCateringOrderDO(
            List<CateringOrderItemAccessoryDO> accessoryDOS,
            List<CateringOrderItemDO> itemDOS, ProduceCateringOrderBizRequest request) {
        CateringOrderDO cateringOrderDO = new CateringOrderDO();

        cateringOrderDO.setTenantId(request.getTenantId());
        cateringOrderDO.setId(request.getOrderId());
        cateringOrderDO.setVersion(request.getVersion());
        cateringOrderDO.setLastModifiedBy(request.getUserId());
        cateringOrderDO.setStatus(CateringOrderStatusEnum.PREPARED);
        cateringOrderDO.setLastModifiedAt(request.getLastModifiedAt());

        int code = CateringOrderStatusEnum.PREPARED.getCode();
        for (CateringOrderItemAccessoryDO accessoryDO : accessoryDOS) {
            if (!Objects.equals(accessoryDO.getStatus(),CateringOrderItemAccessoryStatusEnum.PREPARED)
                    && !Objects.equals(accessoryDO.getStatus(),CateringOrderItemAccessoryStatusEnum.CANCELLED)){
                code = CateringOrderStatusEnum.PREPARING.getCode();
            }
        }
        for (CateringOrderItemDO itemDO : itemDOS) {
            if (!Objects.equals(itemDO.getStatus(),CateringOrderItemStatusEnum.PREPARED)
                    && !Objects.equals(itemDO.getStatus(),CateringOrderItemStatusEnum.CANCELLED)) {
                code = CateringOrderStatusEnum.PREPARING.getCode();
            }
        }
        cateringOrderDO.setStatus(CateringOrderStatusEnum.getEnum(code));

        return cateringOrderDO;
    }

    public static List<CateringOrderItemDO> toCateringOrderItemDO(
            List<CateringOrderItemDO> itemDOS, ProduceCateringOrderBizRequest request) {

        LinkedList<CateringOrderItemDO> list = Lists.newLinkedList();
        for (ProduceCateringOrderBizRequest.Item item : request.getItems()) {
            CateringOrderItemDO orderItemDO = new CateringOrderItemDO();
            orderItemDO.setTenantId(request.getTenantId());
            orderItemDO.setVersion(item.getVersion());
            orderItemDO.setOrderId(request.getOrderId());
            orderItemDO.setSeqNo(item.getSeqNo());
            for (CateringOrderItemDO itemDO : itemDOS) {
                if (Objects.equals(item.getSeqNo(), itemDO.getSeqNo())) {
                    BigDecimal add = itemDO.getProduceQuantity().add(item.getQuantityOnProduce());
                    orderItemDO.setProduceQuantity(add);
                    if (itemDO.getLatestQuantity().compareTo(add) == 0) {
                        orderItemDO.setStatus(CateringOrderItemStatusEnum.PREPARED);
                    }
                }
            }
            list.add(orderItemDO);
            orderItemDO = null;
        }
        return list;

    }

    public static List<CateringOrderItemAccessoryDO> toCateringOrderItemAccessoryDO(
            List<CateringOrderItemAccessoryDO> accessoryDOS, ProduceCateringOrderBizRequest request) {
        LinkedList<CateringOrderItemAccessoryDO> list = Lists.newLinkedList();
        for (ProduceCateringOrderBizRequest.Item item : request.getItems()) {
            for (ProduceCateringOrderBizRequest.Item.Accessory accessory : item.getAccessories()) {
                for (CateringOrderItemAccessoryDO accessoryDO : accessoryDOS) {
                    if (Objects.equals(accessory.getSeqNo(), accessoryDO.getSeqNo())) {
                        CateringOrderItemAccessoryDO itemAccessoryDO = new CateringOrderItemAccessoryDO();
                        itemAccessoryDO.setTenantId(request.getTenantId());
                        itemAccessoryDO.setVersion(accessory.getVersion());
                        itemAccessoryDO.setOrderItemId(accessoryDO.getOrderItemId());
                        itemAccessoryDO.setSeqNo(accessory.getSeqNo());
                        BigDecimal add = accessory.getQuantityOnProduce().add(accessoryDO.getProduceQuantity());
                        itemAccessoryDO.setProduceQuantity(add);
                        if (accessoryDO.getLatestQuantity().compareTo(add)==0){
                            itemAccessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.PREPARED);
                        }
                        list.add(itemAccessoryDO);
                    }
                }
            }
        }
        return list;
    }


    private static ProduceCateringOrderBizRequest.Item buildItem(ProduceCateringOrderHttpRequest.Item request) {
        ProduceCateringOrderBizRequest.Item item = new ProduceCateringOrderBizRequest.Item();
        item.setQuantityOnProduce(request.getQuantityOnProduce());
        item.setVersion(request.getVersion());
        item.setSeqNo(request.getSeqNo());
        item.getAccessories().addAll(request.getAccessories().stream().map(ProduceCateringOrderRequestConverter::buildAccessory).collect(Collectors.toList()));

        return item;
    }

    private static ProduceCateringOrderBizRequest.Item.Accessory buildAccessory(ProduceCateringOrderHttpRequest.Item.Accessory request) {
        ProduceCateringOrderBizRequest.Item.Accessory accessory = new ProduceCateringOrderBizRequest.Item.Accessory();
        accessory.setQuantityOnProduce(request.getQuantityOnProduce());
        accessory.setSeqNo(request.getSeqNo());
        accessory.setVersion(request.getVersion());

        return accessory;
    }
}
