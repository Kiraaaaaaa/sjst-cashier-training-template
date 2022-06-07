package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.order.api.http.model.request.PlaceCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.PlaceCateringOrderBizRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/1 15:04
 * @ClassName: PlaceCateringOrderBizRequestConverter
 */
public class PlaceCateringOrderRequestConverter {

    public static PlaceCateringOrderBizRequest toPlaceCateringOrderBizRequest(PlaceCateringOrderHttpRequest request) {
        PlaceCateringOrderBizRequest bizRequest = new PlaceCateringOrderBizRequest();
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

    private static PlaceCateringOrderBizRequest.Item buildItem(PlaceCateringOrderHttpRequest.Item httpItem) {

        PlaceCateringOrderBizRequest.Item item = new PlaceCateringOrderBizRequest.Item();
        item.setSeqNo(httpItem.getSeqNo());
        item.setQuantity(httpItem.getQuantity());
        item.setProductId(httpItem.getProductId());
        item.setProductMethodId(httpItem.getProductMethodId());

        List<PlaceCateringOrderBizRequest.Item.Accessory> accessories = item.getAccessories();
        List<PlaceCateringOrderHttpRequest.Item.Accessory> httpAccessories = httpItem.getAccessories();
        for (PlaceCateringOrderHttpRequest.Item.Accessory httpAccessory : httpAccessories) {
            accessories.add(buildAccessory(httpAccessory));
        }
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
