package com.meituan.catering.management.order.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.PlaceCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.PlaceCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;
import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/1 15:04
 * @ClassName: PlaceCateringOrderBizRequestConverter
 */
public class PlaceCateringOrderRequestConverter {

    public static PlaceCateringOrderBizRequest toPlaceCateringOrderBizRequest(Long tenantId, Long userId, PlaceCateringOrderHttpRequest request) {
        PlaceCateringOrderBizRequest bizRequest = new PlaceCateringOrderBizRequest();
        bizRequest.setTenantId(tenantId);
        bizRequest.setUserId(userId);
        bizRequest.setShopBusinessNo(request.getShopBusinessNo());
        bizRequest.setTableNo(request.getTableNo());
        bizRequest.setCustomerCount(request.getCustomerCount());
        bizRequest.setTotalPrice(request.getTotalPrice());
        bizRequest.setComment(request.getComment());

        List<PlaceCateringOrderBizRequest.Item> items = bizRequest.getItems();
        List<PlaceCateringOrderHttpRequest.Item> httpItem = request.getItems();
        for (PlaceCateringOrderHttpRequest.Item item : httpItem) {
            items.add(buildItem(item));
        }
        return bizRequest;
    }

    public static CateringOrderDO toCateringOrderDO(Long tenantId, Long userId, PlaceCateringOrderBizRequest request, ShopDetailRemoteResponse shopDetail) {

        CateringOrderDO cateringOrderDO = new CateringOrderDO();
        if (CollectionUtils.isEmpty(request.getItems())) {
            cateringOrderDO.setStatus(CateringOrderStatusEnum.DRAFT);
        } else {
            cateringOrderDO.setStatus(CateringOrderStatusEnum.PLACED);
        }
        cateringOrderDO.setShopId(shopDetail.getId());
        cateringOrderDO.setShopBusinessNo(shopDetail.getBusinessNo());
        cateringOrderDO.setShopNameOnPlace(shopDetail.getName());
        cateringOrderDO.setTableNo(request.getTableNo());
        cateringOrderDO.setCustomerCount(request.getCustomerCount().toString());
        cateringOrderDO.setTotalPrice(request.getTotalPrice());
        cateringOrderDO.setComment(request.getComment());
        cateringOrderDO.setTenantId(tenantId);
        cateringOrderDO.setVersion(1);
        cateringOrderDO.setCreatedBy(userId);
        cateringOrderDO.setCreatedAt(new Date());
        cateringOrderDO.setLastModifiedBy(userId);
        cateringOrderDO.setLastModifiedAt(new Date());

        return cateringOrderDO;
    }

    public static List<CateringOrderItemDO> toCateringOrderItemDO(CateringOrderDO cateringOrderDO, List<PlaceCateringOrderBizRequest.Item> itemDOS, List<ProductDetailRemoteResponse> productDetailList) {
        ArrayList<CateringOrderItemDO> list = Lists.newArrayList();
        itemDOS.forEach(item -> {
            CateringOrderItemDO cateringOrderItemDO = new CateringOrderItemDO();
            cateringOrderItemDO.setTenantId(cateringOrderDO.getTenantId());
            cateringOrderItemDO.setVersion(1);
            cateringOrderItemDO.setOrderId(cateringOrderDO.getId());
            cateringOrderItemDO.setSeqNo(item.getSeqNo());
            cateringOrderItemDO.setStatus(CateringOrderItemStatusEnum.PLACED);
            cateringOrderItemDO.setPlaceQuantity(item.getQuantity());
            cateringOrderItemDO.setProduceQuantity(BigDecimal.ZERO);
            cateringOrderItemDO.setLatestQuantity(item.getQuantity());
            cateringOrderItemDO.setProductId(item.getProductId());
            productDetailList.forEach(productDetail -> {
                if (Objects.equals(item.getProductId(), productDetail.getId())) {
                    cateringOrderItemDO.setProductNameOnPlace(productDetail.getName());
                    cateringOrderItemDO.setProductUnitPriceOnPlace(BigDecimal.valueOf(productDetail.getUnitPrice()));
                    cateringOrderItemDO.setProductUnitOfMeasureOnPlace(productDetail.getUnitOfMeasure());
                    cateringOrderItemDO.setProductMethodId(item.getProductMethodId());
                    productDetail.getMethodGroups().forEach(methodGroup -> {
                        methodGroup.getOptions().forEach(option -> {
                            if (option.getId().equals(item.getProductMethodId())) {
                                cateringOrderItemDO.setProductMethodGroupNameOnPlace(methodGroup.getName());
                                cateringOrderItemDO.setProductMethodNameOnPlace(option.getName());
                            }
                        });
                    });
                }
            });
            list.add(cateringOrderItemDO);
        });


        return list;
    }

