package com.meituan.catering.management.shop.biz.model.request;

import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 14:42
 * @ClassName: CloseShopBizRequest
 */
@Data
public class CloseShopBizRequest {
    private Long tenantId;

    private Long userId;

    private String businessNo;

    private Integer version;
}
