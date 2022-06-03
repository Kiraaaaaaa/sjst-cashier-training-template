package com.meituan.catering.management.order.biz.model;

import com.meituan.catering.management.common.model.biz.BaseBO;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 订单BO定义
 */
@Data
public class CateringOrderBO extends BaseBO {

    private CateringOrderStatusEnum status;

    private String tableNo;

    private String customerCount;

    private BigDecimal totalPrice;

    private String comment;

    private final ShopSnapshot shopSnapshotOnPlace = new ShopSnapshot();

    private final Billing billing = new Billing();

    private final List<CateringOrderItem> item = new LinkedList<>();

    @Data
    public static class ShopSnapshot {

        private Long id;

        private String businessNo;

        private String name;
    }

    @Data
    public static class Billing {

        private BigDecimal billingPromotion;

        private BigDecimal billingPaid;

        private CateringOrderPaymentChannelEnum billingPaymentChannel;
    }

    @Data
    public static class CateringOrderItem {

        private Long id;

        private Integer version;

        private String seqNo;

        private CateringOrderItemStatusEnum status;

        private final Quantity quantity = new Quantity();

        private final ProductSnapShot productSnapShot = new ProductSnapShot();

        private final ProductMethodSnapshot productMethodSnapshot = new ProductMethodSnapshot();

        private final List<CateringOrderItemAccessory> accessories = new LinkedList<>();

        @Data
        public static class Quantity {

            private BigDecimal onPlace;

            private BigDecimal onProduce;

            private BigDecimal latest;
        }

        @Data
        public static class ProductSnapShot {
            private Long id;

            private String name;

            private BigDecimal unitPrice;

            private String unitOfMeasure;
        }

        @Data
        public static class ProductMethodSnapshot {
            private Long id;

            private String name;

            private String groupName;
        }

        @Data
        public static class CateringOrderItemAccessory {

            private Long id;

            private Integer version;

            private String seqNo;

            private CateringOrderItemAccessoryStatusEnum status;

            private final Quantity quantity = new Quantity();

            private final ProductAccessorySnapshot productAccessorySnapshot = new ProductAccessorySnapshot();

            @Data
            public static class Quantity {

                private BigDecimal latest;

                private BigDecimal onPlace;

                private BigDecimal onProduce;
            }

            @Data
            public static class ProductAccessorySnapshot {

                private Long id;

                private String name;

                private String groupName;

                private BigDecimal unitPrice;

                private String unitOfMeasure;

            }
        }
    }
}