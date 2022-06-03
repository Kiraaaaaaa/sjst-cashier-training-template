package com.meituan.catering.management.shop.api.http.model.response;

import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.shop.api.http.model.dto.ShopDetailHttpDTO;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * 门店详情的Http返回体
 * @author mac
 */
@ApiModel("门店详情的Http返回体")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ShopDetailHttpResponse extends BaseResponse<ShopDetailHttpDTO> {

}