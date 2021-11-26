package com.meituan.catering.management.order.api.http.controller.cases;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.meituan.catering.management.CateringManagementOrderApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CateringManagementOrderApplication.class})
@ActiveProfiles({"test"})
@WebAppConfiguration
@Transactional
@Rollback
@Sql({"/schema-h2.sql", "/data-h2.sql"})
public abstract class BaseCateringOrderControllerCase {

    protected static final Long TENANT_ID = 500L;

    protected static final Long USER_ID = 11000L;

    protected static final ObjectMapper OM = new ObjectMapper();

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

    protected MockHttpServletRequestBuilder appendHeaders(MockHttpServletRequestBuilder postRequest) {
        return postRequest
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name())
                .accept(APPLICATION_JSON)
                .header("tenantId", TENANT_ID.toString())
                .header("userId", USER_ID.toString());
    }

    protected <T> T getHttpResponseEntity(MvcResult result, Class<T> entityType) throws Exception {
        result.getResponse().setCharacterEncoding("UTF-8");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        OM.setDateFormat(dateFormat);
        return OM.readValue(
                result.getResponse().getContentAsString(),
                entityType);
    }
}
