package com.meituan.catering.management.product.api.http.model.response;

import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.product.api.http.model.dto.ProductDetailHttpDTO;
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
 * 商品详情的Http返回体
 * @author mac
 */
@ApiModel("商品详情的Http返回体")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ProductDetailHttpResponse extends BaseResponse<ProductDetailHttpDTO> {

}