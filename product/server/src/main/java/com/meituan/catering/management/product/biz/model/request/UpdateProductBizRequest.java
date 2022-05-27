package com.meituan.catering.management.product.biz.model.request;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 14:45
 * @ClassName: UpdateProductBizRequest
 */
@Data
public class UpdateProductBizRequest extends SaveProductBizRequest{

    private Integer version;
}
