package com.meituan.catering.management.product.api.thrift.config;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
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
public class ProductHttpExceptionHandlerConfig {

    @ExceptionHandler(NullPointerException.class)
    public BaseResponse handleNullPointException(NullPointerException ex) {
        return buildModelAndView(ErrorCode.PARAM_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public BaseResponse handleIllegalStateException(IllegalStateException ex) {
        return buildModelAndView(ErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleRuntimeException(RuntimeException ex) {
        return buildModelAndView(ErrorCode.REPEAT_ERROR);
    }

    private BaseResponse buildModelAndView(ErrorCode errorCode) {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(StatusHelper.failure(errorCode));
        return baseResponse;
    }
}
