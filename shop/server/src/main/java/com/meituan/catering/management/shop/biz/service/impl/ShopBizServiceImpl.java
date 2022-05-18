package com.meituan.catering.management.shop.biz.service.impl;

import com.meituan.catering.management.shop.biz.model.ShopBO;
import com.meituan.catering.management.shop.biz.model.converter.ShopBOConverter;
import com.meituan.catering.management.shop.biz.model.request.*;
import com.meituan.catering.management.shop.biz.service.ShopBizService;
import com.meituan.catering.management.shop.dao.converter.SearchShopDataRequestConverter;
import com.meituan.catering.management.shop.dao.converter.ShopDOConverter;
import com.meituan.catering.management.shop.dao.converter.SwitchShopDateRequestConverter;
import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import com.meituan.catering.management.shop.dao.model.ShopDO;
import com.meituan.catering.management.shop.dao.model.request.CloseShopDataRequest;
import com.meituan.catering.management.shop.dao.model.request.OpenShopDataRequest;
import com.meituan.catering.management.shop.dao.model.request.SearchShopDataRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        Assert.isTrue(id>=0,"创建门店错误");
        return ShopBOConverter.toShopBO(shopMapper.selectById(shopDO.getId()));
    }

    @Override
    public ShopBO update(Long tenantId, Long userId, String businessNo, UpdateShopBizRequest updateShopBizRequest) {
        ShopDO shopDO = ShopDOConverter.toShopDO(tenantId, userId, businessNo, updateShopBizRequest);
        int id = shopMapper.update(shopDO);
        Assert.isTrue(id>=0,"跟新门店错误");
        return ShopBOConverter.toShopBO(shopMapper.findByBusinessNo(tenantId, userId,businessNo));
    }

    @Override
    public List<ShopBO> searchByConditional(Long tenantId, Long userId, SearchShopBizRequest searchShopBizRequest) {
        SearchShopDataRequest searchShopDataRequest = SearchShopDataRequestConverter.toSearchShopDataRequest(tenantId,userId,searchShopBizRequest);
        List<ShopDO> shopDOS = shopMapper.selectByConditional(searchShopDataRequest);
        Assert.isTrue(shopDOS!=null,"查找门店错误");
        ArrayList<ShopBO> shopBOS = new ArrayList<>();
        for (ShopDO shopDO : shopDOS) {
            ShopBO shopBO = ShopBOConverter.toShopBO(shopDO);
            shopBOS.add(shopBO);
        }
        return shopBOS;
    }

    @Override
    public ShopBO open(Long tenantId, Long userId, String businessNo, OpenShopBizRequest openShopBizRequest) {

        OpenShopDataRequest openShopDataRequest = SwitchShopDateRequestConverter.toOpenShopDataRequest(tenantId,userId,businessNo, openShopBizRequest);
        int id = shopMapper.open(openShopDataRequest);
        Assert.isTrue(id>=0,"打开门店错误");
        return ShopBOConverter.toShopBO(shopMapper.findByBusinessNo(tenantId,userId,businessNo));
    }

    @Override
    public ShopBO close(Long tenantId, Long userId, String businessNo, CloseShopBizRequest closeShopBizRequest) {
        CloseShopDataRequest closeShopDataRequest = SwitchShopDateRequestConverter.toCloseShopDataRequest(tenantId, userId, businessNo, closeShopBizRequest);
        int id = shopMapper.close(closeShopDataRequest);
        Assert.isTrue(id>=0,"关闭门店错误");
        return ShopBOConverter.toShopBO(shopMapper.findByBusinessNo(tenantId,userId,businessNo));
    }

}
