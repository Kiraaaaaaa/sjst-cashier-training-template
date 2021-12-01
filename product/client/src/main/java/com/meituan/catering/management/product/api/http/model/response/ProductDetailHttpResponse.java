package com.meituan.catering.management.product.api.http.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品详情的Http返回体
 */
@Data
@ApiModel("商品详情的Http返回体")
public class ProductDetailHttpResponse {

    @ApiModelProperty("物理ID")
    private Long id;

    @ApiModelProperty("版本号")
    private Integer version;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    @ApiModelProperty("是否为上架状态")
    private Boolean enabled;

    @ApiModelProperty("审计信息")
    private final AuditingHttpModel auditing = new AuditingHttpModel();

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("起售量")
    private BigDecimal minSalesQuantity;

    @ApiModelProperty("增售量")
    private BigDecimal increaseSalesQuantity;

    @ApiModelProperty("计量单位")
    private String unitOfMeasure;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("做法组")
    private final List<MethodGroup> methodGroups = new LinkedList<>();

    @ApiModelProperty("加料组")
    private final List<AccessoryGroup> accessoryGroups = new LinkedList<>();

    @Data
    @ApiModel("商品做法组")
    public static class MethodGroup {

        @ApiModelProperty("做法组名")
        private String name;

        @ApiModelProperty("做法项列表")
        private final List<Option> options = new LinkedList<>();

        @Data
        @ApiModel("做法项")
        public static class Option {

            @ApiModelProperty("物理ID")
            private Long id;

            @ApiModelProperty("名称")
            private String name;

        }
    }

    @Data
    @ApiModel("商品加料组")
    public static class AccessoryGroup {

        @ApiModelProperty("加料组名")
        private String name;

        @ApiModelProperty("加料项列表")
        private final List<Option> options = new LinkedList<>();

        @Data
        @ApiModel("加料项")
        public static class Option {

            @ApiModelProperty("物理ID")
            private Long id;

            @ApiModelProperty("名称")
            private String name;

            @ApiModelProperty("单价")
            private BigDecimal unitPrice;

            @ApiModelProperty("计量单位")
            private String unitOfMeasure;

        }
    }
}