    public static List<CateringOrderItemAccessoryDO> toCateringOrderItemAccessoryDO(List<CateringOrderItemDO> orderItemDOS, PlaceCateringOrderBizRequest request, List<ProductDetailRemoteResponse> productDetailList) {

        return request.getItems().stream().flatMap(item -> {
            Map<String, CateringOrderItemDO> map = orderItemDOS.stream().collect(Collectors.toMap(CateringOrderItemDO::getSeqNo, Function.identity()));
            CateringOrderItemDO orderItemDO = map.get(item.getSeqNo());
            return item.getAccessories().stream().map(accessory -> {
                CateringOrderItemAccessoryDO accessoryDO = new CateringOrderItemAccessoryDO();
                accessoryDO.setTenantId(orderItemDO.getTenantId());
                accessoryDO.setVersion(1);
                accessoryDO.setOrderItemId(orderItemDO.getId());
                accessoryDO.setSeqNo(accessory.getSeqNo());
                accessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.PLACED);
                accessoryDO.setPlaceQuantity(accessory.getQuantity());
                accessoryDO.setProduceQuantity(BigDecimal.ZERO);
                accessoryDO.setLatestQuantity(accessory.getQuantity());
                accessoryDO.setProductAccessoryId(accessory.getProductAccessoryId());
                for (ProductDetailRemoteResponse productDetail : productDetailList) {
                    for (ProductDetailRemoteResponse.AccessoryGroup accessoryGroup : productDetail.getAccessoryGroups()) {
                        for (ProductDetailRemoteResponse.AccessoryGroup.Option option : accessoryGroup.getOptions()) {
                            if (accessory.getProductAccessoryId().equals(option.getId())) {
                                accessoryDO.setProductAccessoryNameOnPlace(option.getName());
                                accessoryDO.setProductAccessoryGroupNameOnPlace(accessoryGroup.getName());
                                accessoryDO.setProductAccessoryUnitPriceOnPlace(BigDecimal.valueOf(option.getUnitPrice()));
                                accessoryDO.setProductAccessoryUnitOfMeasureOnPlace(option.getUnitOfMeasure());
                            }
                        }
                    }
                }
                return accessoryDO;
            });
        }).collect(Collectors.toList());
    }

    private static PlaceCateringOrderBizRequest.Item buildItem(PlaceCateringOrderHttpRequest.Item httpItem) {

        PlaceCateringOrderBizRequest.Item item = new PlaceCateringOrderBizRequest.Item();
        item.setSeqNo(httpItem.getSeqNo());
        item.setQuantity(httpItem.getQuantity());
        item.setProductId(httpItem.getProductId());
        item.setProductMethodId(httpItem.getProductMethodId());

        List<PlaceCateringOrderBizRequest.Item.Accessory> accessories = item.getAccessories();
        List<PlaceCateringOrderHttpRequest.Item.Accessory> httpAccessories = httpItem.getAccessories();
        httpAccessories.forEach(httpAccessory -> {
            accessories.add(buildAccessory(httpAccessory));

        });
        return item;
    }

    private static PlaceCateringOrderBizRequest.Item.Accessory buildAccessory(PlaceCateringOrderHttpRequest.Item.Accessory httpAccessory) {

        PlaceCateringOrderBizRequest.Item.Accessory accessory = new PlaceCateringOrderBizRequest.Item.Accessory();
        accessory.setProductAccessoryId(httpAccessory.getProductAccessoryId());
        accessory.setQuantity(httpAccessory.getQuantity());
        accessory.setSeqNo(httpAccessory.getSeqNo());

        return accessory;
    }

}
