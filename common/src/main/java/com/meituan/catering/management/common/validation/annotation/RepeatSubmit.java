package com.meituan.catering.management.common.validation.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/19 17:31
 * @ClassName: RepeatSubmit
 */

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
    /**
     * 防重复操作限时标记数值（存储redis限时标记数值）
     */
    String value() default "value";

    /**
     * 防重复操作过期时间（借助redis实现限时控制）
     */
    long expireSeconds() default 5;

}
