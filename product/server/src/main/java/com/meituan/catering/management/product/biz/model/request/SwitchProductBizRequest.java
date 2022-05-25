package com.meituan.catering.management.product.biz.model.request;

import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 10:39
 * @ClassName: SwitchProductBizRquest
 */
@Data
public class SwitchProductBizRequest {

    Boolean enabled;
    Integer version;
}
