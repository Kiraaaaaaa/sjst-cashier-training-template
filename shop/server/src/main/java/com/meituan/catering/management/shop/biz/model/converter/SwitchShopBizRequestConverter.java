package com.meituan.catering.management.shop.biz.model.converter;

import com.meituan.catering.management.shop.api.http.model.request.CloseShopHttpRequest;
import com.meituan.catering.management.shop.api.http.model.request.OpenShopHttpRequest;
import com.meituan.catering.management.shop.biz.model.request.CloseShopBizRequest;
import com.meituan.catering.management.shop.biz.model.request.OpenShopBizRequest;


/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/17 14:43
 * @ClassName: SwitchShopBizRequestConverter
 */
public class SwitchShopBizRequestConverter {

    public static OpenShopBizRequest toOpenShopBizRequest(OpenShopHttpRequest request){

        OpenShopBizRequest openShopBizRequest = new OpenShopBizRequest();
        openShopBizRequest.setVersion(request.getVersion());

        return openShopBizRequest;
    }

    public static CloseShopBizRequest toCloseShopBizRequest(CloseShopHttpRequest request){

        CloseShopBizRequest closeShopBizRequest = new CloseShopBizRequest();
        closeShopBizRequest.setVersion(request.getVersion());


        return closeShopBizRequest;
    }
}
