package com.meituan.catering.management.order.biz.service.impl;

import com.google.common.collect.Lists;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.converter.CateringOrderBOConverter;
import com.meituan.catering.management.order.biz.model.converter.CateringOrderDOConverter;
import com.meituan.catering.management.order.biz.model.request.PlaceCateringOrderBizRequest;
import com.meituan.catering.management.order.biz.service.CateringOrderBizService;
import com.meituan.catering.management.order.dao.mapper.CateringOrderItemAccessoryMapper;
import com.meituan.catering.management.order.dao.mapper.CateringOrderItemMapper;
import com.meituan.catering.management.order.dao.mapper.CateringOrderMapper;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import com.meituan.catering.management.order.remote.ProductRemoteService;
import com.meituan.catering.management.order.remote.ShopRemoteService;
import com.meituan.catering.management.order.remote.model.request.ShopPageRemoteRequest;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;
import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CateringOrderBizServiceImpl implements CateringOrderBizService {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private CateringOrderMapper orderMapper;

    @Resource
    private CateringOrderItemMapper itemMapper;

    @Resource
    private CateringOrderItemAccessoryMapper accessoryMapper;

    @Resource
    private ShopRemoteService shopRemoteService;

    @Resource
    private ProductRemoteService productRemoteService;

    @Override
    public CateringOrderBO insert(Long tenantId, Long userId, PlaceCateringOrderBizRequest request) {
        return transactionTemplate.execute(status -> {
            ShopDetailRemoteResponse shopDetail = shopRemoteService.findByBusinessNo(tenantId, userId, request.getShopBusinessNo());
            Set<Long> ids = request.getItems().stream().map(PlaceCateringOrderBizRequest.Item::getProductId).collect(Collectors.toSet());
            List<ProductDetailRemoteResponse> productDetailList = productRemoteService.findByIds(tenantId, userId, ids);

            CateringOrderDO cateringOrderDO = CateringOrderDOConverter.toCateringOrderDO(tenantId, userId, request, shopDetail);
            Integer insert = orderMapper.insert(cateringOrderDO);
            if (insert == 0) {
                throw new BizException(ErrorCode.INSET_ERROR);
            }
            for (PlaceCateringOrderBizRequest.Item item : request.getItems()) {
                CateringOrderItemDO cateringOrderItemDO = CateringOrderDOConverter.toCateringOrderItemDO(cateringOrderDO, item, productDetailList);
                Integer insertItem = itemMapper.insert(cateringOrderItemDO);
                if (insertItem == 0) {
                    throw new BizException(ErrorCode.INSET_ERROR);
                }
                List<CateringOrderItemAccessoryDO> cateringOrderItemAccessoryDOS = CateringOrderDOConverter.toCateringOrderItemAccessoryDO(cateringOrderItemDO, item, productDetailList);
                if (CollectionUtils.isNotEmpty(cateringOrderItemAccessoryDOS)) {
                    Integer batchInsert = accessoryMapper.batchInsert(cateringOrderItemAccessoryDOS);
                    if (batchInsert == 0) {
                        throw new BizException(ErrorCode.INSET_ERROR);
                    }
                }
            }
            CateringOrderDO queryOrderDO = orderMapper.queryById(tenantId, cateringOrderDO.getId());
            List<CateringOrderItemDO> itemDOS = itemMapper.queryByOrderId(tenantId, cateringOrderDO.getId());
            List<Long> orderItemIds = itemDOS.stream().map(CateringOrderItemDO::getId).collect(Collectors.toList());
            List<CateringOrderItemAccessoryDO> accessoryDOS = accessoryMapper.batchQueryByOrderItemId(tenantId, orderItemIds);
            return CateringOrderBOConverter.toCateringOrderBO(queryOrderDO, itemDOS, accessoryDOS);
        });

    }
}
