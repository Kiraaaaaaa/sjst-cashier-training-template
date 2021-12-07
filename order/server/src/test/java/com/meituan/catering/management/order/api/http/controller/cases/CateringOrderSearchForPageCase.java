package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.request.SearchCateringOrderHttpRequest;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderPageHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.PLACED;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CateringOrderSearchForPageCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(
                        post("/order/catering/search"))
                                 .content(OM.writeValueAsString(buildRequest())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    CateringOrderPageHttpResponse response = getHttpResponseEntity(result, CateringOrderPageHttpResponse.class);
                    assertCateringOrderPageHttpResponse(response);
                });
    }

    private SearchCateringOrderHttpRequest buildRequest() {
        SearchCateringOrderHttpRequest httpRequest = new SearchCateringOrderHttpRequest();
        // Page
        httpRequest.setPageIndex(1);
        httpRequest.setPageSize(2);
        // Condition
        SearchCateringOrderHttpRequest.Condition condition = httpRequest.getCondition();
        condition.setStatus(PLACED);
        condition.setCustomerCount(2);
        condition.setTableNo("A08");
        // Sort Fields
        httpRequest.getSortFields().add(new SearchCateringOrderHttpRequest.SortField("total_price", true));
        return httpRequest;
    }

    private void assertCateringOrderPageHttpResponse(CateringOrderPageHttpResponse response) {
        assertNotNull(response);
        assertEquals(Integer.valueOf(1), response.getPageIndex());
        assertEquals(Integer.valueOf(2), response.getPageSize());
        assertEquals(Integer.valueOf(1), response.getTotalCount());
        assertEquals(Integer.valueOf(1), response.getTotalPageCount());
        List<CateringOrderPageHttpResponse.Record> records = response.getRecords();
        assertNotNull(records);
        int recordSize = records.size();
        assertEquals(1, recordSize);
        records.forEach(this::assertCateringOrderPageHttpResponseRecord);
    }

    private void assertCateringOrderPageHttpResponseRecord(CateringOrderPageHttpResponse.Record record) {
        assertNotNull(record);
        assertNotNull(record.getId());
        assertNotNull(record.getTenantId());
        assertNotNull(record.getAuditing());
        assertNotNull(record.getAuditing().getCreatedAt());
        assertNotNull(record.getAuditing().getCreatedBy());
        if (Objects.equals(record.getVersion(), 1L)) {
            assertNull(record.getAuditing().getLastModifiedAt());
            assertNull(record.getAuditing().getLastModifiedBy());
        } else {
            assertNotNull(record.getAuditing().getLastModifiedAt());
            assertNotNull(record.getAuditing().getLastModifiedBy());
        }
        assertNotNull(record.getStatus());
        assertNotNull(record.getTableNo());
        assertNotNull(record.getCustomerCount());
        assertNotNull(record.getTotalPrice());
        assertNotNull(record.getShopSnapshotOnPlace());
        assertNotNull(record.getShopSnapshotOnPlace().getId());
        assertNotNull(record.getShopSnapshotOnPlace().getName());
        assertNotNull(record.getShopSnapshotOnPlace().getBusinessNo());
    }
}
