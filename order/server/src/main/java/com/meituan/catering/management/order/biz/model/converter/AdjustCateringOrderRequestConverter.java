package com.meituan.catering.management.order.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.AdjustCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.AdjustCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/6 15:25
 * @ClassName: AdjustCateringOrderRequestConverter
 */
public class AdjustCateringOrderRequestConverter {

    public static AdjustCateringOrderBizRequest toAdjustCateringOrderBizRequest(Long tenantId, Long userId, Long orderId, AdjustCateringOrderHttpRequest request) {
        AdjustCateringOrderBizRequest bizRequest = new AdjustCateringOrderBizRequest();
        bizRequest.setTenantId(tenantId);
        bizRequest.setOrderId(orderId);
        bizRequest.setVersion(request.getVersion());
        bizRequest.setUserId(userId);
        bizRequest.setLastModifiedAt(new Date());
        bizRequest.getItems().addAll(request.getItems().stream().map(AdjustCateringOrderRequestConverter::buildItem).collect(Collectors.toList()));
        return bizRequest;
    }

    public static CateringOrderDO toCateringOrderDO(
            List<CateringOrderItemAccessoryDO> accessoryDOS, List<CateringOrderItemDO> itemDOS, AdjustCateringOrderBizRequest request) {

        CateringOrderDO cateringOrderDO = new CateringOrderDO();

        int code = itemDOS.get(0).getStatus().getCode();
        for (CateringOrderItemDO itemDO : itemDOS) {
            if (code > itemDO.getStatus().getCode()) {
                code = itemDO.getStatus().getCode();
            }
        }
        for (CateringOrderItemAccessoryDO accessoryDO : accessoryDOS) {
            if (code > accessoryDO.getStatus().getCode()) {
                code = accessoryDO.getStatus().getCode();
            }
        }
        cateringOrderDO.setId(request.getOrderId());
        cateringOrderDO.setStatus(CateringOrderStatusEnum.getEnum(code));
        cateringOrderDO.setTenantId(request.getTenantId());
        cateringOrderDO.setVersion(request.getVersion());
        cateringOrderDO.setLastModifiedBy(request.getUserId());
        cateringOrderDO.setLastModifiedAt(request.getLastModifiedAt());

        return cateringOrderDO;
    }

    public static CateringOrderItemDO toCateringOrderItemDO(
            Long tenantId, Long orderId,
            CateringOrderItemDO itemDO, ProductDetailRemoteResponse productDetail,
            AdjustCateringOrderBizRequest.Item item) {
        CateringOrderItemDO cateringOrderItemDO = new CateringOrderItemDO();
        cateringOrderItemDO.setTenantId(tenantId);
        cateringOrderItemDO.setVersion(item.getVersion());
        cateringOrderItemDO.setOrderId(orderId);
        cateringOrderItemDO.setSeqNo(item.getSeqNo());
        //说明是插入
        if (Objects.nonNull(item.getProductId())) {
            cateringOrderItemDO.setStatus(CateringOrderItemStatusEnum.PLACED);
            cateringOrderItemDO.setPlaceQuantity(item.getQuantityOnAdjustment());
            cateringOrderItemDO.setProduceQuantity(BigDecimal.ZERO);
            cateringOrderItemDO.setLatestQuantity(item.getQuantityOnAdjustment());
            cateringOrderItemDO.setProductId(item.getProductId());
            cateringOrderItemDO.setProductNameOnPlace(productDetail.getName());
            cateringOrderItemDO.setProductUnitPriceOnPlace(BigDecimal.valueOf(productDetail.getUnitPrice()));
            cateringOrderItemDO.setProductUnitOfMeasureOnPlace(productDetail.getUnitOfMeasure());
            if (Objects.nonNull(item.getProductMethodId())) {
                for (ProductDetailRemoteResponse.MethodGroup methodGroup : productDetail.getMethodGroups()) {
                    for (ProductDetailRemoteResponse.MethodGroup.Option option : methodGroup.getOptions()) {
                        if (Objects.equals(item.getProductMethodId(), option.getId())) {
                            cateringOrderItemDO.setProductMethodId(option.getId());
                            cateringOrderItemDO.setProductMethodNameOnPlace(option.getName());
                            cateringOrderItemDO.setProductMethodGroupNameOnPlace(methodGroup.getName());
                        }
                    }
                }
            }
        }
        //说明是更新
        if (Objects.isNull(item.getProductId())) {
            cateringOrderItemDO.setId(itemDO.getId());
            BigDecimal latest = itemDO.getLatestQuantity().add(item.getQuantityOnAdjustment());
            if (BigDecimal.ZERO.compareTo(latest) < 0) {
                if (latest.compareTo(itemDO.getProduceQuantity()) <= 0) {
                    cateringOrderItemDO.setLatestQuantity(latest);
                    cateringOrderItemDO.setProduceQuantity(latest);
                    cateringOrderItemDO.setStatus(CateringOrderItemStatusEnum.PLACED);
                }
                if (latest.compareTo(itemDO.getProduceQuantity()) > 0) {
                    cateringOrderItemDO.setLatestQuantity(latest);
                    cateringOrderItemDO.setStatus(CateringOrderItemStatusEnum.PREPARING);
                }
            }
            if (BigDecimal.ZERO.compareTo(latest) == 0) {
                cateringOrderItemDO.setLatestQuantity(latest);
                cateringOrderItemDO.setProduceQuantity(latest);
                cateringOrderItemDO.setStatus(CateringOrderItemStatusEnum.CANCELLED);
            }
        }

        return cateringOrderItemDO;
    }

