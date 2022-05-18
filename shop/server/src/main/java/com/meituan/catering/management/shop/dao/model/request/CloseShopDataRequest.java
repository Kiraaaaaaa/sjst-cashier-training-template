package com.meituan.catering.management.shop.dao.model.request;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 14:41
 * @ClassName: CloseShopDataRequest
 */
@Data
public class CloseShopDataRequest {

    private Long tenantId;
    private String businessNo;
    private Integer version;
    private Long lastModifiedBy;
    private Date lastModifiedAt;
}
