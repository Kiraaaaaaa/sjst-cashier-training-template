package com.meituan.catering.management.product.api.http.model.dto;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mac
 */
@Data
@ApiModel("搜索商品的Http返回体")
public class ProductPageHttpDTO {
    @ApiModelProperty("分页码")
    private Integer pageIndex;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("总条目数")
    private Integer totalCount;

    @ApiModelProperty("总页码数")
    private Integer totalPageCount;

    @ApiModelProperty("当前页的商品信息列表")
    private final List<Record> records = new LinkedList<>();

    @Data
    @ApiModel("商品分页信息项")
    public static class Record {

        @ApiModelProperty("物理ID")
        private Long id;

        @ApiModelProperty("租户ID")
        private Long tenantId;

        @ApiModelProperty("版本号")
        private Integer version;

        @ApiModelProperty("审计信息")
        private final AuditingHttpModel auditing = new AuditingHttpModel();

        @ApiModelProperty("名称")
        private String name;

        @ApiModelProperty("单价")
        private BigDecimal unitPrice;

        @ApiModelProperty("计量单位")
        private String unitOfMeasure;

        @ApiModelProperty("起售量")
        private BigDecimal minSalesQuantity;

        @ApiModelProperty("增售量")
        private BigDecimal increaseSalesQuantity;

        @ApiModelProperty("描述")
        private String description;

        @ApiModelProperty("是否上架")
        private Boolean enabled;


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
}
