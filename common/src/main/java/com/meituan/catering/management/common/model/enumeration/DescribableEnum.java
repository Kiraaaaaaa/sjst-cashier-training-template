package com.meituan.catering.management.common.model.enumeration;

import com.facebook.swift.codec.ThriftEnum;
import com.facebook.swift.codec.ThriftEnumValue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * 可自描述的枚举类型的基类
 */
@ThriftEnum
public interface DescribableEnum {

    /**
     * @return 获取数字型的代码
     */
    @ThriftEnumValue
    int getCode();

    /**
     * @return 获取该枚举的描述名（PS：不是枚举字段的英文名，而是中文描述）
     */
    String getName();

    /**
     * @return 获取该枚举的原生名（PS：不是枚举的中文描述，而是字段的英文名）
     */
    String name();

    static <T extends DescribableEnum> T getByCode(Class<T> enumClazz, Integer code) {
        return Arrays.stream(enumClazz.getEnumConstants())
                .filter(candidate -> Objects.equals(candidate.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    static <T extends DescribableEnum, P> T getByProperty(Class<T> enumClazz, Function<T, P> property, P value) {
        return Arrays.stream(enumClazz.getEnumConstants())
                .filter(candidate -> Objects.equals(property.apply(candidate), value))
                .findFirst()
                .orElse(null);
    }
}
