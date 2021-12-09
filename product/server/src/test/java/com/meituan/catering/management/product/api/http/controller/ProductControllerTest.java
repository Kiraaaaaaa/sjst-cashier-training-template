package com.meituan.catering.management.product.api.http.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meituan.catering.management.CateringManagementProductApplication;
import com.meituan.catering.management.product.api.http.model.request.CreateProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.DisableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.EnableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.SaveProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.SearchProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.UpdateProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.response.ProductDetailHttpResponse;
import com.meituan.catering.management.product.api.http.model.response.ProductPageHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.meituan.catering.management.product.api.http.controller.ProductControllerTest.UserContextConstant.TENANT_ID;
import static com.meituan.catering.management.product.api.http.controller.ProductControllerTest.UserContextConstant.USER_ID;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.time.DateUtils.addSeconds;
import static org.apache.commons.lang3.time.DateUtils.setMilliseconds;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 门店管理的集成测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CateringManagementProductApplication.class})
@ActiveProfiles({"test"})
@WebAppConfiguration
@Transactional
@Rollback
@Sql({"/schema-h2.sql", "/data-h2.sql"})
public class ProductControllerTest {

    private static final ObjectMapper OM = new ObjectMapper();

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        OM.setDateFormat(dateFormat);
    }

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }))
                .build();
    }

    @Test
    public void testFindById() throws Exception {
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.get(
                "/product/100");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest);
        mvc.perform(postRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ProductDetailHttpResponse response = getHttpResponseEntity(result, ProductDetailHttpResponse.class);
                    assertNotNull(response);
                    assertEquals((Long) 100L, response.getId());
                    assertEquals(TENANT_ID, response.getTenantId());
                    assertEquals("宫保鸡丁", response.getName());
                    assertEquals(new BigDecimal("26.80"), response.getUnitPrice());
                    assertEquals("元/份", response.getUnitOfMeasure());
                    assertEquals(new BigDecimal("1.00"), response.getMinSalesQuantity());
                    assertEquals(new BigDecimal("1.00"), response.getIncreaseSalesQuantity());
                    assertEquals("是汉族传统经典的名菜,属川菜名菜。创始人为四川地区居民,后被宫保丁宝桢改良发扬光大,流传至今。此道菜也被归纳为北京宫廷菜。 红而不辣、辣而不猛、香辣味浓、肉质滑脆。", response.getDescription());
                    assertEquals(Boolean.TRUE, response.getEnabled());
                    assertEquals((Integer) 2, response.getVersion());
                    assertEquals((Long) 11000L, response.getAuditing().getCreatedBy());
                    assertEquals((Long) 1627788720000L, (Long) response.getAuditing().getCreatedAt().getTime());
                    assertEquals((Long) 11000L, response.getAuditing().getLastModifiedBy());
                    assertEquals((Long) 1627791120000L, (Long) response.getAuditing().getLastModifiedAt().getTime());
                    assertEquals((Integer) 2, response.getVersion());
                    // Product Accessory
                    assertEquals(1, response.getAccessoryGroups().size());
                    ProductDetailHttpResponse.AccessoryGroup accessoryGroup = response.getAccessoryGroups().get(0);
                    assertEquals("配菜", accessoryGroup.getName());
                    ProductDetailHttpResponse.AccessoryGroup.Option option1 = accessoryGroup.getOptions().get(0);
                    assertEquals("泡萝卜", option1.getName());
                    assertEquals(new BigDecimal("1.00"), option1.getUnitPrice());
                    assertEquals("份", option1.getUnitOfMeasure());
                    ProductDetailHttpResponse.AccessoryGroup.Option option2 = accessoryGroup.getOptions().get(1);
                    assertEquals("辣椒酱", option2.getName());
                    assertEquals(new BigDecimal("1.00"), option2.getUnitPrice());
                    assertEquals("两", option2.getUnitOfMeasure());
                    ProductDetailHttpResponse.AccessoryGroup.Option option3 = accessoryGroup.getOptions().get(2);
                    assertEquals("米饭", option3.getName());
                    assertEquals(new BigDecimal("2.00"), option3.getUnitPrice());
                    assertEquals("碗", option3.getUnitOfMeasure());
                    // Product Method
                    assertEquals(1, response.getMethodGroups().size());
                    ProductDetailHttpResponse.MethodGroup methodGroup = response.getMethodGroups().get(0);
                    assertEquals("辣度", methodGroup.getName());
                    assertEquals("微辣", methodGroup.getOptions().get(0).getName());
                    assertEquals("中辣", methodGroup.getOptions().get(1).getName());
                    assertEquals("重辣", methodGroup.getOptions().get(2).getName());

                });
    }

    @Test
    public void testSearchForPage() throws Exception {
        SearchProductHttpRequest httpRequest = buildSearchProductHttpRequest();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/product/search");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ProductPageHttpResponse response = getHttpResponseEntity(result, ProductPageHttpResponse.class);
                    assertNotNull(response);
                    assertEquals((Integer) 1, response.getPageIndex());
                    assertEquals((Integer) 2, response.getPageSize());
                    assertEquals((Integer) 4, response.getTotalCount());
                    assertEquals((Integer) 2, response.getTotalPageCount());
                    List<ProductPageHttpResponse.Record> records = response.getRecords();
                    assertNotNull(records);
                    int recordSize = records.size();
                    assertEquals((Integer) 2, (Integer) recordSize);
                    records.forEach(record -> {
                        assertNotNull(record);
                        assertNotNull(record.getId());
                        assertNotNull(record.getTenantId());
                        assertTrue(isNotBlank(record.getName()));
                        assertNotNull(record.getUnitPrice());
                        assertTrue(isNotBlank(record.getUnitOfMeasure()));
                        assertNotNull(record.getMinSalesQuantity());
                        assertNotNull(record.getIncreaseSalesQuantity());
                        assertTrue(isNotBlank(record.getDescription()));
                        assertNotNull(record.getEnabled());
                        assertNotNull(record.getVersion());
                    });
                });
    }

    private SearchProductHttpRequest buildSearchProductHttpRequest() {
        SearchProductHttpRequest httpRequest = new SearchProductHttpRequest();
        // Page
        httpRequest.setPageIndex(1);
        httpRequest.setPageSize(2);
        // Condition
        SearchProductHttpRequest.Condition condition = httpRequest.getCondition();
        condition.setName("肉");
        condition.getUnitPrice().setFrom(BigDecimal.valueOf(10L));
        condition.getUnitPrice().setTo(BigDecimal.valueOf(100L));
        condition.setEnabled(true);
        // Sort Fields
        httpRequest.getSortFields().add(new SearchProductHttpRequest.SortField("unit_price", true));
        return httpRequest;
    }

    @Test
    public void testCreate() throws Exception {
        Date startTime = setMilliseconds(new Date(), 0);
        CreateProductHttpRequest httpRequest = buildCreateProductHttpRequest();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/product");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(result -> {
                    Date endTime = setMilliseconds(addSeconds(new Date(), 1), 0);
                    ProductDetailHttpResponse response = getHttpResponseEntity(result, ProductDetailHttpResponse.class);
                    assertSaveProductHttpResponse(response);
                    assertEquals(USER_ID, response.getAuditing().getCreatedBy());
                    assertTrue(response.getAuditing().getCreatedAt().compareTo(startTime) >= 0);
                    assertTrue(response.getAuditing().getCreatedAt().compareTo(endTime) <= 0);
                    assertNull(response.getAuditing().getLastModifiedAt());
                    assertNull(response.getAuditing().getLastModifiedBy());
                    assertEquals((Integer) 1, response.getVersion());
                });
    }

    private CreateProductHttpRequest buildCreateProductHttpRequest() {
        return buildSaveProductHttpRequest(new CreateProductHttpRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        Date startTime = setMilliseconds(new Date(), 0);
        UpdateProductHttpRequest httpRequest = buildUpdateProductHttpRequest();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.put(
                "/product/107");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    Date endTime = setMilliseconds(addSeconds(new Date(), 1), 0);
                    ProductDetailHttpResponse response = getHttpResponseEntity(result, ProductDetailHttpResponse.class);
                    assertSaveProductHttpResponse(response);
                    assertEquals(USER_ID, response.getAuditing().getCreatedBy());
                    assertTrue(response.getAuditing().getCreatedAt().compareTo(startTime) <= 0);
                    assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
                    assertTrue(response.getAuditing().getLastModifiedAt().compareTo(startTime) >= 0);
                    assertTrue(response.getAuditing().getLastModifiedAt().compareTo(endTime) <= 0);
                    assertEquals((Integer) 5, response.getVersion());
                });
    }

    private UpdateProductHttpRequest buildUpdateProductHttpRequest() {
        UpdateProductHttpRequest httpRequest = buildSaveProductHttpRequest(new UpdateProductHttpRequest());
        httpRequest.setVersion(4);
        return httpRequest;
    }

    private <T extends SaveProductHttpRequest> T buildSaveProductHttpRequest(T httpRequest) {
        // Product
        httpRequest.setName("剁椒鱼头");
        httpRequest.setUnitPrice(new BigDecimal("69.80"));
        httpRequest.setUnitOfMeasure("份");
        httpRequest.setMinSalesQuantity(new BigDecimal("1.00"));
        httpRequest.setIncreaseSalesQuantity(new BigDecimal("1.00"));
        httpRequest.setDescription("通常以鳙鱼鱼头、剁椒为主料，配以豉油、姜、葱、蒜等辅料蒸制而成。菜品色泽红亮、味浓、肉质细嫩。肥而不腻、口感软糯、鲜辣适口。");
        // Product Accessory
        SaveProductHttpRequest.AccessoryGroup accessoryGroup = new SaveProductHttpRequest.AccessoryGroup();
        accessoryGroup.setName("主食");
        SaveProductHttpRequest.AccessoryGroup.Option option1 = new SaveProductHttpRequest.AccessoryGroup.Option();
        option1.setName("米饭");
        option1.setUnitPrice(new BigDecimal("1.00"));
        option1.setUnitOfMeasure("份");
        accessoryGroup.getOptions().add(option1);
        SaveProductHttpRequest.AccessoryGroup.Option option2 = new SaveProductHttpRequest.AccessoryGroup.Option();
        option2.setName("挂面");
        option2.setUnitPrice(new BigDecimal("100.00"));
        option2.setUnitOfMeasure("克");
        accessoryGroup.getOptions().add(option2);
        SaveProductHttpRequest.AccessoryGroup.Option option3 = new SaveProductHttpRequest.AccessoryGroup.Option();
        option3.setName("水面");
        option3.setUnitPrice(new BigDecimal("100.00"));
        option3.setUnitOfMeasure("克");
        accessoryGroup.getOptions().add(option3);
        httpRequest.getAccessoryGroups().add(accessoryGroup);
        // Product Method
        SaveProductHttpRequest.MethodGroup methodGroup = new SaveProductHttpRequest.MethodGroup();
        methodGroup.setName("剁椒量");
        SaveProductHttpRequest.MethodGroup.Option methodOption1 = new SaveProductHttpRequest.MethodGroup.Option();
        methodOption1.setName("平均");
        methodGroup.getOptions().add(methodOption1);
        SaveProductHttpRequest.MethodGroup.Option methodOption2 = new SaveProductHttpRequest.MethodGroup.Option();
        methodOption2.setName("偏红椒");
        methodGroup.getOptions().add(methodOption2);
        SaveProductHttpRequest.MethodGroup.Option methodOption3 = new SaveProductHttpRequest.MethodGroup.Option();
        methodOption3.setName("偏青椒");
        methodGroup.getOptions().add(methodOption3);
        httpRequest.getMethodGroups().add(methodGroup);
        return httpRequest;
    }

    private void assertSaveProductHttpResponse(ProductDetailHttpResponse response) {
        assertNotNull(response);
        assertTrue(response.getId() > 0L);
        assertEquals(TENANT_ID, response.getTenantId());
        assertEquals("剁椒鱼头", response.getName());
        assertEquals(new BigDecimal("69.80"), response.getUnitPrice());
        assertEquals("份", response.getUnitOfMeasure());
        assertEquals(new BigDecimal("1.00"), response.getMinSalesQuantity());
        assertEquals(new BigDecimal("1.00"), response.getIncreaseSalesQuantity());
        assertEquals("通常以鳙鱼鱼头、剁椒为主料，配以豉油、姜、葱、蒜等辅料蒸制而成。菜品色泽红亮、味浓、肉质细嫩。肥而不腻、口感软糯、鲜辣适口。", response.getDescription());
        assertEquals(Boolean.TRUE, response.getEnabled());
        // Product Accessory
        assertEquals(1, response.getAccessoryGroups().size());
        ProductDetailHttpResponse.AccessoryGroup accessoryGroup = response.getAccessoryGroups().get(0);
        assertEquals("主食", accessoryGroup.getName());
        ProductDetailHttpResponse.AccessoryGroup.Option option1 = accessoryGroup.getOptions().get(0);
        assertEquals("米饭", option1.getName());
        assertEquals(new BigDecimal("1.00"), option1.getUnitPrice());
        assertEquals("份", option1.getUnitOfMeasure());
        ProductDetailHttpResponse.AccessoryGroup.Option option2 = accessoryGroup.getOptions().get(1);
        assertEquals("挂面", option2.getName());
        assertEquals(new BigDecimal("100.00"), option2.getUnitPrice());
        assertEquals("克", option2.getUnitOfMeasure());
        ProductDetailHttpResponse.AccessoryGroup.Option option3 = accessoryGroup.getOptions().get(2);
        assertEquals("水面", option3.getName());
        assertEquals(new BigDecimal("100.00"), option3.getUnitPrice());
        assertEquals("克", option3.getUnitOfMeasure());
        // Product Method
        assertEquals(1, response.getMethodGroups().size());
        ProductDetailHttpResponse.MethodGroup methodGroup = response.getMethodGroups().get(0);
        assertEquals("剁椒量", methodGroup.getName());
        assertEquals("平均", methodGroup.getOptions().get(0).getName());
        assertEquals("偏红椒", methodGroup.getOptions().get(1).getName());
        assertEquals("偏青椒", methodGroup.getOptions().get(2).getName());
    }

    @Test
    public void testEnableForDisabledProduct() throws Exception {
        EnableProductHttpRequest httpRequest = new EnableProductHttpRequest();
        httpRequest.setVersion(9);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/product/102/enable");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest.content(JSON.toJSONString(httpRequest)));
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ProductDetailHttpResponse response = getHttpResponseEntity(result, ProductDetailHttpResponse.class);
                    assertNotNull(response);
                    assertEquals(Boolean.TRUE, response.getEnabled());
                });
    }

    @Test
    public void testEnableForEnabledProduct() throws Exception {
        EnableProductHttpRequest httpRequest = new EnableProductHttpRequest();
        httpRequest.setVersion(3);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/product/100/enable");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest.content(JSON.toJSONString(httpRequest)));
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDisableForEnabledProduct() throws Exception {
        DisableProductHttpRequest httpRequest = new DisableProductHttpRequest();
        httpRequest.setVersion(2);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/product/100/disable");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ProductDetailHttpResponse response = getHttpResponseEntity(result, ProductDetailHttpResponse.class);
                    assertNotNull(response);
                    assertEquals(Boolean.FALSE, response.getEnabled());
                });
    }

    @Test
    public void testDisableForDisabledProduct() throws Exception {
        DisableProductHttpRequest httpRequest = new DisableProductHttpRequest();
        httpRequest.setVersion(1);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/product/102/disable");
        appendUserContextToParams(postRequest);
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    interface UserContextConstant {

        Long TENANT_ID = 500L;

        Long USER_ID = 11000L;

    }


    private void appendUserContextToParams(MockHttpServletRequestBuilder postRequest) {
        postRequest
                .header("tenantId", TENANT_ID.toString())
                .header("userId", USER_ID.toString());
    }

    private void appendHeaders(MockHttpServletRequestBuilder postRequest) {
        postRequest
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name())
                .accept(APPLICATION_JSON);
    }

    private <T> T getHttpResponseEntity(MvcResult result, Class<T> entityType) throws Exception {
        result.getResponse().setCharacterEncoding("UTF-8");
        return OM.readValue(
                result.getResponse().getContentAsString(),
                entityType);
    }

}
