package com.meituan.catering.management.order.biz.model.request;

import lombok.Data;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/3 9:02
 * @ClassName: PrepareCateringOrderBizRequest
 */
@Data
public class PrepareCateringOrderBizRequest {

    private Long tenantId;

    private Long orderId;

    private Long userId;

    private Integer version;

    private Date lastModifiedAt;

    private final List<Item> items = new LinkedList<>();


    @Data
    public static class Item {

        private String seqNo;

        private Integer version;
    }

}
