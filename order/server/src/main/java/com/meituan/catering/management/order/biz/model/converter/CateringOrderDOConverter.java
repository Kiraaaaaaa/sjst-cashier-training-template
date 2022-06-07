package com.meituan.catering.management.order.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.biz.model.request.PlaceCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;
import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 从其他数据模型向订单DO的转换器
 */
public abstract class CateringOrderDOConverter {

    public static CateringOrderDO toCateringOrderDO(Long tenantId, Long userId, PlaceCateringOrderBizRequest request, ShopDetailRemoteResponse shopDetail) {

        CateringOrderDO cateringOrderDO = new CateringOrderDO();
        cateringOrderDO.setStatus(CateringOrderStatusEnum.PLACED);
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

    public static CateringOrderItemDO toCateringOrderItemDO(CateringOrderDO cateringOrderDO, PlaceCateringOrderBizRequest.Item item, List<ProductDetailRemoteResponse> productDetailList) {
        CateringOrderItemDO cateringOrderItemDO = new CateringOrderItemDO();
        cateringOrderItemDO.setTenantId(cateringOrderDO.getTenantId());
        cateringOrderItemDO.setVersion(1);
        cateringOrderItemDO.setOrderId(cateringOrderDO.getId());
        cateringOrderItemDO.setSeqNo(item.getSeqNo());
        cateringOrderItemDO.setStatus(CateringOrderItemStatusEnum.PLACED);
        cateringOrderItemDO.setPlaceQuantity(item.getQuantity());
        cateringOrderItemDO.setProduceQuantity(new BigDecimal(0));
        cateringOrderItemDO.setLatestQuantity(item.getQuantity());
        cateringOrderItemDO.setProductId(item.getProductId());
        for (ProductDetailRemoteResponse productDetail : productDetailList) {
            if (Objects.equals(item.getProductId(), productDetail.getId())) {
                cateringOrderItemDO.setProductNameOnPlace(productDetail.getName());
                cateringOrderItemDO.setProductUnitPriceOnPlace(BigDecimal.valueOf(productDetail.getUnitPrice()));
                cateringOrderItemDO.setProductUnitOfMeasureOnPlace(productDetail.getUnitOfMeasure());
                cateringOrderItemDO.setProductMethodId(item.getProductMethodId());
                for (ProductDetailRemoteResponse.MethodGroup methodGroup : productDetail.getMethodGroups()) {
                    for (ProductDetailRemoteResponse.MethodGroup.Option option : methodGroup.getOptions()) {
                        if (option.getId().equals(item.getProductMethodId())) {
                            cateringOrderItemDO.setProductMethodGroupNameOnPlace(methodGroup.getName());
                            cateringOrderItemDO.setProductMethodNameOnPlace(option.getName());
                        }
                    }
                }

            }

        }
        return cateringOrderItemDO;
    }

    public static List<CateringOrderItemAccessoryDO> toCateringOrderItemAccessoryDO(CateringOrderItemDO orderItemDO, PlaceCateringOrderBizRequest.Item request, List<ProductDetailRemoteResponse> productDetailList) {

        ArrayList<CateringOrderItemAccessoryDO> list = Lists.newArrayList();
        for (PlaceCateringOrderBizRequest.Item.Accessory accessory : request.getAccessories()) {
            CateringOrderItemAccessoryDO accessoryDO = new CateringOrderItemAccessoryDO();
            accessoryDO.setTenantId(orderItemDO.getTenantId());
            accessoryDO.setVersion(1);
            accessoryDO.setOrderItemId(orderItemDO.getId());
            accessoryDO.setSeqNo(accessory.getSeqNo());
            accessoryDO.setStatus(CateringOrderItemAccessoryStatusEnum.PLACED);
            accessoryDO.setPlaceQuantity(accessory.getQuantity());
            accessoryDO.setProduceQuantity(new BigDecimal(0));
            accessoryDO.setLatestQuantity(new BigDecimal(0));
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
            list.add(accessoryDO);
            accessoryDO = null;
        }

        return list;
    }


}
