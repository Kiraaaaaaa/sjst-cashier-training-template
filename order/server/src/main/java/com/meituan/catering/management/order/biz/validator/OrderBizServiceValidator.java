package com.meituan.catering.management.order.biz.validator;

import com.google.common.collect.Sets;
import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.order.api.http.model.request.*;
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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 15:01
 * @ClassName: ProductBizServiceValidator
 */
@Component
public class OrderBizServiceValidator {
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

    public static void baseValid(Long tenantId, Long userId) throws BizException {
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    public void orderVersionValid(Long tenantId, Long userId, Long id, Integer version) {
        baseValid(tenantId, userId);
        CateringOrderDO cateringOrderDO = orderMapper.queryById(tenantId, id);
        if (Objects.isNull(cateringOrderDO)) {
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        Integer versionDO = cateringOrderDO.getVersion();
        if (!versionDO.equals(version)) {
            throw new BizException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void sqlValid(Long tenantId, Long userId, SearchCateringOrderHttpRequest request) {
        Pattern sqlPattern = Pattern.compile(
                "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete"
                        + "|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)");
        baseValid(tenantId, userId);
        List<SearchCateringOrderHttpRequest.SortField> sortFields = request.getSortFields();
        if (sortFields != null && sortFields.size() != 0) {
            for (SearchCateringOrderHttpRequest.SortField sortField : sortFields) {
                Matcher matcher = sqlPattern.matcher(sortField.toString());
                if (matcher.find()) {
                    throw new BizException(ErrorCode.ILLEGAL_CODE_ERROR);
                }
            }
        }
    }

    public void createValid(Long tenantId, Long userId, PlaceCateringOrderHttpRequest request) {
        baseValid(tenantId, userId);
        String shopBusinessNo = request.getShopBusinessNo();
        ShopDetailRemoteResponse response = shopRemoteService.findByBusinessNo(tenantId, userId, shopBusinessNo);
        Set<Long> productIds = request.getItems().stream().map(PlaceCateringOrderHttpRequest.Item::getProductId).collect(Collectors.toSet());
        List<ProductDetailRemoteResponse> productDetailList = productRemoteService.findByIds(tenantId, userId, productIds);
        if (!response.getEnabled()) {
            throw new BizException(ErrorCode.INSET_ERROR);
        }
        for (ProductDetailRemoteResponse productDetailRemoteResponse : productDetailList) {
            if (!productDetailRemoteResponse.getEnabled()) {
                throw new BizException(ErrorCode.INSET_ERROR);
            }
        }
        AtomicInteger itemSize = new AtomicInteger();
        HashSet<String> itemSet = Sets.newHashSet();
        request.getItems().forEach(item -> {
            itemSet.add(item.getSeqNo());
            itemSize.getAndIncrement();
            AtomicInteger accessorySize = new AtomicInteger();
            HashSet<String> accessorySet = Sets.newHashSet();
            item.getAccessories().forEach(accessory -> {
                accessorySet.add(accessory.getSeqNo());
                accessorySize.getAndIncrement();
            });
            if (!Objects.equals(accessorySet.size(), accessorySize.get())) {
                throw new BizException(ErrorCode.INSET_ERROR);
            }
        });
        if (!Objects.equals(itemSet.size(), itemSize.get())) {
            throw new BizException(ErrorCode.INSET_ERROR);
        }
    }

    public void prepareValid(Long tenantId, Long userId, Long orderId, PrepareCateringOrderHttpRequest request) {
        baseValid(tenantId, userId);
        CateringOrderDO cateringOrderDO = orderMapper.queryById(tenantId, orderId);
        if (Objects.isNull(cateringOrderDO)) {
            throw new BizException(ErrorCode.PREPARE_ERROR);
        }
        if (!request.getVersion().equals(cateringOrderDO.getVersion())) {
            throw new BizException(ErrorCode.PREPARE_ERROR);
        }

        List<CateringOrderItemDO> cateringOrderItemDOS = itemMapper.queryByOrderId(tenantId, orderId);
        if (CollectionUtils.isEmpty(cateringOrderItemDOS)) {
            throw new BizException(ErrorCode.PREPARE_ERROR);
        }

        for (CateringOrderItemDO cateringOrderItemDO : cateringOrderItemDOS) {
            for (PrepareCateringOrderHttpRequest.Item item : request.getItems()) {
                if (item.getSeqNo().equals(cateringOrderItemDO.getSeqNo()) && !item.getVersion().equals(cateringOrderItemDO.getVersion())) {
                    throw new BizException(ErrorCode.PREPARE_ERROR);
                }
            }
        }
    }

    public void produceValid(Long tenantId, Long userId, Long orderId, ProduceCateringOrderHttpRequest request) {
        baseValid(tenantId, userId);
        CateringOrderDO cateringOrderDO = orderMapper.queryById(tenantId, orderId);
        if (Objects.isNull(cateringOrderDO)) {
            throw new BizException(ErrorCode.PRODUCE_ERROR);
        }
        if (!request.getVersion().equals(cateringOrderDO.getVersion())) {
            throw new BizException(ErrorCode.PRODUCE_ERROR);
        }

        List<CateringOrderItemDO> cateringOrderItemDOS = itemMapper.queryByOrderId(tenantId, orderId);
        if (CollectionUtils.isEmpty(cateringOrderItemDOS)) {
            throw new BizException(ErrorCode.PRODUCE_ERROR);
        }

        cateringOrderItemDOS.forEach(cateringOrderItemDO -> {
            request.getItems().forEach(item -> {
                if (item.getSeqNo().equals(cateringOrderItemDO.getSeqNo())) {
                    if (!item.getVersion().equals(cateringOrderItemDO.getVersion())) {
                        throw new BizException(ErrorCode.PRODUCE_ERROR);
                    }
                    if (item.getQuantityOnProduce().compareTo(cateringOrderItemDO.getLatestQuantity()
                            .subtract(cateringOrderItemDO.getProduceQuantity())) > 0) {
                        throw new BizException(ErrorCode.PRODUCE_ERROR);
                    }
                    List<CateringOrderItemAccessoryDO> accessoryDOS = accessoryMapper.queryByOrderItemId(tenantId, cateringOrderItemDO.getId());
                    item.getAccessories().forEach(accessory -> {
                        accessoryDOS.forEach(accessoryDO -> {
                            if (Objects.equals(accessory.getSeqNo(), accessoryDO.getSeqNo())) {
                                if (!accessory.getVersion().equals(accessoryDO.getVersion())) {
                                    throw new BizException(ErrorCode.PRODUCE_ERROR);
                                }
                                if (accessory.getQuantityOnProduce().compareTo(accessoryDO.getLatestQuantity()
                                        .subtract(accessoryDO.getProduceQuantity())) > 0) {
                                    throw new BizException(ErrorCode.PRODUCE_ERROR);
                                }
                            }
                        });
                    });
                }
            });
        });
    }

    public void billValid(Long tenantId, Long userId, Long orderId, BillCateringOrderHttpRequest request) {
        baseValid(tenantId, userId);
        CateringOrderDO cateringOrderDO = orderMapper.queryById(tenantId, orderId);
        if (!cateringOrderDO.getVersion().equals(request.getVersion())) {
            throw new BizException(ErrorCode.BILL_ERROR);
        }
    }

    public void adjustValid(Long tenantId, Long userId, Long orderId, AdjustCateringOrderHttpRequest request) {
        baseValid(tenantId, userId);
        CateringOrderDO cateringOrderDO = orderMapper.queryById(tenantId, orderId);
        if (Objects.isNull(cateringOrderDO)) {
            throw new BizException(ErrorCode.ADJUST_ERROR);
        }
        if (!request.getVersion().equals(cateringOrderDO.getVersion())) {
            throw new BizException(ErrorCode.ADJUST_ERROR);
        }

        List<CateringOrderItemDO> cateringOrderItemDOS = itemMapper.queryByOrderId(tenantId, orderId);
        List<String> seqNoList = cateringOrderItemDOS.stream().map(CateringOrderItemDO::getSeqNo).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cateringOrderItemDOS)) {
            throw new BizException(ErrorCode.ADJUST_ERROR);
        }
        cateringOrderItemDOS.forEach(cateringOrderItemDO -> {
            request.getItems().forEach(item -> {
                if (Objects.isNull(item.getProductId())) {
                    if (item.getSeqNo().equals(cateringOrderItemDO.getSeqNo())
                            && !item.getVersion().equals(cateringOrderItemDO.getVersion())) {
                        throw new BizException(ErrorCode.ADJUST_ERROR);
                    }
                    if (item.getSeqNo().equals(cateringOrderItemDO.getSeqNo())
                            && (item.getQuantityOnAdjustment().add(cateringOrderItemDO.getLatestQuantity()))
                            .compareTo(BigDecimal.ZERO) < 0) {
                        throw new BizException(ErrorCode.ADJUST_ERROR);
                    }
                }
                if (Objects.nonNull(item.getProductId())) {
                    if (seqNoList.contains(item.getSeqNo())) {
                        throw new BizException(ErrorCode.ADJUST_ERROR);
                    }
                }
            });
        });
        request.getItems().forEach(item -> {
            CateringOrderItemDO orderItemDO = itemMapper.queryByOrderIdAndSeqNo(tenantId, orderId, item.getSeqNo());
            AtomicInteger size = new AtomicInteger();
            HashSet<String> set = Sets.newHashSet();
            item.getAccessories().forEach(accessory -> {
                if (Objects.isNull(accessory.getProductAccessoryId())) {
                    CateringOrderItemAccessoryDO accessoryDO = accessoryMapper.queryByItemIdAndSeqNO(tenantId, orderItemDO.getId(), accessory.getSeqNo());
                    if (!Objects.equals(accessoryDO.getVersion(), accessory.getVersion())) {
                        throw new BizException(ErrorCode.ADJUST_ERROR);
                    }
                }
                if (Objects.nonNull(accessory.getProductAccessoryId()) && Objects.isNull(item.getProductId())) {
                    List<CateringOrderItemAccessoryDO> accessoryDOS = accessoryMapper.queryByOrderItemId(tenantId, orderItemDO.getId());
                    List<String> lists = accessoryDOS.stream().map(CateringOrderItemAccessoryDO::getSeqNo).collect(Collectors.toList());
                    if (lists.contains(accessory.getSeqNo())) {
                        throw new BizException(ErrorCode.ADJUST_ERROR);
                    }
                }
                if (Objects.nonNull(accessory.getProductAccessoryId()) && Objects.nonNull(item.getProductId())) {
                    set.add(accessory.getSeqNo());
                    size.getAndIncrement();
                }
            });
            if (!Objects.equals(set.size(), size.get())) {
                throw new BizException(ErrorCode.ADJUST_ERROR);
            }
        });
    }
}

