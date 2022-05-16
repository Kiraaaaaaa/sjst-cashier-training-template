package com.meituan.catering.management.order.api.http.model.response;

import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.order.api.http.model.dto.CateringOrderDetailHttpDTO;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemAccessoryStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderItemStatusEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderPaymentChannelEnum;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 订单详情的Http返回体
 * @author mac
 */
@ApiModel("订单详情的Http返回体")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CateringOrderDetailHttpResponse extends BaseResponse<CateringOrderDetailHttpDTO> {
}