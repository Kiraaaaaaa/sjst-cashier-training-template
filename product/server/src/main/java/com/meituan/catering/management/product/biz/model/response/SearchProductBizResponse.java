package com.meituan.catering.management.product.biz.model.response;

import com.meituan.catering.management.product.biz.model.ProductBO;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 14:52
 * @ClassName: SearchProductBizResponse
 */
@Data
public class SearchProductBizResponse {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer totalCount;

    private final List<ProductBO> ProductBOS= new LinkedList<>();
}
