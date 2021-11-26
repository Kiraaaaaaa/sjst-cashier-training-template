package com.meituan.catering.management.order.biz.service.impl;

import com.meituan.catering.management.order.biz.service.CateringOrderBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
public class CateringOrderBizServiceImpl implements CateringOrderBizService {

    @Resource
    private TransactionTemplate transactionTemplate;

}
