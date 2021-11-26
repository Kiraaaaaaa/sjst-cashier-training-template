package com.meituan.catering.management.order.api.http.controller.cases;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.response.CateringOrderDetailHttpResponse;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum.PLACED;
import static org.junit.Assert.*;

public class CateringOrderFindByIdCase extends BaseCateringOrderControllerCase {

    @Test
    public void test() throws Exception {
        mvc
                .perform(appendHeaders(MockMvcRequestBuilders.get("/order/catering/10000")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    CateringOrderDetailHttpResponse response = getHttpResponseEntity(result, CateringOrderDetailHttpResponse.class);
                    assertNotNull(response);
                    assertOrderHeader(response);
                    assertOrderItems(response);
                });
    }

    private void assertOrderHeader(CateringOrderDetailHttpResponse response) {
        // Base
        assertEquals(Long.valueOf(10000), response.getId());
        assertEquals(TENANT_ID, response.getTenantId());
        assertEquals(Integer.valueOf(2), response.getVersion());
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertNotNull(response.getAuditing().getCreatedAt());
        assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
        assertNotNull(response.getAuditing().getLastModifiedAt());
        // Header
        assertEquals(PLACED, response.getStatus());
        assertEquals("A08", response.getTableNo());
        assertEquals(Integer.valueOf(2), response.getCustomerCount());
        assertEquals(new BigDecimal("204.40"), response.getTotalPrice());
        assertEquals("主营经典川菜系列", response.getComment());
        // Shop
        assertEquals(Long.valueOf(100), response.getShopSnapshotOnPlace().getId());
        assertEquals("1234567890", response.getShopSnapshotOnPlace().getBusinessNo());
        assertEquals("海棠川菜馆", response.getShopSnapshotOnPlace().getName());
        // Billing
        assertNull(response.getBilling().getPromotion());
        assertNull(response.getBilling().getPaid());
        assertNull(response.getBilling().getPaymentChannel());
    }

    private void assertOrderItems(CateringOrderDetailHttpResponse response) {
        assertEquals(3, response.getItems().size());
        // Item 1
        assertOrderItem1(response);
        // Item 2
        assertOrderItem2(response);
        // Item 3
        assertOrderItem3(response);
    }

    private void assertOrderItem1(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item1 = response.getItems().get(0);
        assertEquals(Long.valueOf(100001), item1.getId());
        assertEquals(Integer.valueOf(1), item1.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PLACED, item1.getStatus());
        assertEquals("1", item1.getSeqNo());
        // Item 1 - Quantity
        assertEquals(new BigDecimal("1.00"), item1.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), item1.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("1.00"), item1.getQuantity().getLatest());
        // Item 1 - Product
        assertEquals(Long.valueOf(100), item1.getProductSnapshotOnPlace().getId());
        assertEquals("宫保鸡丁", item1.getProductSnapshotOnPlace().getName());
        assertEquals(new BigDecimal("26.80"), item1.getProductSnapshotOnPlace().getUnitPrice());
        assertEquals("元/份", item1.getProductSnapshotOnPlace().getUnitOfMeasure());
        // Item 1 - Product Method
        assertEquals(Long.valueOf(10021), item1.getProductMethodSnapshotOnPlace().getId());
        assertEquals("微辣", item1.getProductMethodSnapshotOnPlace().getName());
        assertEquals("辣度", item1.getProductMethodSnapshotOnPlace().getGroupName());

