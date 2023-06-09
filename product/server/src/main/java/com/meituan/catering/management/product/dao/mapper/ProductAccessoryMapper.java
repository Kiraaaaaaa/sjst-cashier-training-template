package com.meituan.catering.management.product.dao.mapper;

import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 商品加料对应的MyBatis Mapper
 */
@Mapper
public interface ProductAccessoryMapper {

    /**
     * 根据租户号和产品ID列表找到商品加料DO实例
     *
     * @param tenantId   租户号
     * @param productIds 产品ID列表
     * @return 符合条件的所有商品做法DO实例
     */
    List<ProductAccessoryDO> findAllByProductIds(Long tenantId, Set<Long> productIds);

    /**
     * 插入新的商品加料DO实例
     *
     * @param productAccessoryDOS 新的商品加料DO实例的列表
     * @return 插入条数
     */
    int batchInsert(List<ProductAccessoryDO> productAccessoryDOS);

    int insert(ProductAccessoryDO productAccessoryDO);

    /**
     * 根据产品ID删除商品加料
     *
     * @param tenantId  租户ID
     * @param productId 产品ID
     * @return 删除的条数
     */
    int deleteByProductId(Long tenantId, Long productId);


    /**
     * 根据ID删除商品加料
     * @param tenantId
     * @param id
     * @return
     */
    int deleteById(Long tenantId, Long id);
    /**
     * 根据ID更新加料
     * @param productAccessoryDO
     * @return
     */
    int updateById(ProductAccessoryDO productAccessoryDO);


    /**
     * 根据productId查取加料
     * @param tenantId
     * @param productId
     * @return
     */
    List<ProductAccessoryDO> findAllByProductId(Long tenantId,Long productId);

}
