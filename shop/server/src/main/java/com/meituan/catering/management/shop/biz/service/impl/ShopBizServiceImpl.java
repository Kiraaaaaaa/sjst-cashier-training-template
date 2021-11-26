package com.meituan.catering.management.shop.biz.service.impl;

import com.meituan.catering.management.shop.biz.service.ShopBizService;
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

}
