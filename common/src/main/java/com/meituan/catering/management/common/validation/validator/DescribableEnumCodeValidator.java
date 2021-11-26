package com.meituan.catering.management.common.validation.validator;

import com.meituan.catering.management.common.model.enumeration.DescribableEnum;
import com.meituan.catering.management.common.validation.annotation.DescribableEnumCode;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * 针对{@link DescribableEnumCode}注解标识的{@link Number}目标的验证逻辑
 */
@Component
public class DescribableEnumCodeValidator implements ConstraintValidator<DescribableEnumCode, Number> {

    private DescribableEnum[] enumCandidates;

    @Override
    public void initialize(DescribableEnumCode constraintAnnotation) {
        this.enumCandidates = constraintAnnotation.enumClazz().getEnumConstants();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int intValue = value.intValue();
        return Arrays.stream(enumCandidates).anyMatch(candidate -> Objects.equals(candidate.getCode(), intValue));
    }
}
