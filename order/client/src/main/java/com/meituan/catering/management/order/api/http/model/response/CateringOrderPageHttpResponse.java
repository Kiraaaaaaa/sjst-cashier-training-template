package com.meituan.catering.management.order.api.http.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 订单分页信息的Http返回体
 */
@Data
@ApiModel("订单分页信息的Http返回体")
public class CateringOrderPageHttpResponse {

    @ApiModelProperty("分页码")
    private Integer pageIndex;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("总条目数")
    private Integer totalCount;

    @ApiModelProperty("总页码数")
    private Integer totalPageCount;

    @ApiModelProperty("当前页的订单信息列表")
    private final List<Record> records = new LinkedList<>();

    @Data
    @ApiModel("订单分页信息项")
    public static class Record {

        @ApiModelProperty("订单ID")
        private Long id;

        @ApiModelProperty("租户ID")
        private Long tenantId;

        @ApiModelProperty("版本号")
        private Integer version;

        @ApiModelProperty("审计信息")
        private final AuditingHttpModel auditing = new AuditingHttpModel();

        @ApiModelProperty("订单状态")
        private CateringOrderStatusEnum status;

        @ApiModelProperty("座位号")
        private String tableNo;

        @ApiModelProperty("用餐人数")
        private Integer customerCount;

        @ApiModelProperty("订单总价")
        private BigDecimal totalPrice;

        @ApiModelProperty("备注")
        private String comment;

        @ApiModelProperty("订单快照")
        private final ShopSnapshot shopSnapshotOnPlace = new ShopSnapshot();

        @Data
        @ApiModel("订单门店快照")
        public static class ShopSnapshot {

            @ApiModelProperty("物理ID")
            private Long id;

            @ApiModelProperty("门店业务号")
            private String businessNo;

            @ApiModelProperty("门店名")
            private String name;
        }
    }
}