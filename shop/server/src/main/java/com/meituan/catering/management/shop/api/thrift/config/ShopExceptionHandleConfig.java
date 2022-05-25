package com.meituan.catering.management.shop.api.thrift.config;

import com.meituan.catering.management.common.api.http.config.LogConfig;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.shop.api.http.model.response.ShopDetailHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/23 15:13
 * @ClassName: ShopExceptionHandleConfig
 */
@Order(1)
@RestControllerAdvice(basePackages = "com.meituan.catering.management.shop.api.http.controller")
public class ShopExceptionHandleConfig {

    @ExceptionHandler(RuntimeException.class)
    public ShopDetailHttpResponse handleBizException(BizException e){
        LogConfig.writeLog(e,ShopExceptionHandleConfig.class);
        ShopDetailHttpResponse response = new ShopDetailHttpResponse();
        response.setStatus(StatusHelper.failure(e.getErrorCode()));
        return response;
    }
}
