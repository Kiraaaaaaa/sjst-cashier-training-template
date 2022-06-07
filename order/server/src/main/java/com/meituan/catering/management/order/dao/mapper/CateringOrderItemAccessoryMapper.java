package com.meituan.catering.management.order.dao.mapper;


import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * 订单子项加料对应的MyBatis Mapper
 */
@Mapper
public interface CateringOrderItemAccessoryMapper {

    /**
     * 根据orderId集合查找
     * @param tenantId
     * @param orderItemIds
     * @return
     */
    List<CateringOrderItemAccessoryDO> batchQueryByOrderItemId(Long tenantId, List<Long> orderItemIds);

    /**
     * 根据单个orderId查找
     * @param tenantId
     * @param orderItemId
     * @return
     */
    List<CateringOrderItemAccessoryDO> queryByOrderItemId(Long tenantId,Long orderItemId);

    /**
     * 批量插入
     * @param accessoryDOS
     * @return
     */
    Integer batchInsert(List<CateringOrderItemAccessoryDO> accessoryDOS);

    /**
     * 插入
     * @param accessoryDO
     * @return
     */
    Integer insert(CateringOrderItemAccessoryDO accessoryDO);

    /**
     * 更新
     * @param accessoryDO
     * @return
     */
    Integer update(CateringOrderItemAccessoryDO accessoryDO);

}
