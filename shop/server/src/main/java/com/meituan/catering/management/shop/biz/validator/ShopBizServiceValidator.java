package com.meituan.catering.management.shop.biz.validator;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.shop.api.http.model.request.CreateShopHttpRequest;
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
        if (userId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }
}
