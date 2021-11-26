package com.meituan.catering.management.shop.api.http.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meituan.catering.management.CateringManagementShopApplication;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import com.meituan.catering.management.shop.api.http.model.request.CloseShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.CreateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.OpenShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.SaveShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.SearchShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.UpdateShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.response.ShopDetailHttpResponse;
import com.meituan.catering.management.shop.api.http.model.response.ShopPageHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.meituan.catering.management.shop.api.http.controller.ShopControllerTest.RequestConstant.*;
import static com.meituan.catering.management.shop.api.http.controller.ShopControllerTest.UserContextConstant.TENANT_ID;
import static com.meituan.catering.management.shop.api.http.controller.ShopControllerTest.UserContextConstant.USER_ID;
import static com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum.DIRECT_SALES;
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
@SpringBootTest(classes = {CateringManagementShopApplication.class})
@ActiveProfiles({"test"})
@WebAppConfiguration
@Transactional
@Rollback
@Sql({"/schema-h2.sql", "/data-h2.sql"})
public class ShopControllerTest {

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
    public void testCreate() throws Exception {
        Date startTime = setMilliseconds(new Date(), 0);
        CreateShopHttpRequest httpRequest = buildCreateShopHttpRequest();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/shop");
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    Date endTime = setMilliseconds(addSeconds(new Date(), 1), 0);
                    ShopDetailHttpResponse response = getHttpResponseEntity(result, ShopDetailHttpResponse.class);
                    assertCreateShopHttpResponse(startTime, endTime, response);
                });
    }

    @Test
    public void testUpdate() throws Exception {
        Date startTime = setMilliseconds(new Date(), 0);
        UpdateShopHttpRequest httpRequest = buildUpdateShopHttpRequest();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.put(
                "/shop/" + SaveShopConstant.UPDATING_BUSINESS_NO);
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    Date endTime = setMilliseconds(addSeconds(new Date(), 1), 0);
                    ShopDetailHttpResponse response = getHttpResponseEntity(result, ShopDetailHttpResponse.class);
                    assertUpdateShopHttpResponse(startTime, endTime, response);
                });
    }

    @Test
    public void testSearchForPage() throws Exception {
        SearchShopHttpRequest httpRequest = buildSearchShopHttpRequest();
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/shop/search");
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ShopPageHttpResponse response = getHttpResponseEntity(result, ShopPageHttpResponse.class);
                    assertShopPageHttpResponse(response);
                });
    }

    @Test
    public void testFindByBusinessNo() throws Exception {
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.get(
                "/shop/" + SaveShopConstant.UPDATING_BUSINESS_NO);
        appendHeaders(postRequest);
        mvc.perform(postRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ShopDetailHttpResponse response = getHttpResponseEntity(result, ShopDetailHttpResponse.class);
                    assertFindShopHttpResponse(response);
                });
    }

    @Test
    public void testOpenForDisabledShop() throws Exception {
        OpenShopHttpRequest httpRequest = new OpenShopHttpRequest();
        httpRequest.setVersion(3);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/shop/" + ChangeShopEnabledConstant.DISABLED_SHOP_BUSINESS_NO + "/open");
        appendHeaders(postRequest.content(JSON.toJSONString(httpRequest)));
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ShopDetailHttpResponse response = getHttpResponseEntity(result, ShopDetailHttpResponse.class);
                    assertOpenShopHttpResponse(response);
                });
    }

    @Test
    public void testOpenForEnabledShop() throws Exception {
        OpenShopHttpRequest httpRequest = new OpenShopHttpRequest();
        httpRequest.setVersion(3);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/shop/" + ChangeShopEnabledConstant.ENABLED_SHOP_BUSINESS_NO + "/open");
        appendHeaders(postRequest.content(JSON.toJSONString(httpRequest)));
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCloseForEnabledShop() throws Exception {
        CloseShopHttpRequest httpRequest = new CloseShopHttpRequest();
        httpRequest.setVersion(1);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/shop/" + ChangeShopEnabledConstant.ENABLED_SHOP_BUSINESS_NO + "/close");
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    ShopDetailHttpResponse response = getHttpResponseEntity(result, ShopDetailHttpResponse.class);
                    assertCloseShopHttpResponse(response);
                });
    }

    @Test
    public void testCloseForDisabledShop() throws Exception {
        CloseShopHttpRequest httpRequest = new CloseShopHttpRequest();
        httpRequest.setVersion(1);
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(
                "/shop/" + ChangeShopEnabledConstant.DISABLED_SHOP_BUSINESS_NO + "/close");
        appendHeaders(postRequest);
        mvc.perform(postRequest.content(JSON.toJSONString(httpRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    private CreateShopHttpRequest buildCreateShopHttpRequest() {
        return buildSaveShopHttpRequest(new CreateShopHttpRequest());
    }

    private UpdateShopHttpRequest buildUpdateShopHttpRequest() {
        UpdateShopHttpRequest httpRequest = buildSaveShopHttpRequest(new UpdateShopHttpRequest());
        httpRequest.setVersion(2);
        return httpRequest;
    }

    private <T extends SaveShopHttpRequest> T buildSaveShopHttpRequest(T httpRequest) {
        httpRequest.setName(SaveShopConstant.NAME);
        httpRequest.setBusinessType(SaveShopConstant.BUSINESS_TYPE);
        httpRequest.setManagementType(SaveShopConstant.MANAGEMENT_TYPE);
        httpRequest.getContact().setTelephone(SaveShopConstant.CONTACT_TELEPHONE);
        httpRequest.getContact().setCellphone(SaveShopConstant.CONTACT_CELLPHONE);
        httpRequest.getContact().setName(SaveShopConstant.CONTACT_NAME);
        httpRequest.getContact().setAddress(SaveShopConstant.CONTACT_ADDRESS);
        httpRequest.getOpeningHours().setOpenTime(SaveShopConstant.OPENING_HOURS_START);
        httpRequest.getOpeningHours().setCloseTime(SaveShopConstant.OPENING_HOURS_END);
        httpRequest.setBusinessArea(SaveShopConstant.BUSINESS_AREA);
        httpRequest.setComment(SaveShopConstant.COMMENT);
        return httpRequest;
    }

    private SearchShopHttpRequest buildSearchShopHttpRequest() {
        SearchShopHttpRequest httpRequest = new SearchShopHttpRequest();
        // Page
        httpRequest.setPageIndex(PAGE_INDEX);
        httpRequest.setPageSize(PAGE_SIZE);
        // Condition
        SearchShopHttpRequest.Condition condition = httpRequest.getCondition();
        condition.setKeyword("37");
        // httpRequest.getCondition().setBusinessTypeCode(BusinessTypeEnum.DINNER.getCode());
        condition.getManagementTypes().add(DIRECT_SALES);
        condition.setEnabled(true);
        // Sort Fields
        httpRequest.getSortFields().add(new SearchShopHttpRequest.SortField("business_no", true));
        return httpRequest;
    }

    private void appendHeaders(MockHttpServletRequestBuilder postRequest) {
        postRequest
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name())
                .accept(APPLICATION_JSON)
                .header("tenantId", TENANT_ID)
                .header("userId", USER_ID);
    }

    private <T> T getHttpResponseEntity(MvcResult result, Class<T> entityType) throws Exception {
        result.getResponse().setCharacterEncoding("UTF-8");
        return OM.readValue(
                result.getResponse().getContentAsString(),
                entityType);
    }

    private void assertCreateShopHttpResponse(Date startTime, Date endTime, ShopDetailHttpResponse response) {
        assertSaveShopHttpResponse(response);
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertTrue(response.getAuditing().getCreatedAt().compareTo(startTime) >= 0);
        assertTrue(response.getAuditing().getCreatedAt().compareTo(endTime) <= 0);
        assertNull(response.getAuditing().getLastModifiedAt());
        assertNull(response.getAuditing().getLastModifiedBy());
        assertEquals((Integer) 1, response.getVersion());
    }

    private void assertUpdateShopHttpResponse(Date startTime, Date endTime, ShopDetailHttpResponse response) {
        assertSaveShopHttpResponse(response);
        assertEquals(SaveShopConstant.UPDATING_BUSINESS_NO, response.getBusinessNo());
        assertEquals(USER_ID, response.getAuditing().getCreatedBy());
        assertTrue(response.getAuditing().getCreatedAt().compareTo(startTime) <= 0);
        assertEquals(USER_ID, response.getAuditing().getLastModifiedBy());
        assertTrue(response.getAuditing().getLastModifiedAt().compareTo(startTime) >= 0);
        assertTrue(response.getAuditing().getLastModifiedAt().compareTo(endTime) <= 0);
        assertEquals((Integer) 3, response.getVersion());
    }

    private void assertSaveShopHttpResponse(ShopDetailHttpResponse response) {
        assertNotNull(response);
        assertTrue(response.getId() > 0L);
        assertEquals(TENANT_ID, response.getTenantId());
        assertTrue(NumberUtils.isDigits(response.getBusinessNo()));
        assertEquals(10, StringUtils.length(response.getBusinessNo()));
        assertEquals(SaveShopConstant.NAME, response.getName());
        assertEquals(SaveShopConstant.BUSINESS_TYPE, response.getBusinessType());
        assertEquals(SaveShopConstant.BUSINESS_TYPE.getName(), response.getBusinessType().getName());
        assertEquals(SaveShopConstant.CONTACT_TELEPHONE, response.getContact().getTelephone());
        assertEquals(SaveShopConstant.CONTACT_CELLPHONE, response.getContact().getCellphone());
        assertEquals(SaveShopConstant.CONTACT_NAME, response.getContact().getName());
        assertEquals(SaveShopConstant.CONTACT_ADDRESS, response.getContact().getAddress());
        assertEquals(SaveShopConstant.MANAGEMENT_TYPE, response.getManagementType());
        assertEquals(SaveShopConstant.MANAGEMENT_TYPE.getName(), response.getManagementType().getName());
        assertEquals(SaveShopConstant.OPENING_HOURS_START, response.getOpeningHours().getOpenTime());
        assertEquals(SaveShopConstant.OPENING_HOURS_END, response.getOpeningHours().getCloseTime());
        assertEquals(SaveShopConstant.BUSINESS_AREA, response.getBusinessArea());
        assertEquals(SaveShopConstant.COMMENT, response.getComment());
        assertEquals(Boolean.TRUE, response.getEnabled());
    }

    private void assertFindShopHttpResponse(ShopDetailHttpResponse response) {
        assertNotNull(response);
        assertEquals(FindShopConstant.ID, response.getId());
        assertEquals(TENANT_ID, response.getTenantId());
        assertEquals(FindShopConstant.BUSINESS_NO, response.getBusinessNo());
        assertEquals(FindShopConstant.NAME, response.getName());
        assertEquals(FindShopConstant.BUSINESS_TYPE, response.getBusinessType());
        assertEquals(FindShopConstant.CONTACT_TELEPHONE, response.getContact().getTelephone());
        assertEquals(FindShopConstant.CONTACT_CELLPHONE, response.getContact().getCellphone());
        assertEquals(FindShopConstant.CONTACT_NAME, response.getContact().getName());
        assertEquals(FindShopConstant.CONTACT_ADDRESS, response.getContact().getAddress());
        assertEquals(FindShopConstant.MANAGEMENT_TYPE, response.getManagementType());
        assertEquals(FindShopConstant.OPENING_HOURS_START, response.getOpeningHours().getOpenTime());
        assertEquals(FindShopConstant.OPENING_HOURS_END, response.getOpeningHours().getCloseTime());
        assertEquals(FindShopConstant.BUSINESS_AREA, response.getBusinessArea());
        assertEquals(FindShopConstant.COMMENT, response.getComment());
        assertEquals(FindShopConstant.ENABLED, response.getEnabled());
        assertEquals(FindShopConstant.VERSION, response.getVersion());
        assertEquals(FindShopConstant.AUDITING_CREATED_BY, response.getAuditing().getCreatedBy());
        assertEquals(FindShopConstant.AUDITING_CREATED_AT, (Long) response.getAuditing().getCreatedAt().getTime());
        assertEquals(FindShopConstant.AUDITING_LAST_MODIFIED_BY, response.getAuditing().getLastModifiedBy());
        assertEquals(FindShopConstant.AUDITING_LAST_MODIFIED_AT, (Long) response.getAuditing().getLastModifiedAt().getTime());
        assertEquals((Integer) 2, response.getVersion());
    }

    private void assertShopPageHttpResponse(ShopPageHttpResponse response) {
        assertNotNull(response);
        assertEquals(PAGE_INDEX, response.getPageIndex());
        assertEquals(PAGE_SIZE, response.getPageSize());
        assertEquals(TOTAL_COUNT, response.getTotalCount());
        assertEquals(TOTAL_PAGE_COUNT, response.getTotalPageCount());
        List<ShopPageHttpResponse.Record> records = response.getRecords();
        assertNotNull(records);
        int recordSize = records.size();
        assertEquals(PAGE_SIZE, (Integer) recordSize);
        records.forEach(record -> {
            assertNotNull(record);
            assertNotNull(record.getId());
            assertNotNull(record.getTenantId());
            assertNotNull(record.getVersion());
            assertTrue(isNotBlank(record.getBusinessNo()));
            assertTrue(isNotBlank(record.getName()));
            assertNotNull(record.getBusinessType());
            assertNotNull(record.getContact());
            assertNotNull(record.getContact().getTelephone());
            assertNotNull(record.getContact().getCellphone());
            assertNotNull(record.getContact().getName());
            assertNotNull(record.getContact().getAddress());
            assertNotNull(record.getManagementType());
            assertNotNull(record.getOpeningHours().getOpenTime());
            assertNotNull(record.getOpeningHours().getCloseTime());
            assertNotNull(record.getBusinessArea());
            assertNotNull(record.getComment());
            assertNotNull(record.getEnabled());
        });
        for (int i = 0; i < recordSize - 1; i++) {
            assertTrue(Long.parseLong(records.get(i).getBusinessNo()) < Long.parseLong(records.get(i + 1).getBusinessNo()));
        }
    }

    private void assertOpenShopHttpResponse(ShopDetailHttpResponse response) {
        assertNotNull(response);
        assertEquals(Boolean.TRUE, response.getEnabled());
    }

    private void assertCloseShopHttpResponse(ShopDetailHttpResponse response) {
        assertNotNull(response);
        assertEquals(Boolean.FALSE, response.getEnabled());
    }


    interface RequestConstant {

        Integer PAGE_INDEX = 1;

        Integer PAGE_SIZE = 2;

        Integer TOTAL_PAGE_COUNT = 2;

        Integer TOTAL_COUNT = 3;
    }

    interface UserContextConstant {

        Long TENANT_ID = 500L;

        Long USER_ID = 11000L;
    }

    interface SaveShopConstant {

        String NAME = "江小龙火锅";

        String UPDATING_BUSINESS_NO = "1234567890";

        BusinessTypeEnum BUSINESS_TYPE = BusinessTypeEnum.HOT_POT;

        String CONTACT_TELEPHONE = "021-83734613";

        String CONTACT_CELLPHONE = "13882618275";

        String CONTACT_NAME = "江小龙";

        String CONTACT_ADDRESS = "楚西市耀辉路389号";

        ManagementTypeEnum MANAGEMENT_TYPE = DIRECT_SALES;

        String OPENING_HOURS_START = "11:00";

        String OPENING_HOURS_END = "23:00";

        String BUSINESS_AREA = "120平米";

        String COMMENT = "当地出名的重庆火锅";

    }

    interface FindShopConstant {

        Long ID = 100L;

        String NAME = "海棠川菜馆";

        String BUSINESS_NO = "1234567890";

        BusinessTypeEnum BUSINESS_TYPE = BusinessTypeEnum.DINNER;

        String CONTACT_TELEPHONE = "028-02938102";

        String CONTACT_CELLPHONE = "15827617283";

        String CONTACT_NAME = "刘先生";

        String CONTACT_ADDRESS = "汉东市昌平路192号";

        ManagementTypeEnum MANAGEMENT_TYPE = DIRECT_SALES;

        String OPENING_HOURS_START = "10:00";

        String OPENING_HOURS_END = "22:00";

        String BUSINESS_AREA = "80平米";

        String COMMENT = "主营经典川菜系列";

        Boolean ENABLED = Boolean.TRUE;

        Integer VERSION = 2;

        Long AUDITING_CREATED_BY = 11000L;

        Long AUDITING_CREATED_AT = 1627788720000L;

        Long AUDITING_LAST_MODIFIED_BY = 11000L;

        Long AUDITING_LAST_MODIFIED_AT = 1627791120000L;
    }

    interface ChangeShopEnabledConstant {

        String ENABLED_SHOP_BUSINESS_NO = "1234567895";

        String DISABLED_SHOP_BUSINESS_NO = "1234567894";

    }
}
