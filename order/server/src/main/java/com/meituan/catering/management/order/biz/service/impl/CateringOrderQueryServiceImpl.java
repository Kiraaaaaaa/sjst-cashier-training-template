package com.meituan.catering.management.order.biz.service.impl;

import cn.hutool.core.stream.CollectorUtil;
import com.meituan.catering.management.order.biz.model.CateringOrderBO;
import com.meituan.catering.management.order.biz.model.converter.CateringOrderBOConverter;
import com.meituan.catering.management.order.biz.model.converter.SearchCateringOrderResponseConverter;
import com.meituan.catering.management.order.biz.model.converter.SearchCateringOrderRequestConverter;

import com.meituan.catering.management.order.biz.model.request.SearchCateringOrderBizRequest;
import com.meituan.catering.management.order.biz.model.response.SearchCateringOrderBizResponse;
import com.meituan.catering.management.order.biz.service.CateringOrderQueryService;
import com.meituan.catering.management.order.dao.mapper.CateringOrderItemAccessoryMapper;
import com.meituan.catering.management.order.dao.mapper.CateringOrderItemMapper;
import com.meituan.catering.management.order.dao.mapper.CateringOrderMapper;
import com.meituan.catering.management.order.dao.model.CateringOrderDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemAccessoryDO;
import com.meituan.catering.management.order.dao.model.CateringOrderItemDO;
import com.meituan.catering.management.order.dao.model.request.SearchCateringOrderDataRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CateringOrderQueryServiceImpl implements CateringOrderQueryService {
    @Resource
    private CateringOrderMapper orderMapper;

    @Resource
    private CateringOrderItemMapper itemMapper;

    @Resource
    private CateringOrderItemAccessoryMapper accessoryMapper;

    @Override
    public CateringOrderBO findById(Long tenantId, Long orderId) {
        CateringOrderDO cateringOrderDO = orderMapper.queryById(tenantId, orderId);
        List<CateringOrderItemDO> itemDOS = itemMapper.queryByOrderId(tenantId, orderId);
        List<Long> orderItemIds = itemDOS.stream().map(CateringOrderItemDO::getId).filter(Objects::nonNull).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(orderItemIds)){
            List<CateringOrderItemAccessoryDO> accessoryDOS = accessoryMapper.batchQueryByOrderItemId(tenantId, orderItemIds);
            return CateringOrderBOConverter.toCateringOrderBO(cateringOrderDO, itemDOS, accessoryDOS);
        }
        return CateringOrderBOConverter.toCateringOrderBO(cateringOrderDO,itemDOS,null);
    }


    @Override
    public SearchCateringOrderBizResponse searchForPage(Long tenantId, SearchCateringOrderBizRequest request) {
        SearchCateringOrderDataRequest dataRequest = SearchCateringOrderRequestConverter.toSearchCateringOrderDataRequest(tenantId, request);
        List<CateringOrderDO> cateringOrderDOS = orderMapper.searchForPage(dataRequest);
        Integer totalCount = orderMapper.countForPage(dataRequest);
        SearchCateringOrderBizResponse bizResponse = SearchCateringOrderResponseConverter.toSearchCateringOrderBizResponse(request.getPageIndex(), request.getPageSize(), totalCount, cateringOrderDOS);
        return bizResponse;
    }

}
