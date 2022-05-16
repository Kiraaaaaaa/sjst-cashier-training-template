package com.meituan.catering.management.shop.api.http.model.response;

import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.dto.ShopDetailHttpDTO;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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