package com.meituan.catering.management.shop.dao.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.meituan.catering.management.shop.dao.model.ShopDO;
import org.apache.ibatis.annotations.Mapper;

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

}
