package com.meituan.catering.management.common.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.Set;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public abstract class ValidationHelper {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 使用Hibernate Validation机制验证目标对象
     *
     * @param target 待验证的目标对象
     * @param <T>    目标对象的类型
     * @throws ConstraintViolationException 验证异常
     */
    public static <T> void validate(T target) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(target);
        if (isNotEmpty(constraintViolations)) {
            throw new ConstraintViolationException(
                    MessageFormat.format(
                            "非法的实例. target: {0}, constraintViolations: {1}",
                            target,
                            constraintViolations),
                    constraintViolations);
        }
    }
}
