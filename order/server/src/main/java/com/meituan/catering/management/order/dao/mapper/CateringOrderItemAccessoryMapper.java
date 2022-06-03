package com.meituan.catering.management.order.dao.mapper;

import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单子项加料对应的MyBatis Mapper
 */
@Mapper
public interface CateringOrderItemAccessoryMapper {

    List<CateringOrderItemAccessoryDO> batchQueryByOrderItemId(Long tenantId, List<Long> orderItemIds);

    Integer batchInsert(List<CateringOrderItemAccessoryDO> accessoryDOS);

}
