package com.meituan.catering.management.shop.dao.mapper;
import com.meituan.catering.management.shop.dao.model.request.CloseShopDataRequest;
import com.meituan.catering.management.shop.dao.model.request.OpenShopDataRequest;
import com.meituan.catering.management.shop.dao.model.request.SearchShopDataRequest;
import org.apache.ibatis.annotations.Param;

import com.meituan.catering.management.shop.dao.model.ShopDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 门店对应的MyBatis Mapper
 * @author mac
 */
@Mapper
public interface ShopMapper {

    /**
     * 根据商户号查询门店
     * @param tenantId
     * @param userId
     * @param businessNo
     * @return
     */
    ShopDO findByBusinessNo(Long tenantId, Long userId, String businessNo);

    /**
     * 插入门店信息
     * @param shopDO
     * @return
     */
    int insert(ShopDO shopDO);

    /**
     * 根据ID获取门店信息
     * @param id
     * @return
     */
    ShopDO selectById(@Param("id") Long id);

    /**
     * 跟新门店信息
     * @param shopDO
     * @return
     */
    int update(ShopDO shopDO);

    /**
     * 分页查找
     * @param request
     * @return
     */
    List<ShopDO> selectByConditional(SearchShopDataRequest request);

    /**
     * 打开一个门店
     * @param request
     * @return
     */
    int open(OpenShopDataRequest request);

    /**
     * 关闭一个门店
     * @param request
     * @return
     */
    int close(CloseShopDataRequest request);
}
