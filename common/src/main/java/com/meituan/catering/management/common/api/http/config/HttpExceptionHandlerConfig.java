package com.meituan.catering.management.common.api.http.config;

import com.google.common.collect.ImmutableMap;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 公共Web异常拦截器
 *
 * @author dulinfeng
 */
@ControllerAdvice
public class HttpExceptionHandlerConfig {


    @Resource
    private LogConfig logConfig;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildGlobalModelAndView(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return buildGlobalModelAndView(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleRuntimeException(BizException ex){
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(StatusHelper.failure(ex.getErrorCode()));
        logConfig.writeLog(ex,HttpExceptionHandlerConfig.class);
        return baseResponse;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return buildGlobalModelAndView(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> buildGlobalModelAndView(Exception ex, HttpStatus httpStatus) {
        logConfig.writeLog(ex,HttpExceptionHandlerConfig.class);
        return ResponseEntity
                .status(httpStatus)
                .body(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindException(BindException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ImmutableMap.of(
                        "message", "请求参数验证失败，请根据规则进行修改。",
                        "errors", ex.getAllErrors()
                ));
    }


}
