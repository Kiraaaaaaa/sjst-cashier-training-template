package com.meituan.catering.management.order.dao.mapper;

import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.request.SearchCateringOrderDataRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单对应的MyBatis Mapper
 */
@Mapper
public interface CateringOrderMapper {

    /**
     * 根据id查询
     * @param tenantId
     * @param orderId
     * @return
     */
    CateringOrderDO queryById(Long tenantId,Long orderId);

    /**
     * 分页查询
     * @param request
     * @return
     */
    List<CateringOrderDO> searchForPage(SearchCateringOrderDataRequest request);

    /**
     * 分页查询计算总条数
     * @param request
     * @return
     */
    Integer countForPage(SearchCateringOrderDataRequest request);

    Integer insert(CateringOrderDO cateringOrderDO);
}
