package com.meituan.catering.management.common.api.http.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.Boolean.TRUE;

/**
 * 针对请求header中带有的特殊的测试指令{@link RollbackForTestRequestInterceptor#SPECIFIC_HEADER_KEY}的Http请求体，默认DB会回滚其修改
 */
@Slf4j
@Component
public class RollbackForTestRequestInterceptor implements Filter {

    private static final String SPECIFIC_HEADER_KEY = "rollback_for_test";

    private static final String SPECIFIC_HEADER_VALUE = TRUE.toString();

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (StringUtils.equalsAnyIgnoreCase(httpServletRequest.getHeader(SPECIFIC_HEADER_KEY), SPECIFIC_HEADER_VALUE)) {
                transactionTemplate.executeWithoutResult(transactionStatus -> {
                    try {
                        log.info("探测到测试请求，默认回滚该请求对应的DB修改");
                        chain.doFilter(request, response);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    } finally {
                        transactionStatus.setRollbackOnly();
                        log.info("回滚该请求对应的DB修改完成");
                    }
                });
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
