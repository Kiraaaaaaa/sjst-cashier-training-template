package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.common.model.biz.AuditingBO;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.PlaceCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 从其他数据模型向订单BO的转换器
 */
public abstract class CateringOrderBOConverter {

    public static CateringOrderBO toCateringOrderBO(
            CateringOrderDO cateringOrderDO,
            List<CateringOrderItemDO> cateringOrderItemDOS,
            List<CateringOrderItemAccessoryDO> cateringOrderItemAccessoryDOS) {
        if (Objects.isNull(cateringOrderDO)) {
            return null;
        }

        CateringOrderBO cateringOrderBO = new CateringOrderBO();
        cateringOrderBO.setStatus(cateringOrderDO.getStatus());
        cateringOrderBO.setTableNo(cateringOrderDO.getTableNo());
        cateringOrderBO.setCustomerCount(cateringOrderDO.getCustomerCount());
        cateringOrderBO.setTotalPrice(cateringOrderDO.getTotalPrice());
        cateringOrderBO.setComment(cateringOrderDO.getComment());
        cateringOrderBO.setId(cateringOrderDO.getId());
        cateringOrderBO.setTenantId(cateringOrderDO.getTenantId());
        cateringOrderBO.setVersion(cateringOrderDO.getVersion());

        AuditingBO auditingBO = new AuditingBO();
        auditingBO.setLastModifiedBy(cateringOrderDO.getLastModifiedBy());
        auditingBO.setCreatedBy(cateringOrderDO.getCreatedBy());
        auditingBO.setCreatedAt(cateringOrderDO.getCreatedAt());
        auditingBO.setLastModifiedAt(cateringOrderDO.getLastModifiedAt());
        cateringOrderBO.setAuditing(auditingBO);

        cateringOrderBO.getShopSnapshotOnPlace().setId(cateringOrderDO.getShopId());
        cateringOrderBO.getShopSnapshotOnPlace().setName(cateringOrderDO.getShopNameOnPlace());
        cateringOrderBO.getShopSnapshotOnPlace().setBusinessNo(cateringOrderDO.getShopBusinessNo());

        cateringOrderBO.getBilling().setBillingPaid(cateringOrderDO.getBillingPaid());
        cateringOrderBO.getBilling().setBillingPromotion(cateringOrderDO.getBillingPromotion());
        cateringOrderBO.getBilling().setBillingPaymentChannel(cateringOrderDO.getBillingPaymentChannel());

        for (CateringOrderItemDO cateringOrderItemDO : cateringOrderItemDOS) {
            cateringOrderBO.getItem().add(buildItem(cateringOrderItemDO, cateringOrderItemAccessoryDOS));
        }

        return cateringOrderBO;
    }


    private static CateringOrderBO.CateringOrderItem buildItem(CateringOrderItemDO itemDO, List<CateringOrderItemAccessoryDO> accessoryDOS) {
        if (Objects.isNull(itemDO)) {
            return null;
        }

        CateringOrderBO.CateringOrderItem itemBO = new CateringOrderBO.CateringOrderItem();
        itemBO.setId(itemDO.getId());
        itemBO.setVersion(itemDO.getVersion());
        itemBO.setSeqNo(itemDO.getSeqNo());
        itemBO.setStatus(itemDO.getStatus());

        itemBO.getQuantity().setLatest(itemDO.getLatestQuantity());
        itemBO.getQuantity().setOnPlace(itemDO.getPlaceQuantity());
        itemBO.getQuantity().setOnProduce(itemDO.getProduceQuantity());

        itemBO.getProductMethodSnapshot().setGroupName(itemDO.getProductMethodGroupNameOnPlace());
        itemBO.getProductMethodSnapshot().setName(itemDO.getProductMethodNameOnPlace());
        itemBO.getProductMethodSnapshot().setId(itemDO.getProductMethodId());

        itemBO.getProductSnapShot().setId(itemDO.getProductId());
        itemBO.getProductSnapShot().setName(itemDO.getProductNameOnPlace());
        itemBO.getProductSnapShot().setUnitPrice(itemDO.getProductUnitPriceOnPlace());
        itemBO.getProductSnapShot().setUnitOfMeasure(itemDO.getProductUnitOfMeasureOnPlace());

        List<CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory> accessories = itemBO.getAccessories();
        Collection<CateringOrderItemAccessoryDO> cateringOrderItemAccessoryDOS = CollectionUtils.emptyIfNull(accessoryDOS);
        cateringOrderItemAccessoryDOS.forEach(accessoryDO -> {
            if (accessoryDO.getOrderItemId().equals(itemDO.getId())) {
                accessories.add(buildAccessory(accessoryDO));
            }
        });

        return itemBO;
    }

    private static CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory buildAccessory(CateringOrderItemAccessoryDO accessoryDO) {
        if (Objects.isNull(accessoryDO)) {
            return null;
        }
        CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory accessoryBO = new CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory();
        accessoryBO.setId(accessoryDO.getId());
        accessoryBO.setVersion(accessoryDO.getVersion());
        accessoryBO.setSeqNo(accessoryDO.getSeqNo());
        accessoryBO.setStatus(accessoryDO.getStatus());

        accessoryBO.getProductAccessorySnapshot().setGroupName(accessoryDO.getProductAccessoryGroupNameOnPlace());
        accessoryBO.getProductAccessorySnapshot().setUnitPrice(accessoryDO.getProductAccessoryUnitPriceOnPlace());
        accessoryBO.getProductAccessorySnapshot().setUnitOfMeasure(accessoryDO.getProductAccessoryUnitOfMeasureOnPlace());
        accessoryBO.getProductAccessorySnapshot().setId(accessoryDO.getProductAccessoryId());
        accessoryBO.getProductAccessorySnapshot().setName(accessoryDO.getProductAccessoryNameOnPlace());

        accessoryBO.getQuantity().setLatest(accessoryDO.getLatestQuantity());
        accessoryBO.getQuantity().setOnPlace(accessoryDO.getPlaceQuantity());
        accessoryBO.getQuantity().setOnProduce(accessoryDO.getProduceQuantity());

        return accessoryBO;
    }

}
