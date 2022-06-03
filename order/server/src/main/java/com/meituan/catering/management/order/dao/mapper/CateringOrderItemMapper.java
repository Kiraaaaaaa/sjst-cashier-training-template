package com.meituan.catering.management.order.dao.mapper;

import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单子项对应的MyBatis Mapper
 */
@Mapper
public interface CateringOrderItemMapper {

    List<CateringOrderItemDO> queryByOrderId(Long tenantId, Long orderId);

    Integer insert(CateringOrderItemDO itemDO);

}
