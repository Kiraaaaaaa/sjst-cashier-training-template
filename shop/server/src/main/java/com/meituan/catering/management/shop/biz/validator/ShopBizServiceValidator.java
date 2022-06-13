package com.meituan.catering.management.shop.biz.validator;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.shop.api.http.model.request.*;
import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import com.meituan.catering.management.shop.dao.model.ShopDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author mac
 */
@Component
public class ShopBizServiceValidator {
    @Resource
    private ShopMapper shopMapper;

    public void createValid(Long tenantId, Long userId, CreateShopHttpRequest createShopHttpRequest) throws BizException {
        baseValid(tenantId, userId);
    }

    public void updateValid(Long tenantId, Long userId, String businessNo, UpdateShopHttpRequest updateShopHttpRequest) throws BizException {
        ShopDO shopDO = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
        if (!Objects.equals(shopDO.getVersion(),updateShopHttpRequest.getVersion())){
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        baseValid(tenantId, userId, businessNo);
    }

    public void searchValid(Long tenantId, Long userId, String businessNo) throws BizException {
        baseValid(tenantId, userId, businessNo);
    }

    public void searchByValid(Long tenantId, Long userId, SearchShopHttpRequest searchShopHttpRequest) throws BizException {
        baseValid(tenantId, userId);
    }

    public void closeValid(Long tenantId, Long userId, String businessNo, CloseShopHttpRequest closeShopHttpRequest) throws BizException {
        baseValid(tenantId, userId, businessNo);
        ShopDO shopDO = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
        if (!Objects.equals(shopDO.getVersion(),closeShopHttpRequest.getVersion())){
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
    }

    public void openValid(Long tenantId, Long userId, String businessNo, OpenShopHttpRequest openShopHttpRequest) throws BizException {
        baseValid(tenantId, userId, businessNo);
        ShopDO shopDO = shopMapper.findByBusinessNo(tenantId, userId, businessNo);
        if (!Objects.equals(shopDO.getVersion(),openShopHttpRequest.getVersion())){
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        baseValid(tenantId, userId, businessNo);
    }

    private static void baseValid(Long tenantId, Long userId, String businessNo) throws BizException {
        if (userId < 0 || tenantId < 0 || businessNo == null) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    private static void baseValid(Long tenantId, Long userId) throws BizException {
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }
}
