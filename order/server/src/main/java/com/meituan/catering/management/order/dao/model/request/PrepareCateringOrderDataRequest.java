package com.meituan.catering.management.order.dao.model.request;

import com.meituan.catering.management.order.biz.model.request.PrepareCateringOrderBizRequest;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/5 11:13
 * @ClassName: PrepareCateringOrderDataRequest
 */
public class PrepareCateringOrderDataRequest {

    private Long tenantId;

    private Long orderId;

    private Integer version;

    private final List<Item> items = new LinkedList<>();


    @Data
    public static class Item {

        private String seqNo;

        private Integer version;
    }
}
