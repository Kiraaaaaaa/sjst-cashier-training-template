package com.meituan.catering.management.shop.biz.model.request;

import com.meituan.catering.management.shop.api.http.model.request.SaveShopHttpRequest;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/12 17:14
 * @ClassName: UpdateShopBizRequest
 */
@Data
public class UpdateShopBizRequest extends SaveShopBizRequest {

    /**
     * 目标门店的版本号
     */
    private Integer version;

}
