package com.meituan.catering.management.shop.biz.service.impl;

import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.converter.ShopBOConverter;
import com.meituan.catering.management.shop.biz.model.request.SaveShopBizRequest;
import com.meituan.catering.management.shop.biz.service.ShopBizService;
import com.meituan.catering.management.shop.dao.converter.ShopDOConverter;
import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import com.meituan.catering.management.shop.dao.model.ShopDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * {@link ShopBizService}的核心实现
 */
@Service
public class ShopBizServiceImpl implements ShopBizService {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private ShopMapper shopMapper;

    @Override
    public ShopBO findByBusinessNo(Long tenantId, Long userId, String businessNo) {
        return ShopBOConverter.toShopBO(shopMapper.findByBusinessNo(tenantId,userId,businessNo));
    }

    @Override
    public ShopBO create(Long tenantId, Long userId, SaveShopBizRequest saveShopBizRequest) {
        ShopDO shopDO= ShopDOConverter.toShopDO(tenantId,userId,saveShopBizRequest);
        int id=shopMapper.insert(shopDO);
        Assert.isTrue(id>0,"插入数据库错误");
        return ShopBOConverter.toShopBO(shopMapper.selectById(shopDO.getId()));
    }
}
