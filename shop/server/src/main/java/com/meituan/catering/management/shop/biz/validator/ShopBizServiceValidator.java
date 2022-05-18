package com.meituan.catering.management.shop.biz.validator;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.shop.api.http.model.request.*;
import com.meituan.catering.management.shop.biz.model.request.SaveShopBizRequest;
import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mac
 */
@Component
public class ShopBizServiceValidator {
    @Resource
    private ShopMapper shopMapper;

    public void createValid(Long tenantId, Long userId, CreateShopHttpRequest createShopHttpRequest) throws BizException {
        //以下为示例
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    public void updateValid(Long tenantId, Long userId, String businessNo, UpdateShopHttpRequest updateShopHttpRequest) throws BizException {
        if (userId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    public void searchValid(Long tenantId, Long userId, SearchShopHttpRequest searchShopHttpRequest) throws BizException {
        if (userId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    public void closeValid(Long tenantId, Long userId,String businessNo, CloseShopHttpRequest closeShopHttpRequest) throws BizException {
        if (userId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    public void openValid(Long tenantId, Long userId, String businessNo,OpenShopHttpRequest openShopHttpRequest) throws BizException {
        if (userId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }
}
