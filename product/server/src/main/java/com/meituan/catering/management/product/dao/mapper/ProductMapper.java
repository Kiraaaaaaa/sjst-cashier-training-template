package com.meituan.catering.management.product.dao.mapper;

import com.meituan.catering.management.product.dao.model.ProductDO;
import com.meituan.catering.management.product.dao.model.request.SearchProductDataRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 商品对应的MyBatis Mapper
 */
@Mapper
public interface ProductMapper {

    /**
     * 为分页查询符合当前分页码的商品列表
     *
     * @param dataRequest 查询条件
     * @return 当前分页码的商品列表
     */
    List<ProductDO> searchForPage(SearchProductDataRequest dataRequest);

    /**
     * 根据租户号找到商品DO实例
     *
     * @param tenantId 租户号
     * @return 该租户下的所有商品DO实例
     */
    List<ProductDO> findAll(Long tenantId);

    /**
     * 根据物理ID列表找到商品DO实例列表
     *
     * @param tenantId 租户号
     * @param ids      物理ID列表
     * @return 商品DO实例
     */
    List<ProductDO> findByIds(Long tenantId, Set<Long> ids);

    /**
     * 根据物理ID找到商品DO实例
     *
     * @param tenantId 租户号
     * @param id       物理ID
     * @return 商品DO实例
     */
    ProductDO findById(Long tenantId, Long id);

    /**
     * 为分页查询计算符合查询条件的总条目数
     *
     * @param dataRequest 查询条件
     * @return 总条目数
     */
    int countForPage(SearchProductDataRequest dataRequest);

    /**
     * 插入新的商品DO实例
     *
     * @param productDO 新的商品DO实例
     * @return 插入条数
     */
    int insert(ProductDO productDO);

    /**
     * 根据物理ID更新已有的商品DO实例
     *
     * @param productDO 需要更新的商品DO实例
     * @return 更新条数
     */
    int updateSelective(ProductDO productDO);

    /**
     * 根据物理ID修改商品上架状态
     *
     * @param tenantId 租户ID
     * @param id       物理ID
     * @param enabled  上架-1，下架-2
     * @param version  乐观锁版本号
     * @return 更新条数
     */
    int changeEnabledById(Long tenantId, Long id, Integer enabled, Integer version);
}
