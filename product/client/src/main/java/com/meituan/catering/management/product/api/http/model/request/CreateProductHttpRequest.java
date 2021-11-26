package com.meituan.catering.management.product.api.http.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;

/**
 * 创建商品的Http请求体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Valid
@ApiModel("创建商品的Http请求体")
public class CreateProductHttpRequest extends SaveProductHttpRequest {

}