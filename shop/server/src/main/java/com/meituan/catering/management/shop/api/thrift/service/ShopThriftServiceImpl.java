package com.meituan.catering.management.shop.api.thrift.service;

import com.meituan.catering.management.common.helper.StatusHelper;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.shop.api.thrift.model.dto.ShopDetailThriftDTO;
import com.meituan.catering.management.shop.api.thrift.model.response.ShopDetailThriftResponse;
import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.converter.ShopDetailThriftDTOConverter;
import com.meituan.catering.management.shop.biz.service.ShopBizService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class ShopThriftServiceImpl implements ShopThriftService {

    @Resource
    private ShopBizService shopBizService;
    @Override
    public ShopDetailThriftResponse findByBusinessNo(
            UserContextThriftRequest userContext, String businessNo) {
        ShopBO shopBO = shopBizService.findByBusinessNo(userContext.getTenantId(), userContext.getUserId(), businessNo);
        ShopDetailThriftResponse response = new ShopDetailThriftResponse();
        response.setStatus(StatusHelper.success());
        response.setData(ShopDetailThriftDTOConverter.toShopDetailThriftDTO(shopBO));
        return response;
    }

    @Override
    public List<ShopDetailThriftResponse> findByBusinessNoList(UserContextThriftRequest userContext, Set<String> businessNoList) {
        return null;
    }
}
