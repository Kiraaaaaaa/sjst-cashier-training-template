package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import com.meituan.catering.management.order.api.http.model.request.BillCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.BILLED;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CateringOrderBillOrderCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering/10003/bill"))
                                 .content(OM.writeValueAsString(buildRequest())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    CateringOrderDetailHttpResponse response = getHttpResponseEntity(result, CateringOrderDetailHttpResponse.class);
                    assertNotNull(response);
                    assertOrderHeader(response);
                });
    }

    private BillCateringOrderHttpRequest buildRequest() {
        BillCateringOrderHttpRequest httpRequest = new BillCateringOrderHttpRequest();
        httpRequest.setVersion(5);
        httpRequest.setPromotion(BigDecimal.valueOf(10));
        httpRequest.setPaid(BigDecimal.valueOf(155));
        httpRequest.setPaymentChannel(CateringOrderPaymentChannelEnum.ALIPAY);
        return httpRequest;
    }

    private void assertOrderHeader(CateringOrderDetailHttpResponse response) {
        // Base
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertTrue(response.getAuditing().getCreatedAt().before(new Date()));
        assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
        assertTrue(response.getAuditing().getLastModifiedAt().before(new Date()));
        assertEquals(Integer.valueOf(6), response.getVersion());
        // Header
        assertEquals(BILLED, response.getStatus());
        assertEquals(new BigDecimal("10.00"), response.getBilling().getPromotion());
        assertEquals(new BigDecimal("155.00"), response.getBilling().getPaid());
        assertEquals(CateringOrderPaymentChannelEnum.ALIPAY, response.getBilling().getPaymentChannel());
    }
}
