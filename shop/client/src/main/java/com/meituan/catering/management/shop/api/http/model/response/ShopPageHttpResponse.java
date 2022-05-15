package com.meituan.catering.management.shop.api.http.model.response;

import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.dto.ShopPageHttpDTO;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 门店分页信息Http返回体
 */
@ApiModel("门店分页信息Http返回体")
public class ShopPageHttpResponse extends BaseResponse<ShopPageHttpDTO> {
    public ShopPageHttpResponse(Status status, ShopPageHttpDTO data) {
        super(status, data);
    }
}