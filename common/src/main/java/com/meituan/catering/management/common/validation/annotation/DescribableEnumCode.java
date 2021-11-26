package com.meituan.catering.management.common.validation.annotation;


import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import com.meituan.catering.management.common.validation.validator.DescribableEnumCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证一个指定的{@link Number}类型的code是否在指定实现了{@link DescribableEnum}接口的{@link Enum}{@link DescribableEnum#getCode()}范围内
 * <p>
 * NOTE: 如果目标为空，则不触发检查，如果需要请自行使用{@link javax.validation.constraints.NotNull}
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Repeatable(DescribableEnumCode.List.class)
@Documented
@Constraint(validatedBy = {DescribableEnumCodeValidator.class})
public @interface DescribableEnumCode {

    Class<? extends DescribableEnum> enumClazz();

    String message() default " 不在合规的枚举code范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    @Documented @interface List {

        DescribableEnumCode[] value();
    }
}
