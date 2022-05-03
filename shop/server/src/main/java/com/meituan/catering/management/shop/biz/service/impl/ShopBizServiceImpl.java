package com.meituan.catering.management.shop.biz.service.impl;

import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.converter.ShopBOConverter;
import com.meituan.catering.management.shop.biz.service.ShopBizService;
import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

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
}
