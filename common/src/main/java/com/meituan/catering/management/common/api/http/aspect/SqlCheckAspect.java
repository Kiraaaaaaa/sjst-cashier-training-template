package com.meituan.catering.management.common.api.http.aspect;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String request = args[args.length-1].toString();

        Pattern sqlPattern = Pattern.compile(
                "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete"
                        + "|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)");
        Matcher matcher = sqlPattern.matcher(request);
        if (matcher.find()){
            throw new BizException(ErrorCode.ILLEGAL_CODE_ERROR);
        }
        return joinPoint.proceed();
    }
}
