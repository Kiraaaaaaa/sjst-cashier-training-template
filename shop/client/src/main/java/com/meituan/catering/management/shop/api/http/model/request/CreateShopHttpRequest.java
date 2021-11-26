package com.meituan.catering.management.shop.api.http.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;

/**
 * 创建门店的Http请求体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Valid
@ApiModel("创建门店的Http请求体")
public class CreateShopHttpRequest extends SaveShopHttpRequest {

}