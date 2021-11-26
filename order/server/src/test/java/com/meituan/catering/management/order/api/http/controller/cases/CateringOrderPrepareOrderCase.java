package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.PrepareCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.PREPARING;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CateringOrderPrepareOrderCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering/10000/prepare"))
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

    private PrepareCateringOrderHttpRequest buildRequest() {
        PrepareCateringOrderHttpRequest httpRequest = new PrepareCateringOrderHttpRequest();
        httpRequest.setVersion(2);
        // Item 1
        PrepareCateringOrderHttpRequest.Item itemHttpRequest1 = new PrepareCateringOrderHttpRequest.Item();
        itemHttpRequest1.setSeqNo("1");
        itemHttpRequest1.setVersion(1);
        httpRequest.getItems().add(itemHttpRequest1);
        // Item 2
        PrepareCateringOrderHttpRequest.Item itemHttpRequest2 = new PrepareCateringOrderHttpRequest.Item();
        itemHttpRequest2.setSeqNo("3");
        itemHttpRequest2.setVersion(1);
        httpRequest.getItems().add(itemHttpRequest2);
        // Return
        return httpRequest;
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
        assertEquals(3, response.getItems().size());
        // Item 1
        assertOrderItem1WithAccessories(response);
        // Item 3
        assertOrderItem3(response);
    }

    private void assertOrderItem1WithAccessories(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item1 = response.getItems().get(0);
        assertEquals(Integer.valueOf(2), item1.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item1.getStatus());
        // Item 1 - Accessories
        CateringOrderDetailHttpResponse.Item.Accessory accessory1 = item1.getAccessories().get(0);
        // Item 1 - Accessory 1
        assertEquals(Integer.valueOf(2), accessory1.getVersion());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory1.getStatus());
        // Item 1 - Accessory 2
        CateringOrderDetailHttpResponse.Item.Accessory accessory2 = item1.getAccessories().get(1);
        assertEquals(Integer.valueOf(2), accessory2.getVersion());
        assertEquals(CateringOrderItemAccessoryStatusEnum.PREPARING, accessory2.getStatus());
    }

    private void assertOrderItem3(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item3 = response.getItems().get(2);
        assertEquals(Integer.valueOf(2), item3.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PREPARING, item3.getStatus());
    }
}