    public static CateringOrderItemAccessoryDO toCateringOrderItemAccessoryDO(
            Long tenantId, Long orderItemId,
            CateringOrderItemAccessoryDO accessoryDO, ProductDetailRemoteResponse.AccessoryGroup accessoryDetail,
            AdjustCateringOrderBizRequest.Item.Accessory accessory) {

        CateringOrderItemAccessoryDO cateringOrderItemAccessoryDO = new CateringOrderItemAccessoryDO();
        cateringOrderItemAccessoryDO.setTenantId(tenantId);
        cateringOrderItemAccessoryDO.setVersion(accessory.getVersion());
        cateringOrderItemAccessoryDO.setOrderItemId(orderItemId);
        cateringOrderItemAccessoryDO.setSeqNo(accessory.getSeqNo());
        //说明是更新
        if (Objects.isNull(accessory.getProductAccessoryId())) {
            cateringOrderItemAccessoryDO.setId(accessoryDO.getId());
            BigDecimal latest = accessoryDO.getLatestQuantity().add(accessory.getQuantityOnAdjustment());
            if (BigDecimal.ZERO.compareTo(latest) < 0) {
                if (latest.compareTo(accessoryDO.getProduceQuantity()) <= 0) {
                    cateringOrderItemAccessoryDO.setLatestQuantity(latest);
                    cateringOrderItemAccessoryDO.setProduceQuantity(latest);
                    cateringOrderItemAccessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.PLACED);
                }
                if (latest.compareTo(accessoryDO.getProduceQuantity()) > 0) {
                    cateringOrderItemAccessoryDO.setLatestQuantity(latest);
                    cateringOrderItemAccessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.PREPARING);
                }
            }
            if (BigDecimal.ZERO.compareTo(latest) == 0) {
                cateringOrderItemAccessoryDO.setLatestQuantity(latest);
                cateringOrderItemAccessoryDO.setProduceQuantity(latest);
                cateringOrderItemAccessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.CANCELLED);
            }
        }
        //说明是插入
        if (Objects.nonNull(accessory.getProductAccessoryId())) {
            cateringOrderItemAccessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.PLACED);
            cateringOrderItemAccessoryDO.setLatestQuantity(accessory.getQuantityOnAdjustment());
            cateringOrderItemAccessoryDO.setProduceQuantity(BigDecimal.ZERO);
            cateringOrderItemAccessoryDO.setPlaceQuantity(accessory.getQuantityOnAdjustment());
            for (ProductDetailRemoteResponse.AccessoryGroup.Option option : accessoryDetail.getOptions()) {
                if (Objects.equals(option.getId(), accessory.getProductAccessoryId())) {
                    cateringOrderItemAccessoryDO.setProductAccessoryId(option.getId());
                    cateringOrderItemAccessoryDO.setProductAccessoryNameOnPlace(option.getName());
                    cateringOrderItemAccessoryDO.setProductAccessoryGroupNameOnPlace(accessoryDetail.getName());
                    cateringOrderItemAccessoryDO.setProductAccessoryUnitPriceOnPlace(BigDecimal.valueOf(option.getUnitPrice()));
                    cateringOrderItemAccessoryDO.setProductAccessoryUnitOfMeasureOnPlace(option.getUnitOfMeasure());
                }
            }
        }
        return cateringOrderItemAccessoryDO;
    }

    private static AdjustCateringOrderBizRequest.Item.Accessory buildAccessory(AdjustCateringOrderHttpRequest.Item.Accessory httpAccessory) {
        AdjustCateringOrderBizRequest.Item.Accessory bizAccessory = new AdjustCateringOrderBizRequest.Item.Accessory();
        bizAccessory.setSeqNo(httpAccessory.getSeqNo());
        bizAccessory.setVersion(httpAccessory.getVersion());
        bizAccessory.setQuantityOnAdjustment(httpAccessory.getQuantityOnAdjustment());
        bizAccessory.setProductAccessoryId(httpAccessory.getProductAccessoryId());
        return bizAccessory;
    }

    private static AdjustCateringOrderBizRequest.Item buildItem(AdjustCateringOrderHttpRequest.Item httpItem) {
        AdjustCateringOrderBizRequest.Item bizItem = new AdjustCateringOrderBizRequest.Item();
        bizItem.setSeqNo(httpItem.getSeqNo());
        bizItem.setVersion(httpItem.getVersion());
        bizItem.setQuantityOnAdjustment(httpItem.getQuantityOnAdjustment());
        bizItem.setProductId(httpItem.getProductId());
        bizItem.setProductMethodId(httpItem.getProductMethodId());
        bizItem.getAccessories().addAll(httpItem.getAccessories().stream().map(AdjustCateringOrderRequestConverter::buildAccessory).collect(Collectors.toList()));
        return bizItem;
    }
}
