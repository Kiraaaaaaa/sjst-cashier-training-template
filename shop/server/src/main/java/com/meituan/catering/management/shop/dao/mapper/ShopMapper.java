package com.meituan.catering.management.shop.dao.mapper;

import com.meituan.catering.management.shop.dao.model.ShopDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 门店对应的MyBatis Mapper
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
}
