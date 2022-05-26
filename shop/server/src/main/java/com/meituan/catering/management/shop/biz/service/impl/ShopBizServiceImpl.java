package com.meituan.catering.management.shop.biz.service.impl;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
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
        ShopDO shopDO = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
        return ShopBOConverter.toShopBO(shopDO);
    }

    @Override
    public ShopBO create(Long tenantId, Long userId, SaveShopBizRequest saveShopBizRequest) {
        return transactionTemplate.execute(status -> {
            ShopDO shopDO = ShopDOConverter.toShopDO(tenantId, userId, saveShopBizRequest);
            int id = shopMapper.insert(shopDO);
            if (id == 0) {
                status.setRollbackOnly();
                throw new BizException(ErrorCode.PARAM_ERROR);
            }
            ShopDO shopDOLater = shopMapper.selectById(shopDO.getId());
            return ShopBOConverter.toShopBO(shopDOLater);
        });
    }

    @Override
    public ShopBO update(Long tenantId, Long userId, String businessNo, UpdateShopBizRequest updateShopBizRequest) {
        return transactionTemplate.execute(status -> {
            ShopDO shopDO = ShopDOConverter.toShopDO(tenantId, userId, businessNo, updateShopBizRequest);
            int id = shopMapper.update(shopDO);
            if (id == 0) {
                status.setRollbackOnly();
                throw new BizException(ErrorCode.UPDATE_ERROR);
            }
            ShopDO shopDOLater = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
            return ShopBOConverter.toShopBO(shopDOLater);
        });
    }

    @Override
    public List<ShopBO> searchByConditional(Long tenantId, Long userId, SearchShopBizRequest searchShopBizRequest) throws BizException {
        return transactionTemplate.execute(status -> {
            List<ShopBO> shopBOS = new ArrayList<>();
            SearchShopDataRequest searchShopDataRequest = SearchShopDataRequestConverter.toSearchShopDataRequest(tenantId, userId, searchShopBizRequest);
            List<ShopDO> shopDOS = shopMapper.selectByConditional(searchShopDataRequest);
            for (ShopDO shopDO : shopDOS) {
                ShopBO shopBO = ShopBOConverter.toShopBO(shopDO);
                shopBOS.add(shopBO);
            }
            return shopBOS;
        });
    }

    @Override
    public int searchTotalCount(Long tenantId, Long userId, SearchShopBizRequest searchShopBizRequest) {
        return transactionTemplate.execute(status -> {
            SearchShopDataRequest searchShopDataRequest = SearchShopDataRequestConverter.toSearchShopDataRequest(tenantId, userId, searchShopBizRequest);
            List<ShopDO> shopDOS = shopMapper.selectTotalCount(searchShopDataRequest);
            int totalCount = shopDOS.size();
            return totalCount;
        });
    }

    @Override
    public ShopBO open(Long tenantId, Long userId, String businessNo, OpenShopBizRequest openShopBizRequest) throws BizException {
        return transactionTemplate.execute(status -> {
            OpenShopDataRequest openShopDataRequest = SwitchShopDateRequestConverter.toOpenShopDataRequest(tenantId, userId, businessNo, openShopBizRequest);
            //todo:合并
            int id = shopMapper.open(openShopDataRequest);
            if (id == 0) {
                status.setRollbackOnly();
                throw new BizException(ErrorCode.OPEN_ERROR);
            }
            ShopDO shopDOLater = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
            return ShopBOConverter.toShopBO(shopDOLater);
        });
    }

    @Override
    public ShopBO close(Long tenantId, Long userId, String businessNo, CloseShopBizRequest closeShopBizRequest) throws BizException {
        return transactionTemplate.execute(status -> {
            CloseShopDataRequest closeShopDataRequest = SwitchShopDateRequestConverter.toCloseShopDataRequest(tenantId, userId, businessNo, closeShopBizRequest);
            int id = shopMapper.close(closeShopDataRequest);
            if (id == 0) {
                status.setRollbackOnly();
                throw new BizException(ErrorCode.CLOSE_ERROR);
            }
            ShopDO shopDOLater = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
            return ShopBOConverter.toShopBO(shopDOLater);
        });
    }

}