        // Item 1 - Accessories
        assertOrderItem1Accessories(item1);
    }

    private void assertOrderItem1Accessories(CateringOrderDetailHttpResponse.Item item1) {
        assertEquals(2, item1.getAccessories().size());

        // Item 1 - Accessory 1
        assertOrderItem1Accessory1(item1);
        // Item 1 - Accessory 2
        assertOrderItem1Accessory2(item1);
    }

    private void assertOrderItem1Accessory1(CateringOrderDetailHttpResponse.Item item1) {
        CateringOrderDetailHttpResponse.Item.Accessory accessory1 = item1.getAccessories().get(0);
        assertEquals(Long.valueOf(10000101), accessory1.getId());
        assertEquals(Integer.valueOf(1), accessory1.getVersion());
        assertEquals("1-1", accessory1.getSeqNo());
        // Item 1 - Accessory 1 - Quantity
        assertEquals(new BigDecimal("1.00"), accessory1.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), accessory1.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("1.00"), accessory1.getQuantity().getLatest());
        // Item 1 - Accessory 1 - Product
        assertEquals(Long.valueOf(10013), accessory1.getProductAccessorySnapshotOnPlace().getId());
        assertEquals("米饭", accessory1.getProductAccessorySnapshotOnPlace().getName());
        assertEquals("配菜", accessory1.getProductAccessorySnapshotOnPlace().getGroupName());
        assertEquals(new BigDecimal("2.00"), accessory1.getProductAccessorySnapshotOnPlace().getUnitPrice());
        assertEquals("碗", accessory1.getProductAccessorySnapshotOnPlace().getUnitOfMeasure());
    }

    private void assertOrderItem1Accessory2(CateringOrderDetailHttpResponse.Item item1) {
        CateringOrderDetailHttpResponse.Item.Accessory accessory2 = item1.getAccessories().get(1);
        assertEquals(Long.valueOf(10000102), accessory2.getId());
        assertEquals(Integer.valueOf(1), accessory2.getVersion());
        assertEquals("1-2", accessory2.getSeqNo());
        // Item 1 - Accessory 2 - Quantity
        assertEquals(new BigDecimal("1.00"), accessory2.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), accessory2.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("1.00"), accessory2.getQuantity().getLatest());
        // Item 1 - Accessory 2 - Product
        assertEquals(Long.valueOf(10011), accessory2.getProductAccessorySnapshotOnPlace().getId());
        assertEquals("泡萝卜", accessory2.getProductAccessorySnapshotOnPlace().getName());
        assertEquals("配菜", accessory2.getProductAccessorySnapshotOnPlace().getGroupName());
        assertEquals(new BigDecimal("1.00"), accessory2.getProductAccessorySnapshotOnPlace().getUnitPrice());
        assertEquals("份", accessory2.getProductAccessorySnapshotOnPlace().getUnitOfMeasure());
    }

    private void assertOrderItem2(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item2 = response.getItems().get(1);
        assertEquals(Long.valueOf(100002), item2.getId());
        assertEquals(Integer.valueOf(1), item2.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PLACED, item2.getStatus());
        assertEquals("2", item2.getSeqNo());
        // Item 2 - Quantity
        assertEquals(new BigDecimal("1.00"), item2.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), item2.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("1.00"), item2.getQuantity().getLatest());
        // Item 2 - Product
        assertEquals(Long.valueOf(101), item2.getProductSnapshotOnPlace().getId());
        assertEquals("红烧肉", item2.getProductSnapshotOnPlace().getName());
        assertEquals(new BigDecimal("48.80"), item2.getProductSnapshotOnPlace().getUnitPrice());
        assertEquals("元/份", item2.getProductSnapshotOnPlace().getUnitOfMeasure());
        // Item 2 - Product Method
        assertNull(item2.getProductMethodSnapshotOnPlace().getId());
        assertNull(item2.getProductMethodSnapshotOnPlace().getName());
        assertNull(item2.getProductMethodSnapshotOnPlace().getGroupName());
        // Item 2 - Accessories
        assertEquals(0, item2.getAccessories().size());
    }

    private void assertOrderItem3(CateringOrderDetailHttpResponse response) {
        CateringOrderDetailHttpResponse.Item item3 = response.getItems().get(2);
        assertEquals(Long.valueOf(100003), item3.getId());
        assertEquals(Integer.valueOf(1), item3.getVersion());
        assertEquals(CateringOrderItemStatusEnum.PLACED, item3.getStatus());
        assertEquals("3", item3.getSeqNo());
        // Item 3 - Quantity
        assertEquals(new BigDecimal("1.00"), item3.getQuantity().getOnPlace());
        assertEquals(new BigDecimal("0.00"), item3.getQuantity().getOnProduce());
        assertEquals(new BigDecimal("1.00"), item3.getQuantity().getLatest());
        // Item 3 - Product
        assertEquals(Long.valueOf(102), item3.getProductSnapshotOnPlace().getId());
        assertEquals("松鼠鱼", item3.getProductSnapshotOnPlace().getName());
        assertEquals(new BigDecimal("128.80"), item3.getProductSnapshotOnPlace().getUnitPrice());
        assertEquals("元/份", item3.getProductSnapshotOnPlace().getUnitOfMeasure());
        // Item 3 - Product Method
        assertNull(item3.getProductMethodSnapshotOnPlace().getId());
        assertNull(item3.getProductMethodSnapshotOnPlace().getName());
        assertNull(item3.getProductMethodSnapshotOnPlace().getGroupName());
        // Item 3 - Accessories
        assertEquals(0, item3.getAccessories().size());
    }
}
