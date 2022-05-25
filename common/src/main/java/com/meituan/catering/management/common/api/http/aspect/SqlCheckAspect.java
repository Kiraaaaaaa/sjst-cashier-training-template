package com.meituan.catering.management.common.api.http.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 9:42
 * @ClassName: hello
 */
@Slf4j
@Component
@Aspect
public class SqlCheckAspect {

    @Pointcut("@annotation(com.meituan.catering.management.common.validation.annotation.SqlCheck)")
    public void preventDuplication() {
    }

    @Around("preventDuplication()")
    public void around(ProceedingJoinPoint joinPoint){
        joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

    }
}
