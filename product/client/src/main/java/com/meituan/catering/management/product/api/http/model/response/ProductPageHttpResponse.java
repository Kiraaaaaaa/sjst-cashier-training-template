package com.meituan.catering.management.product.api.http.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 搜索商品的Http返回体
 */
@Data
@ApiModel("搜索商品的Http返回体")
public class ProductPageHttpResponse {

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
    }
}