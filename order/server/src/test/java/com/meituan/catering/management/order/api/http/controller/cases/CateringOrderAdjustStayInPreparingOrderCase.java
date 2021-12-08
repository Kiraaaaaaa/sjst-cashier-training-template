package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.AdjustCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.PREPARING;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CateringOrderAdjustStayInPreparingOrderCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering/10002/adjust"))
                                 .content(OM.writeValueAsString(buildRequest())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    CateringOrderDetailHttpResponse response = getHttpResponseEntity(result, CateringOrderDetailHttpResponse.class);
                    assertNotNull(response);
                    // Header
                    assertOrderHeader(response);
                    // Items
                    assertOrderItems(response);
                });
    }


    private AdjustCateringOrderHttpRequest buildRequest() {
        AdjustCateringOrderHttpRequest httpRequest = new AdjustCateringOrderHttpRequest();
        httpRequest.setVersion(3);
        buildRequestItems(httpRequest);
        return httpRequest;
    }

    private void buildRequestItems(AdjustCateringOrderHttpRequest httpRequest) {
        // Item 1
        buildRequestItem1(httpRequest);
        // Item 2
        buildRequestItem2(httpRequest);
        // Item 3
        buildRequestItem3(httpRequest);
        // Item 4
        buildRequestItem4(httpRequest);
        // Item 5
        buildRequestItem5(httpRequest);
        // Item 6
        buildRequestItem6WithAccessories(httpRequest);
        // Item 7 - Adding
        buildRequestItem7WithAccessoriesForAdding(httpRequest);
    }

    private void buildRequestItem1(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest1 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest1.setSeqNo("1");
        itemHttpRequest1.setVersion(2);
        // 2 = 1 + 1
        itemHttpRequest1.setQuantityOnAdjustment(BigDecimal.ONE);
        httpRequest.getItems().add(itemHttpRequest1);
    }

    private void buildRequestItem2(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest2 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest2.setSeqNo("2");
        itemHttpRequest2.setVersion(2);
        // 0 = 1 - 1
        itemHttpRequest2.setQuantityOnAdjustment(BigDecimal.valueOf(-1));
        httpRequest.getItems().add(itemHttpRequest2);
    }

    private void buildRequestItem3(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest3 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest3.setSeqNo("3");
        itemHttpRequest3.setVersion(2);
        // 2 = 3 - 1
        itemHttpRequest3.setQuantityOnAdjustment(BigDecimal.valueOf(-1));
        httpRequest.getItems().add(itemHttpRequest3);
    }

    private void buildRequestItem4(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest4 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest4.setSeqNo("4");
        itemHttpRequest4.setVersion(4);
        // 1 = 5 - 4
        itemHttpRequest4.setQuantityOnAdjustment(BigDecimal.valueOf(-4));
        httpRequest.getItems().add(itemHttpRequest4);
    }

    private void buildRequestItem5(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest5 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest5.setSeqNo("5");
        itemHttpRequest5.setVersion(5);
        // 4 = 3 + 1
        itemHttpRequest5.setQuantityOnAdjustment(BigDecimal.valueOf(1));
        httpRequest.getItems().add(itemHttpRequest5);
    }

    private void buildRequestItem6WithAccessories(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest6 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest6.setSeqNo("6");
        itemHttpRequest6.setVersion(3);
        // 7 = 4 + 3
        itemHttpRequest6.setQuantityOnAdjustment(BigDecimal.valueOf(3));
        // Item 6 - Accessory 1
        AdjustCateringOrderHttpRequest.Item.Accessory accessory61 = new AdjustCateringOrderHttpRequest.Item.Accessory();
        accessory61.setSeqNo("6-1");
        accessory61.setVersion(3);
        // 1 = 4 - 3
        accessory61.setQuantityOnAdjustment(BigDecimal.valueOf(-3));
        itemHttpRequest6.getAccessories().add(accessory61);
        // Item 6 - Accessory 2
        AdjustCateringOrderHttpRequest.Item.Accessory accessory62 = new AdjustCateringOrderHttpRequest.Item.Accessory();
        accessory62.setSeqNo("6-2");
        accessory62.setVersion(3);
        // 11 = 8 + 3
        accessory62.setQuantityOnAdjustment(BigDecimal.valueOf(3));
        itemHttpRequest6.getAccessories().add(accessory62);
        httpRequest.getItems().add(itemHttpRequest6);
    }


    private void buildRequestItem7WithAccessoriesForAdding(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest7 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest7.setSeqNo("7");
        itemHttpRequest7.setProductId(100L);
        itemHttpRequest7.setProductMethodId(10022L);
        itemHttpRequest7.setQuantityOnAdjustment(BigDecimal.valueOf(1));
        // Item 6 - Accessory 1
        AdjustCateringOrderHttpRequest.Item.Accessory accessory71 = new AdjustCateringOrderHttpRequest.Item.Accessory();
        accessory71.setSeqNo("7-1");
        accessory71.setProductAccessoryId(10012L);
        accessory71.setQuantityOnAdjustment(BigDecimal.valueOf(3));
        itemHttpRequest7.getAccessories().add(accessory71);
        // Item 6 - Accessory 2
        AdjustCateringOrderHttpRequest.Item.Accessory accessory72 = new AdjustCateringOrderHttpRequest.Item.Accessory();
        accessory72.setSeqNo("7-2");
        accessory72.setProductAccessoryId(10011L);
        accessory72.setQuantityOnAdjustment(BigDecimal.valueOf(2));
        itemHttpRequest7.getAccessories().add(accessory72);
        httpRequest.getItems().add(itemHttpRequest7);
    }


    private void assertOrderHeader(CateringOrderDetailHttpResponse response) {
        // Base
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertTrue(response.getAuditing().getCreatedAt().before(new Date()));
        assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
        assertTrue(response.getAuditing().getLastModifiedAt().before(new Date()));
        assertEquals(Integer.valueOf(4), response.getVersion());
        // Header
        assertEquals(PREPARING, response.getStatus());
        assertEquals(new BigDecimal("767.20"), response.getTotalPrice());
    }

    private void assertOrderItems(CateringOrderDetailHttpResponse response) {
        assertEquals(7, response.getItems().size());
        // Item 1
        assertOrderItem1(response);
        // Item 2
        assertOrderItem2(response);
        // Item 3
        assertOrderItem3(response);
        // Item 4
        assertOrderItem4(response);
        // Item 5
        assertOrderItem5(response);
        // Item 6
        assertOrderItem6WithAccessories(response);
        // Item 7
        assertOrderItem7WithAccessoriesForAdding(response);
    }

    private void assertOrderItem1(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item1 = response.getItems().get(0);
        assertEquals(Integer.valueOf(3), item1.getVersion());
        // 2 = 1 + 1
        assertEquals(new BigDecimal("2.00"), item1.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item1.getStatus());
    }

    private void assertOrderItem2(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item2 = response.getItems().get(1);
        assertEquals(Integer.valueOf(3), item2.getVersion());
        // 0 = 1 - 1
        assertEquals(new BigDecimal("0.00"), item2.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.CANCELLED, item2.getStatus());
    }

    private void assertOrderItem3(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item3 = response.getItems().get(2);
        assertEquals(Integer.valueOf(3), item3.getVersion());
        // 2 = 3 - 1
        assertEquals(new BigDecimal("2.00"), item3.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item3.getStatus());
    }

    private void assertOrderItem4(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item4 = response.getItems().get(3);
        assertEquals(Integer.valueOf(5), item4.getVersion());
        // 1 = 5 - 4
        assertEquals(new BigDecimal("1.00"), item4.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARED, item4.getStatus());
    }

    private void assertOrderItem5(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item5 = response.getItems().get(4);
        assertEquals(Integer.valueOf(6), item5.getVersion());
        // 4 = 3 + 1
        assertEquals(new BigDecimal("4.00"), item5.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item5.getStatus());
    }

    private void assertOrderItem6WithAccessories(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item6 = response.getItems().get(5);
        assertEquals(Integer.valueOf(4), item6.getVersion());
        // 7 = 4 + 3
        assertEquals(new BigDecimal("7.00"), item6.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item6.getStatus());
        // Item 6 - Accessories
        assertEquals(2, item6.getAccessories().size());
        // Item 6 - Accessory 1
        CateringOrderDetailHttpResponse.Item.Accessory accessory61 = item6.getAccessories().get(0);
        assertEquals(Integer.valueOf(4), accessory61.getVersion());
        // 4 = 1 + 3
        assertEquals(new BigDecimal("1.00"), accessory61.getQuantity().getLatest());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARED, accessory61.getStatus());
        // Item 6 - Accessory 2
        CateringOrderDetailHttpResponse.Item.Accessory accessory62 = item6.getAccessories().get(1);
        assertEquals(Integer.valueOf(4), accessory62.getVersion());
        // 8 = 2 + 6
        assertEquals(new BigDecimal("11.00"), accessory62.getQuantity().getLatest());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory62.getStatus());
    }

    private void assertOrderItem7WithAccessoriesForAdding(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item7 = response.getItems().get(6);
        assertEquals(Integer.valueOf(1), item7.getVersion());
        // Quantity
        assertEquals(new BigDecimal("1.00"), item7.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), item7.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("1.00"), item7.getQuantity().getLatest());
        // Product
        CateringOrderDetailHttpResponse.Item.ProductSnapshot productVO7 = item7.getProductSnapshotOnPlace();
        assertEquals(Long.valueOf(100L), productVO7.getId());
        assertEquals("宫保鸡丁", productVO7.getName());
        assertEquals(new BigDecimal("26.80"), productVO7.getUnitPrice());
        assertEquals("元/份", productVO7.getUnitOfMeasure());
        // Product Method
        CateringOrderDetailHttpResponse.Item.ProductMethodSnapshot productMethodVO7 = item7.getProductMethodSnapshotOnPlace();
        assertEquals(Long.valueOf(10022L), productMethodVO7.getId());
        assertEquals("中辣", productMethodVO7.getName());
        assertEquals("辣度", productMethodVO7.getGroupName());
        // Item 7 - Accessories
        assertOrderItem7AccessoriesForAdding(item7);
    }

    private void assertOrderItem7AccessoriesForAdding(CateringOrderDetailHttpResponse.Item item7) {
        assertEquals(2, item7.getAccessories().size());
        // Item 7 - Accessory 1
        CateringOrderDetailHttpResponse.Item.Accessory accessory71 = item7.getAccessories().get(0);
        assertEquals(Integer.valueOf(1), accessory71.getVersion());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory71.getStatus());
        // Quantity
        assertEquals(new BigDecimal("3.00"), accessory71.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), accessory71.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("3.00"), accessory71.getQuantity().getLatest());
        // Product
        assertEquals(Long.valueOf(10012L), accessory71.getProductAccessorySnapshotOnPlace().getId());
        assertEquals("辣椒酱", accessory71.getProductAccessorySnapshotOnPlace().getName());
        assertEquals("配菜", accessory71.getProductAccessorySnapshotOnPlace().getGroupName());
        assertEquals(new BigDecimal("1.00"), accessory71.getProductAccessorySnapshotOnPlace().getUnitPrice());
        assertEquals("两", accessory71.getProductAccessorySnapshotOnPlace().getUnitOfMeasure());
        // Item 7 - Accessory 2
        CateringOrderDetailHttpResponse.Item.Accessory accessory72 = item7.getAccessories().get(1);
        assertEquals(Integer.valueOf(1), accessory72.getVersion());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory72.getStatus());
        // Quantity
        assertEquals(new BigDecimal("2.00"), accessory72.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), accessory72.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("2.00"), accessory72.getQuantity().getLatest());
        // Product
        assertEquals(Long.valueOf(10011L), accessory72.getProductAccessorySnapshotOnPlace().getId());
        assertEquals("泡萝卜", accessory72.getProductAccessorySnapshotOnPlace().getName());
        assertEquals("配菜", accessory72.getProductAccessorySnapshotOnPlace().getGroupName());
        assertEquals(new BigDecimal("1.00"), accessory72.getProductAccessorySnapshotOnPlace().getUnitPrice());
        assertEquals("份", accessory72.getProductAccessorySnapshotOnPlace().getUnitOfMeasure());
    }
}
