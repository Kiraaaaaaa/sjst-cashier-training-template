package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.PlaceCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.PLACED;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CateringOrderPlaceOrderCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering"))
                                 .content(OM.writeValueAsString(buildRequest())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    CateringOrderDetailHttpResponse response = getHttpResponseEntity(result, CateringOrderDetailHttpResponse.class);
                    assertNotNull(response);
                    assertOrderHeader(response);
                    assertOrderItems(response);
                });
    }

    private PlaceCateringOrderHttpRequest buildRequest() {
        PlaceCateringOrderHttpRequest httpRequest = new PlaceCateringOrderHttpRequest();
        // Header
        buildRequestHeader(httpRequest);
        // Items
        buildRequestItems(httpRequest);
        // Return
        return httpRequest;
    }

    private void buildRequestItems(PlaceCateringOrderHttpRequest httpRequest) {
        // Item 1
        buildRequestItem1(httpRequest);
        // Item 2
        buildRequestItem2WithAccessories(httpRequest);
    }

    private void buildRequestItem1(PlaceCateringOrderHttpRequest httpRequest) {
        PlaceCateringOrderHttpRequest.Item item1 = new PlaceCateringOrderHttpRequest.Item();
        item1.setSeqNo("1");
        item1.setProductId(106L);
        item1.setQuantity(new BigDecimal("2"));
        httpRequest.getItems().add(item1);
    }

    private void buildRequestItem2WithAccessories(PlaceCateringOrderHttpRequest httpRequest) {
        PlaceCateringOrderHttpRequest.Item item2 = new PlaceCateringOrderHttpRequest.Item();
        item2.setSeqNo("2");
        item2.setProductId(100L);
        item2.setProductMethodId(10022L);
        item2.setQuantity(new BigDecimal("3"));
        // Item 2 - Accessory 1
        PlaceCateringOrderHttpRequest.Item.Accessory accessory1 = new PlaceCateringOrderHttpRequest.Item.Accessory();
        accessory1.setSeqNo("2-1");
        accessory1.setProductAccessoryId(10012L);
        accessory1.setQuantity(new BigDecimal("2"));
        item2.getAccessories().add(accessory1);
        // Item 2 - Accessory 1
        PlaceCateringOrderHttpRequest.Item.Accessory accessory2 = new PlaceCateringOrderHttpRequest.Item.Accessory();
        accessory2.setSeqNo("2-2");
        accessory2.setProductAccessoryId(10013L);
        accessory2.setQuantity(new BigDecimal("3"));
        item2.getAccessories().add(accessory2);

        httpRequest.getItems().add(item2);
    }

    private void buildRequestHeader(PlaceCateringOrderHttpRequest httpRequest) {
        httpRequest.setTableNo("B04");
        httpRequest.setCustomerCount(7);
        httpRequest.setTotalPrice(new BigDecimal("386.50"));
        httpRequest.setComment("少盐少油");
        httpRequest.setShopBusinessNo("1234567891");
    }

    private void assertOrderHeader(CateringOrderDetailHttpResponse response) {
        // Base
        assertTrue(response.getId() != null && response.getId() > 0);
        assertEquals(TENANT_ID, response.getTenantId());
        assertEquals(Integer.valueOf(1), response.getVersion());
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertNotNull(response.getAuditing().getCreatedAt());
        assertNull(response.getAuditing().getLastModifiedBy());
        assertNull(response.getAuditing().getLastModifiedAt());
        // Header
        assertEquals(PLACED, response.getStatus());
        assertEquals("B04", response.getTableNo());
        assertEquals(Integer.valueOf(7), response.getCustomerCount());
        assertEquals(new BigDecimal("386.50"), response.getTotalPrice());
        assertEquals("少盐少油", response.getComment());
        // Shop
        assertEquals(Long.valueOf(101), response.getShopSnapshotOnPlace().getId());
        assertEquals("1234567891", response.getShopSnapshotOnPlace().getBusinessNo());
        assertEquals("聚源美食", response.getShopSnapshotOnPlace().getName());
        // Billing
        assertNull(response.getBilling().getPromotion());
        assertNull(response.getBilling().getPaid());
        assertNull(response.getBilling().getPaymentChannel());
    }

    private void assertOrderItems(CateringOrderDetailHttpResponse response) {
        // Items
        assertEquals(2, response.getItems().size());
        // Item 1
        assertOrderItem1(response);
        // Item 2
        assertOrderItem2WithAccessories(response);
    }

    private void assertOrderItem1(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item1 = response.getItems().get(0);
        assertTrue(item1.getId() != null && item1.getId() > 0);
        assertEquals(Integer.valueOf(1), item1.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PLACED, item1.getStatus());
        assertEquals("1", item1.getSeqNo());
        // Item 1 - Quantity
        assertEquals(new BigDecimal("2.00"), item1.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), item1.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("2.00"), item1.getQuantity().getLatest());
        // Item 1 - Product
        assertEquals(Long.valueOf(106), item1.getProductSnapshotOnPlace().getId());
        assertEquals("酸菜鱼", item1.getProductSnapshotOnPlace().getName());
        assertEquals(new BigDecimal("88.80"), item1.getProductSnapshotOnPlace().getUnitPrice());
        assertEquals("元/份", item1.getProductSnapshotOnPlace().getUnitOfMeasure());
        // Item 1 - Product Method
        assertNull(item1.getProductMethodSnapshotOnPlace().getId());
        assertNull(item1.getProductMethodSnapshotOnPlace().getName());
        assertNull(item1.getProductMethodSnapshotOnPlace().getGroupName());
        // Item 1 - Accessories
        assertEquals(0, item1.getAccessories().size());
    }

    private void assertOrderItem2WithAccessories(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item2 = response.getItems().get(1);
        assertTrue(item2.getId() != null && item2.getId() > 0);
        assertEquals(Integer.valueOf(1), item2.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PLACED, item2.getStatus());
        assertEquals("2", item2.getSeqNo());
        // Item 2 - Quantity
        assertEquals(new BigDecimal("3.00"), item2.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), item2.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("3.00"), item2.getQuantity().getLatest());
        // Item 2 - Product
        assertEquals(Long.valueOf(100), item2.getProductSnapshotOnPlace().getId());
        assertEquals("宫保鸡丁", item2.getProductSnapshotOnPlace().getName());
        assertEquals(new BigDecimal("26.80"), item2.getProductSnapshotOnPlace().getUnitPrice());
        assertEquals("元/份", item2.getProductSnapshotOnPlace().getUnitOfMeasure());
        // Item 2 - Product Method
        assertEquals(Long.valueOf(10022), item2.getProductMethodSnapshotOnPlace().getId());
        assertEquals("中辣", item2.getProductMethodSnapshotOnPlace().getName());
        assertEquals("辣度", item2.getProductMethodSnapshotOnPlace().getGroupName());
        // Item 2 - Accessories
        assertOrderItem2Accessories(item2);
    }

    private void assertOrderItem2Accessories(CateringOrderDetailHttpResponse.Item item2) {
        assertEquals(2, item2.getAccessories().size());
        // Item 2 - Accessory 1
        assertOrderItem2Accessory1(item2);
        // Item 2 - Accessory 2
        assertOrderItem2Accessory2(item2);
    }

    private void assertOrderItem2Accessory1(CateringOrderDetailHttpResponse.Item item2) {
        CateringOrderDetailHttpResponse.Item.Accessory accessory1 = item2.getAccessories().get(0);
        assertTrue(accessory1.getId() != null && accessory1.getId() > 0);
        assertEquals(Integer.valueOf(1), accessory1.getVersion());
        assertEquals("2-1", accessory1.getSeqNo());
        // Item 2 - Accessory 1 - Quantity
        assertEquals(new BigDecimal("2.00"), accessory1.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), accessory1.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("2.00"), accessory1.getQuantity().getLatest());
        // Item 2 - Accessory 1 - Product
        assertEquals(Long.valueOf(10012), accessory1.getProductAccessorySnapshotOnPlace().getId());
        assertEquals("辣椒酱", accessory1.getProductAccessorySnapshotOnPlace().getName());
        assertEquals("配菜", accessory1.getProductAccessorySnapshotOnPlace().getGroupName());
        assertEquals(new BigDecimal("3.00"), accessory1.getProductAccessorySnapshotOnPlace().getUnitPrice());
        assertEquals("两", accessory1.getProductAccessorySnapshotOnPlace().getUnitOfMeasure());
    }

    private void assertOrderItem2Accessory2(CateringOrderDetailHttpResponse.Item item2) {
        CateringOrderDetailHttpResponse.Item.Accessory accessory2 = item2.getAccessories().get(1);
        assertTrue(accessory2.getId() != null && accessory2.getId() > 0);
        assertEquals(Integer.valueOf(1), accessory2.getVersion());
        assertEquals("2-2", accessory2.getSeqNo());
        // Item 2 - Accessory 2 - Quantity
        assertEquals(new BigDecimal("3.00"), accessory2.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), accessory2.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("3.00"), accessory2.getQuantity().getLatest());
        // Item 2 - Accessory 2 - Product
        assertEquals(Long.valueOf(10013), accessory2.getProductAccessorySnapshotOnPlace().getId());
        assertEquals("米饭", accessory2.getProductAccessorySnapshotOnPlace().getName());
        assertEquals("配菜", accessory2.getProductAccessorySnapshotOnPlace().getGroupName());
        assertEquals(new BigDecimal("2.00"), accessory2.getProductAccessorySnapshotOnPlace().getUnitPrice());
        assertEquals("碗", accessory2.getProductAccessorySnapshotOnPlace().getUnitOfMeasure());
    }
}
