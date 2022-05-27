package com.meituan.catering.management.product.biz.model;

import com.meituan.catering.management.common.model.biz.BaseBO;
import com.meituan.catering.management.product.api.http.model.dto.ProductDetailHttpDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品BO定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductBO extends BaseBO {

    private Boolean enabled;

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