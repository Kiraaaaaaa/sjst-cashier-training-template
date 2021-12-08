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

public class CateringOrderAdjustPreparedToPreparingOrderCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering/10003/adjust"))
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
        httpRequest.setVersion(5);
        buildRequestItems(httpRequest);
        return httpRequest;
    }

    private void buildRequestItems(AdjustCateringOrderHttpRequest httpRequest) {
        // Item 1
        buildRequestItem1(httpRequest);
        // Item 6
        buildRequestItem2WithAccessories(httpRequest);
    }

    private void buildRequestItem1(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest1 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest1.setSeqNo("1");
        itemHttpRequest1.setVersion(7);
        itemHttpRequest1.setQuantityOnAdjustment(BigDecimal.valueOf(1));
        httpRequest.getItems().add(itemHttpRequest1);
    }

    private void buildRequestItem2WithAccessories(AdjustCateringOrderHttpRequest httpRequest) {
        AdjustCateringOrderHttpRequest.Item itemHttpRequest2 = new AdjustCateringOrderHttpRequest.Item();
        itemHttpRequest2.setSeqNo("2");
        itemHttpRequest2.setVersion(5);
        itemHttpRequest2.setQuantityOnAdjustment(BigDecimal.valueOf(1));
        // Item 6 - Accessory 1
        AdjustCateringOrderHttpRequest.Item.Accessory accessory21 = new AdjustCateringOrderHttpRequest.Item.Accessory();
        accessory21.setSeqNo("2-1");
        accessory21.setVersion(3);
        accessory21.setQuantityOnAdjustment(BigDecimal.valueOf(1));
        itemHttpRequest2.getAccessories().add(accessory21);
        httpRequest.getItems().add(itemHttpRequest2);
    }

    private void assertOrderHeader(CateringOrderDetailHttpResponse response) {
        // Base
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertTrue(response.getAuditing().getCreatedAt().before(new Date()));
        assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
        assertTrue(response.getAuditing().getLastModifiedAt().before(new Date()));
        assertEquals(Integer.valueOf(6), response.getVersion());
        // Header
        assertEquals(PREPARING, response.getStatus());
        assertEquals(new BigDecimal("213.20"), response.getTotalPrice());
    }

    private void assertOrderItems(CateringOrderDetailHttpResponse response) {
        assertEquals(2, response.getItems().size());
        // Item 1
        assertOrderItem1(response);
        // Item 2
        assertOrderItem2WithAccessories(response);
    }

    private void assertOrderItem1(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item1 = response.getItems().get(0);
        assertEquals(Integer.valueOf(8), item1.getVersion());
        assertEquals(new BigDecimal("2.00"), item1.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item1.getStatus());
    }

    private void assertOrderItem2WithAccessories(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item2 = response.getItems().get(1);
        assertEquals(Integer.valueOf(6), item2.getVersion());
        assertEquals(new BigDecimal("2.00"), item2.getQuantity().getLatest());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item2.getStatus());
        // Item 2 - Accessories
        assertEquals(1, item2.getAccessories().size());
        // Item 2 - Accessory 1
        CateringOrderDetailHttpResponse.Item.Accessory accessory21 = item2.getAccessories().get(0);
        assertEquals(Integer.valueOf(4), accessory21.getVersion());
        assertEquals(new BigDecimal("2.00"), accessory21.getQuantity().getLatest());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory21.getStatus());
    }
}
