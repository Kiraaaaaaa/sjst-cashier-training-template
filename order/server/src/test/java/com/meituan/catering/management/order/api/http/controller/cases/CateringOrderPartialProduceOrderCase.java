package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.ProduceCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.PREPARING;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CateringOrderPartialProduceOrderCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering/10002/produce"))
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


    private ProduceCateringOrderHttpRequest buildRequest() {
        ProduceCateringOrderHttpRequest httpRequest = new ProduceCateringOrderHttpRequest();
        httpRequest.setVersion(3);
        buildRequestItems(httpRequest);
        return httpRequest;
    }

    private void buildRequestItems(ProduceCateringOrderHttpRequest httpRequest) {
        // Item 1
        buildRequestItem1(httpRequest);
        // Item 2
        buildRequestItem3(httpRequest);
        // Item 3
        buildRequestItem4(httpRequest);
        // Item 6
        buildRequestItem6WithAccessories(httpRequest);
    }

    private void buildRequestItem1(ProduceCateringOrderHttpRequest httpRequest) {
        ProduceCateringOrderHttpRequest.Item itemHttpRequest1 = new ProduceCateringOrderHttpRequest.Item();
        itemHttpRequest1.setSeqNo("1");
        itemHttpRequest1.setVersion(2);
        itemHttpRequest1.setQuantityOnProduce(BigDecimal.ONE);
        httpRequest.getItems().add(itemHttpRequest1);
    }

    private void buildRequestItem3(ProduceCateringOrderHttpRequest httpRequest) {
        ProduceCateringOrderHttpRequest.Item itemHttpRequest3 = new ProduceCateringOrderHttpRequest.Item();
        itemHttpRequest3.setSeqNo("3");
        itemHttpRequest3.setVersion(2);
        itemHttpRequest3.setQuantityOnProduce(BigDecimal.valueOf(2));
        httpRequest.getItems().add(itemHttpRequest3);
    }

    private void buildRequestItem4(ProduceCateringOrderHttpRequest httpRequest) {
        ProduceCateringOrderHttpRequest.Item itemHttpRequest4 = new ProduceCateringOrderHttpRequest.Item();
        itemHttpRequest4.setSeqNo("4");
        itemHttpRequest4.setVersion(4);
        itemHttpRequest4.setQuantityOnProduce(BigDecimal.valueOf(4));
        httpRequest.getItems().add(itemHttpRequest4);
    }

    private void buildRequestItem6WithAccessories(ProduceCateringOrderHttpRequest httpRequest) {
        ProduceCateringOrderHttpRequest.Item itemHttpRequest6 = new ProduceCateringOrderHttpRequest.Item();
        itemHttpRequest6.setSeqNo("6");
        itemHttpRequest6.setVersion(3);
        itemHttpRequest6.setQuantityOnProduce(BigDecimal.valueOf(2));
        // Item 6 - Accessory 1
        ProduceCateringOrderHttpRequest.Item.Accessory accessory1 = new ProduceCateringOrderHttpRequest.Item.Accessory();
        accessory1.setSeqNo("6-1");
        accessory1.setVersion(3);
        accessory1.setQuantityOnProduce(BigDecimal.valueOf(2));
        itemHttpRequest6.getAccessories().add(accessory1);
        // Item 6 - Accessory 2
        ProduceCateringOrderHttpRequest.Item.Accessory accessory2 = new ProduceCateringOrderHttpRequest.Item.Accessory();
        accessory2.setSeqNo("6-2");
        accessory2.setVersion(3);
        accessory2.setQuantityOnProduce(BigDecimal.valueOf(4));
        itemHttpRequest6.getAccessories().add(accessory2);
        httpRequest.getItems().add(itemHttpRequest6);
    }


    private void assertOrderHeader(CateringOrderDetailHttpResponse response) {
        // Base
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertTrue(response.getAuditing().getCreatedAt().before(new Date()));
        assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
        assertTrue(response.getAuditing().getLastModifiedAt().before(new Date()));
        assertEquals(Integer.valueOf(3), response.getVersion());
        // Header
        assertEquals(PREPARING, response.getStatus());
    }

    private void assertOrderItems(CateringOrderDetailHttpResponse response) {
        assertEquals(6, response.getItems().size());
        // Item 1
        assertOrderItem1(response);
        // Item 3
        assertOrderItem3(response);
        // Item 4
        assertOrderItem4(response);
        // Item 6
        assertOrderItem6WithAccessories(response);
    }

    private void assertOrderItem1(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item1 = response.getItems().get(0);
        assertEquals(Integer.valueOf(3), item1.getVersion());
        // 1 = 0 + 1
        assertEquals(new BigDecimal("1.00"), item1.getQuantity().getOnProduce());
        assertEquals(CateringOrderItemStatusEnum.PREPARED, item1.getStatus());
    }

    private void assertOrderItem3(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item3 = response.getItems().get(2);
        assertEquals(Integer.valueOf(3), item3.getVersion());
        // 2 = 0 + 2
        assertEquals(new BigDecimal("2.00"), item3.getQuantity().getOnProduce());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item3.getStatus());
    }

    private void assertOrderItem4(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item4 = response.getItems().get(3);
        assertEquals(Integer.valueOf(5), item4.getVersion());
        // 5 = 1 + 4
        assertEquals(new BigDecimal("5.00"), item4.getQuantity().getOnProduce());
        assertEquals(CateringOrderItemStatusEnum.PREPARED, item4.getStatus());
    }

    private void assertOrderItem6WithAccessories(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item6 = response.getItems().get(5);
        assertEquals(Integer.valueOf(4), item6.getVersion());
        // 3 = 1 + 2
        assertEquals(new BigDecimal("3.00"), item6.getQuantity().getOnProduce());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item6.getStatus());
        assertOrderItem6Accessories(item6);
    }

    private void assertOrderItem6Accessories(CateringOrderDetailHttpResponse.Item item6) {
        // Item 6 - Accessories
        assertEquals(2, item6.getAccessories().size());
        // Item 6 - Accessory 1
        CateringOrderDetailHttpResponse.Item.Accessory accessory61 = item6.getAccessories().get(0);
        assertEquals(Integer.valueOf(4), accessory61.getVersion());
        // 3 = 1 + 2
        assertEquals(new BigDecimal("3.00"), accessory61.getQuantity().getOnProduce());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory61.getStatus());
        // Item 6 - Accessory 2
        CateringOrderDetailHttpResponse.Item.Accessory accessory62 = item6.getAccessories().get(1);
        assertEquals(Integer.valueOf(4), accessory62.getVersion());
        // 6 = 2 + 4
        assertEquals(new BigDecimal("6.00"), accessory62.getQuantity().getOnProduce());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory62.getStatus());
    }
}
