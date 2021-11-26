package com.meituan.catering.management.product.biz.service.impl;

import com.meituan.catering.management.product.biz.service.ProductBizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * {@link ProductBizService}的核心实现
 */
@Service
public class ProductBizServiceImpl implements ProductBizService {

    @Resource
    private TransactionTemplate transactionTemplate;

}
