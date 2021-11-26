package com.meituan.catering.management.common.model.biz;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 数字范围
 */
@Data
public class DecimalRangeBO {

    private BigDecimal from;

    private BigDecimal to;

}
