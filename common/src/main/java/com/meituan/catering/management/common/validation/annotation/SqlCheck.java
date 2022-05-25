package com.meituan.catering.management.common.validation.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/23 20:42
 * @ClassName: SqlCheck
 */
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlCheck {}
