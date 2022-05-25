package com.meituan.catering.management.product.api.thrift.config;

import com.meituan.catering.management.common.api.http.config.HttpExceptionHandlerConfig;
import com.meituan.catering.management.common.api.http.config.LogConfig;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.product.api.http.model.response.ProductDetailHttpResponse;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/19 14:44
 * @ClassName: HttpExceptionHandlerConfig
 */
@Order(2)
@RestControllerAdvice()
public class ProductHttpExceptionHandlerConfig extends HttpExceptionHandlerConfig {

    @ExceptionHandler(RuntimeException.class)
    public ProductDetailHttpResponse handleBizException(BizException e){
        LogConfig.writeLog(e,ProductHttpExceptionHandlerConfig.class);
        ProductDetailHttpResponse response = new ProductDetailHttpResponse();
        response.setStatus(StatusHelper.failure(e.getErrorCode()));
        return response;
    }
}
