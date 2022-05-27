package com.meituan.catering.management.product.biz.model.converter;

import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.biz.model.response.SearchProductBizResponse;
import com.meituan.catering.management.product.dao.model.ProductDO;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 17:01
 * @ClassName: SearchProductBizResponseConverter
 */
public class SearchProductBizResponseConverter {

    public static SearchProductBizResponse toSearchProductBizResponse(Integer pageIndex, Integer pageSize, Integer totalCount, List<ProductBO> productBOS){

        SearchProductBizResponse searchProductBizResponse = new SearchProductBizResponse();
        searchProductBizResponse.setTotalCount(totalCount);
        searchProductBizResponse.setPageSize(pageSize);
        searchProductBizResponse.setPageIndex(pageIndex);

        if (productBOS ==null|| productBOS.size()==0 ){
            return searchProductBizResponse;
        }
        for (ProductBO productBO : productBOS) {
            searchProductBizResponse.getProductBOS().add(productBO);
        }

        return searchProductBizResponse;
    }
}
