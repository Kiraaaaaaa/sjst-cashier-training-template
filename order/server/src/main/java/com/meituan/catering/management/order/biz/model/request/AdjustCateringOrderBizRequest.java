package com.meituan.catering.management.order.biz.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/6 15:27
 * @ClassName: AdjustCateringOrderBizRequest
 */
@Data
public class AdjustCateringOrderBizRequest {

    private Long tenantId;

    private Long orderId;

    private Integer version;

    private Long userId;

    private Date lastModifiedAt;

    private final List<Item> items = new LinkedList<>();

    @Data
    public static class Item {

        private String seqNo;

        private Integer version;

        private BigDecimal quantityOnAdjustment;

        private Long productId;

        private Long productMethodId;

        private final List<Accessory> accessories = new LinkedList<>();

        @Data
        public static class Accessory {

            private String seqNo;

            private Integer version;

            private BigDecimal quantityOnAdjustment;

            private Long productAccessoryId;

        }
    }
}
