package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.response.SearchShopBizResponse;
import com.meituan.catering.management.shop.dao.model.ShopDO;
import io.micrometer.core.instrument.search.Search;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/31 10:54
 * @ClassName: SearchShopBizResponseConverter
 */
public class SearchShopBizResponseConverter {

    public static SearchShopBizResponse toSearchShopBizResponse(Integer pageIndex,Integer pageSize,Integer totalCount,List<ShopDO> shopDOS){

        SearchShopBizResponse response = new SearchShopBizResponse();
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        response.setTotalCount(totalCount);
        response.setTotalPageCount(totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1);

        response.getRecords();
        List<ShopBO> collect = shopDOS.stream().map(ShopBOConverter::toShopBO).collect(Collectors.toList());
        response.getRecords().addAll(collect);

        return response;
    }
}
