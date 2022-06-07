package com.meituan.catering.management.order.biz.model.converter;


import com.meituan.catering.management.order.api.http.model.dto.CateringOrderDetailHttpDTO;
import com.meituan.catering.management.order.api.http.model.dto.CateringOrderPageHttpDTO;
import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.response.SearchCateringOrderBizResponse;


import java.util.List;
import java.util.Objects;

/**
 * 从其他数据模型向订单Http模型的转换器
 */
public abstract class CateringOrderHttpVOConverter {

    public static CateringOrderDetailHttpDTO toDetailHttpDTO(CateringOrderBO orderBO) {
        if (Objects.isNull(orderBO)) {
            return null;
        }
        CateringOrderDetailHttpDTO dto = new CateringOrderDetailHttpDTO();
        dto.setId(orderBO.getId());
        dto.setTenantId(orderBO.getTenantId());
        dto.setVersion(orderBO.getVersion());
        dto.setStatus(orderBO.getStatus());
        dto.setTableNo(orderBO.getTableNo());
        dto.setCustomerCount(Integer.valueOf(orderBO.getCustomerCount()));
        dto.setTotalPrice(orderBO.getTotalPrice());
        dto.setComment(orderBO.getComment());

        dto.getAuditing().setLastModifiedAt(orderBO.getAuditing().getLastModifiedAt());
        dto.getAuditing().setCreatedAt(orderBO.getAuditing().getCreatedAt());
        dto.getAuditing().setCreatedBy(orderBO.getAuditing().getCreatedBy());
        dto.getAuditing().setLastModifiedBy(orderBO.getAuditing().getLastModifiedBy());

        dto.getShopSnapshotOnPlace().setBusinessNo(orderBO.getShopSnapshotOnPlace().getBusinessNo());
        dto.getShopSnapshotOnPlace().setId(orderBO.getShopSnapshotOnPlace().getId());
        dto.getShopSnapshotOnPlace().setName(orderBO.getShopSnapshotOnPlace().getName());

        dto.getBilling().setPaid(orderBO.getBilling().getBillingPaid());
        dto.getBilling().setPromotion(orderBO.getBilling().getBillingPromotion());
        dto.getBilling().setPaymentChannel(orderBO.getBilling().getBillingPaymentChannel());

        List<CateringOrderDetailHttpDTO.Item> items = dto.getItems();
        List<CateringOrderBO.CateringOrderItem> itemsBO = orderBO.getItem();
        for (CateringOrderBO.CateringOrderItem cateringOrderItem : itemsBO) {
            items.add(buildItem(cateringOrderItem));
        }
        return dto;
    }

    public static CateringOrderPageHttpDTO toPageHttpDTO(SearchCateringOrderBizResponse response) {

        CateringOrderPageHttpDTO cateringOrderPageHttpDTO = new CateringOrderPageHttpDTO();
        cateringOrderPageHttpDTO.setPageIndex(response.getPageIndex());
        cateringOrderPageHttpDTO.setPageSize(response.getPageSize());
        cateringOrderPageHttpDTO.setTotalCount(response.getTotalCount());
        cateringOrderPageHttpDTO.setTotalPageCount(response.getTotalPageCount());

        List<CateringOrderPageHttpDTO.Record> records = cateringOrderPageHttpDTO.getRecords();
        List<SearchCateringOrderBizResponse.Record> responseRecords = response.getRecords();
        for (SearchCateringOrderBizResponse.Record responseRecord : responseRecords) {
            records.add(buildRecord(responseRecord));
        }
        return cateringOrderPageHttpDTO;
    }

