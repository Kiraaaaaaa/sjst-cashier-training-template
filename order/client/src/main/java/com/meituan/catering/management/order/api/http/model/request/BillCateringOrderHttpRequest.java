package com.meituan.catering.management.order.api.http.model.request;

import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单结账Http请求体
 *
 * @author dulinfeng
 */
@Data
@ApiModel("订单结账Http请求体")
public class BillCateringOrderHttpRequest {

    @NotNull
    @Min(1)
    @Max(10000)
    @ApiModelProperty("序号")
    private Integer version;

    @Min(0)
    @Max(100000)
    @ApiModelProperty("优惠金额")
    private BigDecimal promotion;

    @Min(0)
    @Max(100000)
    @ApiModelProperty("支付金额")
    private BigDecimal paid;

    @NotNull
    @ApiModelProperty("支付渠道")
    private CateringOrderPaymentChannelEnum paymentChannel;

}