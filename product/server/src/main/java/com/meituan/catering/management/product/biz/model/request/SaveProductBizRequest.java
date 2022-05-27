package com.meituan.catering.management.product.biz.model.request;

import io.micrometer.core.instrument.Meter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 13:08
 * @ClassName: SaveProductBizRequest
 */
@Data
public class SaveProductBizRequest {

    private String name;

    private BigDecimal unitPrice;

    private BigDecimal minSalesQuantity;

    private BigDecimal increaseSalesQuantity;

    private String unitOfMeasure;

    private String description;

    private final List<MethodGroup> methodGroups = new LinkedList<>();

    private final List<AccessoryGroup> accessoryGroups = new LinkedList<>();

    @Data
    public static class MethodGroup {

        private String name;

        private final List<Option> options = new LinkedList<>();

        @Data
        public static class Option {
            private Long id;

            private String name;

        }
    }

    @Data
    public static class AccessoryGroup {

        private String name;

        private final List<Option> options = new LinkedList<>();


        @Data
        public static class Option {

            private Long id;

            private String name;

            private BigDecimal unitPrice;

            private String unitOfMeasure;

        }
    }
}
