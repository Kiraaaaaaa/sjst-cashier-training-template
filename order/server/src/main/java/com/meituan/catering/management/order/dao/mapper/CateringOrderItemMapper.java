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

    /**
     * 根据orderId查找
     * @param tenantId
     * @param orderId
     * @return
     */
    List<CateringOrderItemDO> queryByOrderId(Long tenantId, Long orderId);

    CateringOrderItemDO queryByOrderIdAndSeqNo(Long tenantId, Long orderId, String seqNo);

    /**
     * 插入
     * @param itemDO
     * @return
     */
    Integer insert(CateringOrderItemDO itemDO);

    /**
     * 批量插入
     * @param itemDOS
     * @return
     */
    Integer batchInsert(List<CateringOrderItemDO> itemDOS);

    /**
     * 更新
     * @param cateringOrderItemDO
     * @return
     */
    Integer update(CateringOrderItemDO cateringOrderItemDO);

    Integer batchUpdate(List<CateringOrderItemDO> itemDOS);

}
