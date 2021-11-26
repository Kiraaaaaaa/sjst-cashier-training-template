package com.meituan.catering.management.order.api.http.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 订单详情的Http返回体
 */
@Data
@ApiModel("订单详情的Http返回体")
public class CateringOrderDetailHttpResponse {

    @ApiModelProperty("物理ID")
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

    @ApiModelProperty("结账信息")
    private final Billing billing = new Billing();

    @ApiModelProperty("订单子项实例列表")
    private final List<Item> items = new LinkedList<>();

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

    @Data
    @ApiModel("结账信息")
    public static class Billing {

        @ApiModelProperty("优惠金额")
        private BigDecimal promotion;

        @ApiModelProperty("支付金额")
        private BigDecimal paid;

        @ApiModelProperty("支付渠道")
        private CateringOrderPaymentChannelEnum paymentChannel;

    }

    @Data
    @ApiModel("订单子项实例定义")
    public static class Item {

        @ApiModelProperty("物理ID")
        private Long id;

        @ApiModelProperty("版本号")
        private Integer version;

        @ApiModelProperty("订单子项状态")
        private CateringOrderItemStatusEnum status;

        @ApiModelProperty("序号")
        private String seqNo;

        @ApiModelProperty("数量信息")
        private final Quantity quantity = new Quantity();

        @ApiModelProperty("下单时刻的商品快照")
        private final ProductSnapshot productSnapshotOnPlace = new ProductSnapshot();

        @ApiModelProperty("下单时刻的商品做法快照")
        private final ProductMethodSnapshot productMethodSnapshotOnPlace = new ProductMethodSnapshot();

        @ApiModelProperty("订单子项加料实例列表")
        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        @ApiModel("数量信息")
        public static class Quantity {

            @ApiModelProperty("当前数量")
            private BigDecimal latest;

            @ApiModelProperty("下单数量")
            private BigDecimal onPlace;

            @ApiModelProperty("出餐数量")
            private BigDecimal onProduce;
        }

        @Data
        @ApiModel("商品快照")
        public static class ProductSnapshot {

            @ApiModelProperty("物理ID")
            private Long id;

            @ApiModelProperty("商品名")
            private String name;

            @ApiModelProperty("单价")
            private BigDecimal unitPrice;

            @ApiModelProperty("计量单位")
            private String unitOfMeasure;

        }

        @Data
        @ApiModel("商品做法快照")
        public static class ProductMethodSnapshot {

            @ApiModelProperty("物理ID")
            private Long id;

            @ApiModelProperty("名称")
            private String name;

            @ApiModelProperty("分组名")
            private String groupName;
        }

        @Data
        @ApiModel("订单详情的Http返回体")
        public static class Accessory {

            @ApiModelProperty("物理ID")
            private Long id;

            @ApiModelProperty("版本号")
            private Integer version;

            @ApiModelProperty("序号")
            private String seqNo;

            @ApiModelProperty("状态")
            private CateringOrderItemAccessoryStatusEnum status;

            @ApiModelProperty("数量信息")
            private final Quantity quantity = new Quantity();

            @ApiModelProperty("下单时刻的商品加料快照")
            private final ProductAccessorySnapshot productAccessorySnapshotOnPlace = new ProductAccessorySnapshot();

            @Data
            @ApiModel("数量快照")
            public static class Quantity {

                @ApiModelProperty("当前数量")
                private BigDecimal latest;

                @ApiModelProperty("下单数量")
                private BigDecimal onPlace;

                @ApiModelProperty("出餐数量")
                private BigDecimal onProduce;
            }

            @Data
            @ApiModel("商品加料快照")
            public static class ProductAccessorySnapshot {

                @ApiModelProperty("物理ID")
                private Long id;

                @ApiModelProperty("名称")
                private String name;

                @ApiModelProperty("备注")
                private String groupName;

                @ApiModelProperty("单价")
                private BigDecimal unitPrice;

                @ApiModelProperty("计量单位")
                private String unitOfMeasure;

            }
        }
    }
}