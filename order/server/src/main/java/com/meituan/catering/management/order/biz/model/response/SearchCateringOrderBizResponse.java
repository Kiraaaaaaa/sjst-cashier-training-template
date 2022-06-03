package com.meituan.catering.management.order.biz.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.order.api.http.model.dto.CateringOrderPageHttpDTO;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/30 16:53
 * @ClassName: SearchCateringOrderBizRsponse
 */
@Data
public class SearchCateringOrderBizResponse {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer totalCount;

    private Integer totalPageCount;

    private final List<Record> records = new LinkedList<>();

    @Data
    public static class Record {

        private Long id;

        private Long tenantId;

        private Integer version;

        private final AuditingHttpModel auditing = new AuditingHttpModel();

        private CateringOrderStatusEnum status;

        private String tableNo;

        private String customerCount;

        private BigDecimal totalPrice;

        private String comment;

        private final ShopSnapshot shopSnapshotOnPlace = new ShopSnapshot();

        @Data
        public static class ShopSnapshot {

            private Long id;

            private String businessNo;

            private String name;
        }
    }
}
