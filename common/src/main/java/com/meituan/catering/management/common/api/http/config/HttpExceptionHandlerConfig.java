package com.meituan.catering.management.common.api.http.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * 公共Web异常拦截器
 */
@ControllerAdvice
public class HttpExceptionHandlerConfig {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildGlobalModelAndView(ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return buildGlobalModelAndView(ex);
    }

    private ResponseEntity<String> buildGlobalModelAndView(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
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
