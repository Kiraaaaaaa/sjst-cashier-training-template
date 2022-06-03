package com.meituan.catering.management.shop.biz.model.response;

import com.meituan.catering.management.shop.api.http.model.dto.ShopPageHttpDTO;
import com.meituan.catering.management.shop.biz.model.ShopBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/31 10:47
 * @ClassName: SearchShopBizResponse
 */
@Data
public class SearchShopBizResponse {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer totalCount;

    private Integer totalPageCount;

    private final List<ShopBO> records = new LinkedList<>();
}
