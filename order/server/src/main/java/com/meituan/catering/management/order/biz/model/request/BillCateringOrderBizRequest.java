package com.meituan.catering.management.order.biz.model.request;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/6 13:25
 * @ClassName: BillCateringOrderBizRequest
 */
@Data
public class BillCateringOrderBizRequest {

    private Long tenantId;

    private Long userId;

    private Long orderId;

    private Integer version;

    private BigDecimal promotion;

    private BigDecimal paid;

    private CateringOrderPaymentChannelEnum paymentChannel;
}
