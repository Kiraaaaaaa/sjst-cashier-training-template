package com.meituan.catering.management.shop.dao.converter;

import com.meituan.catering.management.shop.biz.model.request.CloseShopBizRequest;
import com.meituan.catering.management.shop.biz.model.request.OpenShopBizRequest;
import com.meituan.catering.management.shop.dao.model.request.CloseShopDataRequest;
import com.meituan.catering.management.shop.dao.model.request.OpenShopDataRequest;

import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 15:17
 * @ClassName: SwitchShopDateRequestConverter
 */
public class SwitchShopDateRequestConverter {

    public static OpenShopDataRequest toOpenShopDataRequest(Long tenantId,Long userId,String businessNo,OpenShopBizRequest request){
        OpenShopDataRequest openShopDataRequest = new OpenShopDataRequest();
        openShopDataRequest.setVersion(request.getVersion());
        openShopDataRequest.setBusinessNo(businessNo);
        openShopDataRequest.setLastModifiedAt(new Date());
        openShopDataRequest.setLastModifiedBy(userId);
        openShopDataRequest.setTenantId(tenantId);
        return openShopDataRequest;
    }


    public static CloseShopDataRequest toCloseShopDataRequest(Long tenantId, Long userId,String businessNo,CloseShopBizRequest request){
        CloseShopDataRequest closeShopDataRequest = new CloseShopDataRequest();
        closeShopDataRequest.setVersion(request.getVersion());
        closeShopDataRequest.setBusinessNo(businessNo);
        closeShopDataRequest.setTenantId(tenantId);
        closeShopDataRequest.setLastModifiedAt(new Date());
        closeShopDataRequest.setLastModifiedBy(userId);
        return closeShopDataRequest;
    }
}
