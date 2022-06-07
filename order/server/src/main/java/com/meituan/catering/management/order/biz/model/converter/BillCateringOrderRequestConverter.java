package com.meituan.catering.management.order.biz.model.converter;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.api.http.model.request.BillCateringOrderHttpRequest;
import com.meituan.catering.management.order.biz.model.request.BillCateringOrderBizRequest;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;

import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/6 13:56
 * @ClassName: BillCateringOrderRequestConverter
 */
public class BillCateringOrderRequestConverter {

    public static BillCateringOrderBizRequest toBillCateringOrderBizRequest(Long tenantId, Long userId, Long orderId, BillCateringOrderHttpRequest request){
        BillCateringOrderBizRequest bizRequest = new BillCateringOrderBizRequest();
        bizRequest.setTenantId(tenantId);
        bizRequest.setOrderId(orderId);
        bizRequest.setUserId(userId);
        bizRequest.setVersion(request.getVersion());
        bizRequest.setPromotion(request.getPromotion());
        bizRequest.setPaid(request.getPaid());
        bizRequest.setPaymentChannel(request.getPaymentChannel());

        return bizRequest;
    }

    public static CateringOrderDO toCateringOrderDO(BillCateringOrderBizRequest request){

        CateringOrderDO cateringOrderDO = new CateringOrderDO();
        cateringOrderDO.setTenantId(request.getTenantId());
        cateringOrderDO.setId(request.getOrderId());
        cateringOrderDO.setVersion(request.getVersion());
        cateringOrderDO.setLastModifiedBy(request.getUserId());
        cateringOrderDO.setLastModifiedAt(new Date());
        cateringOrderDO.setBillingPromotion(request.getPromotion());
        cateringOrderDO.setBillingPaid(request.getPaid());
        cateringOrderDO.setStatus(CateringOrderStatusEnum.BILLED);
        cateringOrderDO.setBillingPaymentChannel(request.getPaymentChannel());

        return cateringOrderDO;
    }
}