    private static CateringOrderDetailHttpDTO.Item.Accessory buildAccessory(CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory accessoryBO) {

        CateringOrderDetailHttpDTO.Item.Accessory accessory = new CateringOrderDetailHttpDTO.Item.Accessory();
        accessory.setId(accessoryBO.getId());
        accessory.setVersion(accessoryBO.getVersion());
        accessory.setSeqNo(accessoryBO.getSeqNo());
        accessory.setStatus(accessoryBO.getStatus());

        accessory.getQuantity().setLatest(accessoryBO.getQuantity().getLatest());
        accessory.getQuantity().setOnPlace(accessoryBO.getQuantity().getOnPlace());
        accessory.getQuantity().setOnProduce(accessoryBO.getQuantity().getOnProduce());

        accessory.getProductAccessorySnapshotOnPlace().setId(accessoryBO.getProductAccessorySnapshot().getId());
        accessory.getProductAccessorySnapshotOnPlace().setName(accessoryBO.getProductAccessorySnapshot().getName());
        accessory.getProductAccessorySnapshotOnPlace().setGroupName(accessoryBO.getProductAccessorySnapshot().getGroupName());
        accessory.getProductAccessorySnapshotOnPlace().setUnitPrice(accessoryBO.getProductAccessorySnapshot().getUnitPrice());
        accessory.getProductAccessorySnapshotOnPlace().setUnitOfMeasure(accessoryBO.getProductAccessorySnapshot().getUnitOfMeasure());

        return accessory;
    }

    private static CateringOrderDetailHttpDTO.Item buildItem(CateringOrderBO.CateringOrderItem itemBO) {

        CateringOrderDetailHttpDTO.Item item = new CateringOrderDetailHttpDTO.Item();
        item.setId(itemBO.getId());
        item.setVersion(itemBO.getVersion());
        item.setStatus(itemBO.getStatus());
        item.setSeqNo(itemBO.getSeqNo());

        item.getQuantity().setLatest(itemBO.getQuantity().getLatest());
        item.getQuantity().setOnPlace(itemBO.getQuantity().getOnPlace());
        item.getQuantity().setOnProduce(itemBO.getQuantity().getOnProduce());

        item.getProductSnapshotOnPlace().setId(itemBO.getProductSnapShot().getId());
        item.getProductSnapshotOnPlace().setName(itemBO.getProductSnapShot().getName());
        item.getProductSnapshotOnPlace().setUnitPrice(itemBO.getProductSnapShot().getUnitPrice());
        item.getProductSnapshotOnPlace().setUnitOfMeasure(itemBO.getProductSnapShot().getUnitOfMeasure());

        item.getProductMethodSnapshotOnPlace().setId(itemBO.getProductMethodSnapshot().getId());
        item.getProductMethodSnapshotOnPlace().setGroupName(itemBO.getProductMethodSnapshot().getGroupName());
        item.getProductMethodSnapshotOnPlace().setName(itemBO.getProductMethodSnapshot().getName());

        List<CateringOrderDetailHttpDTO.Item.Accessory> accessories = item.getAccessories();
        List<CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory> accessoriesBO = itemBO.getAccessories();
        for (CateringOrderBO.CateringOrderItem.CateringOrderItemAccessory itemAccessory : accessoriesBO) {
            accessories.add(buildAccessory(itemAccessory));
        }


        return item;
    }

    private static CateringOrderPageHttpDTO.Record buildRecord(SearchCateringOrderBizResponse.Record response){
        CateringOrderPageHttpDTO.Record record = new CateringOrderPageHttpDTO.Record();

        record.setId(response.getId());
        record.setTenantId(response.getTenantId());
        record.setVersion(response.getVersion());
        record.setStatus(response.getStatus());
        record.setTableNo(response.getTableNo());
        record.setCustomerCount(Integer.valueOf(response.getCustomerCount()));
        record.setTotalPrice(response.getTotalPrice());
        record.setComment(response.getComment());

        record.getShopSnapshotOnPlace().setBusinessNo(response.getShopSnapshotOnPlace().getBusinessNo());
        record.getShopSnapshotOnPlace().setName(response.getShopSnapshotOnPlace().getName());
        record.getShopSnapshotOnPlace().setId(response.getShopSnapshotOnPlace().getId());

        record.getAuditing().setCreatedBy(response.getAuditing().getCreatedBy());
        record.getAuditing().setCreatedAt(response.getAuditing().getCreatedAt());
        record.getAuditing().setLastModifiedAt(response.getAuditing().getLastModifiedAt());
        record.getAuditing().setLastModifiedBy(response.getAuditing().getLastModifiedBy());

        return record;
    }
}

