package com.meituan.catering.management.order.biz.service.impl;


import com.google.common.collect.Lists;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.order.api.http.model.enumeration.CateringOrderStatusEnum;
import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.converter.*;
import com.meituan.catering.management.order.biz.model.request.*;
import com.meituan.catering.management.order.biz.service.CateringOrderBizService;
import com.meituan.catering.management.order.dao.mapper.CateringOrderItemAccessoryMapper;
import com.meituan.catering.management.order.dao.mapper.CateringOrderItemMapper;
import com.meituan.catering.management.order.dao.mapper.CateringOrderMapper;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import com.meituan.catering.management.order.remote.ProductRemoteService;
import com.meituan.catering.management.order.remote.ShopRemoteService;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;
import com.meituan.catering.management.order.remote.model.response.ShopDetailRemoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
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
    public CateringOrderBO place(PlaceCateringOrderBizRequest request) {

        ShopDetailRemoteResponse shopDetail = shopRemoteService.findByBusinessNo(request.getTenantId(), request.getUserId(), request.getShopBusinessNo());
        Set<Long> ids = request.getItems().stream().map(PlaceCateringOrderBizRequest.Item::getProductId).filter(Objects::nonNull).collect(Collectors.toSet());
        List<ProductDetailRemoteResponse> productDetailList = productRemoteService.findByIds(request.getTenantId(), request.getUserId(), ids);
        return transactionTemplate.execute(status -> {
            CateringOrderDO cateringOrderDO = PlaceCateringOrderRequestConverter.toCateringOrderDO(request.getTenantId(), request.getUserId(), request, shopDetail);
            Integer insert = orderMapper.insert(cateringOrderDO);
            if (insert == 0) {
                throw new BizException(ErrorCode.INSET_ERROR);
            }
            List<CateringOrderItemDO> itemDOS = PlaceCateringOrderRequestConverter.toCateringOrderItemDO(cateringOrderDO, request.getItems(), productDetailList);
            if (!CollectionUtils.isEmpty(itemDOS)) {
                Integer itemInsert = itemMapper.batchInsert(itemDOS);
                if (itemInsert != itemDOS.size()) {
                    throw new BizException(ErrorCode.INSET_ERROR);
                }
            }
            List<CateringOrderItemAccessoryDO> accessoryDOS = PlaceCateringOrderRequestConverter.toCateringOrderItemAccessoryDO(itemDOS, request, productDetailList);
            if (!CollectionUtils.isEmpty(accessoryDOS)) {
                Integer accessoryInsert = accessoryMapper.batchInsert(accessoryDOS);
                if (accessoryInsert != accessoryDOS.size()) {
                    throw new BizException(ErrorCode.INSET_ERROR);
                }
            }
            return getCateringOrderBO(request.getTenantId(), cateringOrderDO.getId());
        });
    }

    @Override
    public CateringOrderBO prepare(PrepareCateringOrderBizRequest request) {
        return transactionTemplate.execute(status -> {
            CateringOrderDO cateringOrderDO = PrepareCateringOrderRequestConverter.toCateringOrderDO(request);
            Integer update = orderMapper.update(cateringOrderDO);
            if (update == 0) {
                throw new BizException(ErrorCode.PREPARE_ERROR);
            }
            List<CateringOrderItemDO> itemDOS = PrepareCateringOrderRequestConverter.toCateringOrderItemDO(request);
            itemDOS.forEach(itemDO -> itemMapper.update(itemDO));
            return getCateringOrderBO(request.getTenantId(), request.getOrderId());
        });
    }

    @Override
    public CateringOrderBO produce(ProduceCateringOrderBizRequest request) {
        return transactionTemplate.execute(status -> {
            List<CateringOrderItemDO> itemDOS = itemMapper.queryByOrderId(request.getTenantId(), request.getOrderId());
            List<CateringOrderItemDO> itemDOList = ProduceCateringOrderRequestConverter.toCateringOrderItemDO(itemDOS, request);
            itemDOList.forEach(itemDO -> {
                Integer update = itemMapper.update(itemDO);
                if (update <= 0){
                    throw new BizException(ErrorCode.PRODUCE_ERROR);
                }
            });
            request.getItems().forEach(item -> {
                itemDOS.forEach(itemDO -> {
                    if (Objects.equals(item.getSeqNo(), itemDO.getSeqNo())) {
                        List<CateringOrderItemAccessoryDO> dos = accessoryMapper.queryByOrderItemId(request.getTenantId(), itemDO.getId());
                        List<CateringOrderItemAccessoryDO> list = ProduceCateringOrderRequestConverter.toCateringOrderItemAccessoryDO(dos, request);
                        list.forEach(itemAccessoryDO -> {
                            Integer update = accessoryMapper.update(itemAccessoryDO);
                            if (update <= 0) {
                                throw new BizException(ErrorCode.PRODUCE_ERROR);
                            }
                        });
                    }
                });
            });
            List<CateringOrderItemDO> itemList = itemMapper.queryByOrderId(request.getTenantId(), request.getOrderId());
            List<Long> ids = itemList.stream().map(CateringOrderItemDO::getId).filter(Objects::nonNull).collect(Collectors.toList());
            List<CateringOrderItemAccessoryDO> accessoryList = accessoryMapper.batchQueryByOrderItemId(request.getTenantId(), ids);
            CateringOrderDO cateringOrderDO = ProduceCateringOrderRequestConverter.toCateringOrderDO(accessoryList, itemList, request);
            Integer update = orderMapper.update(cateringOrderDO);
            if (update <= 0) {
                throw new BizException(ErrorCode.PRODUCE_ERROR);
            }
            return getCateringOrderBO(request.getTenantId(), request.getOrderId());
        });
    }

    @Override
    public CateringOrderBO bill(BillCateringOrderBizRequest request) {
        CateringOrderDO orderDO = orderMapper.queryById(request.getTenantId(), request.getOrderId());
        if (orderDO.getStatus() != CateringOrderStatusEnum.PREPARED) {
            throw new BizException(ErrorCode.BILL_ERROR);
        }
        CateringOrderDO cateringOrderDO = BillCateringOrderRequestConverter.toCateringOrderDO(request);
        Integer update = orderMapper.update(cateringOrderDO);
        if (update <= 0) {
            throw new BizException(ErrorCode.BILL_ERROR);
        }
        return getCateringOrderBO(request.getTenantId(), request.getOrderId());
    }

    @Override
    public CateringOrderBO adjust(AdjustCateringOrderBizRequest request) {

        List<CateringOrderItemDO> itemDOList = itemMapper.queryByOrderId(request.getTenantId(), request.getOrderId());
        Set<Long> ids = request.getItems().stream().map(AdjustCateringOrderBizRequest.Item::getProductId).filter(Objects::nonNull).collect(Collectors.toSet());
        ids.addAll(itemDOList.stream().map(CateringOrderItemDO::getProductId).collect(Collectors.toSet()));
        List<ProductDetailRemoteResponse> productDetailList = productRemoteService.findByIds(request.getTenantId(), request.getUserId(), ids);

        return transactionTemplate.execute(status -> {
            ArrayList<CateringOrderItemDO> itemList = Lists.newArrayList();
            ArrayList<CateringOrderItemAccessoryDO> accessoryList = Lists.newArrayList();
            request.getItems().forEach(item -> {
                //更新
                if (Objects.isNull(item.getProductId())) {
                    itemDOList.forEach(itemDO -> {
                        if (Objects.equals(itemDO.getSeqNo(), item.getSeqNo())) {
                            CateringOrderItemDO orderItemDO = AdjustCateringOrderRequestConverter.toCateringOrderItemDO(request.getTenantId(), request.getOrderId(), itemDO, null, item);
                            Integer adjust = itemMapper.update(orderItemDO);
                            if (adjust <= 0) {
                                throw new BizException(ErrorCode.ADJUST_ERROR);
                            }
                        }
                    });
                }
                //插入
                if (Objects.nonNull(item.getProductId())) {
                    productDetailList.forEach(productDetail -> {
                        if (Objects.equals(productDetail.getId(), item.getProductId())) {
                            CateringOrderItemDO itemDO = AdjustCateringOrderRequestConverter.toCateringOrderItemDO(request.getTenantId(), request.getOrderId(), null, productDetailList.get(0), item);
                            itemList.add(itemDO);
                        }
                    });
                }
            });
            if (!CollectionUtils.isEmpty(itemList)) {
                itemMapper.batchInsert(itemList);
            }
            List<CateringOrderItemDO> itemDOS = itemMapper.queryByOrderId(request.getTenantId(), request.getOrderId());
            request.getItems().forEach(item -> {
                itemDOS.forEach(itemDO -> {
                    if (Objects.equals(itemDO.getSeqNo(), item.getSeqNo())) {
                        item.getAccessories().forEach(accessory -> {
                            //更新
                            if (Objects.isNull(accessory.getProductAccessoryId())) {
                                List<CateringOrderItemAccessoryDO> accessoryDOS = accessoryMapper.queryByOrderItemId(request.getTenantId(), itemDO.getId());
                                accessoryDOS.forEach(accessoryDO -> {
                                    if (Objects.equals(accessoryDO.getSeqNo(), accessory.getSeqNo())) {
                                        CateringOrderItemAccessoryDO itemAccessoryDO = AdjustCateringOrderRequestConverter.toCateringOrderItemAccessoryDO(request.getTenantId(), itemDO.getId(), accessoryDO, null, accessory);
                                        Integer adjust = accessoryMapper.update(itemAccessoryDO);
                                        if (adjust <= 0) {
                                            throw new BizException(ErrorCode.ADJUST_ERROR);
                                        }
                                    }
                                });
                            }
                            //插入
                            if (Objects.nonNull(accessory.getProductAccessoryId())) {
                                productDetailList.forEach(productDetail -> {
                                    if (Objects.equals(productDetail.getId(), itemDO.getProductId())) {
                                        productDetail.getAccessoryGroups().forEach(accessoryGroup -> {
                                            CateringOrderItemAccessoryDO itemAccessoryDO = AdjustCateringOrderRequestConverter.toCateringOrderItemAccessoryDO(request.getTenantId(), itemDO.getId(), null, accessoryGroup, accessory);
                                            accessoryList.add(itemAccessoryDO);
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            });
            if (!CollectionUtils.isEmpty(accessoryList)) {
                accessoryMapper.batchInsert(accessoryList);
            }
            List<CateringOrderItemDO> dos = itemMapper.queryByOrderId(request.getTenantId(), request.getOrderId());
            List<Long> itemIds = dos.stream().map(CateringOrderItemDO::getId).collect(Collectors.toList());
            List<CateringOrderItemAccessoryDO> accessoryDOS = accessoryMapper.batchQueryByOrderItemId(request.getTenantId(), itemIds);
            CateringOrderDO cateringOrderDO = AdjustCateringOrderRequestConverter.toCateringOrderDO(accessoryDOS, dos, request);
            Integer update = orderMapper.update(cateringOrderDO);
            if (update <= 0) {
                throw new BizException(ErrorCode.ADJUST_ERROR);
            }
            return getCateringOrderBO(request.getTenantId(), request.getOrderId());
        });
    }


    private CateringOrderBO getCateringOrderBO(Long tenantId, Long orderId) {
        CateringOrderDO queryOrderDO = orderMapper.queryById(tenantId, orderId);
        List<CateringOrderItemDO> itemDOS = itemMapper.queryByOrderId(tenantId, orderId);
        List<Long> orderItemIds = itemDOS.stream().map(CateringOrderItemDO::getId).collect(Collectors.toList());
        List<CateringOrderItemAccessoryDO> accessoryDOS = null;
        if (!CollectionUtils.isEmpty(orderItemIds)) {
            accessoryDOS = accessoryMapper.batchQueryByOrderItemId(tenantId, orderItemIds);
        }
        return CateringOrderBOConverter.toCateringOrderBO(queryOrderDO, itemDOS, accessoryDOS);
    }


}
