package com.meituan.catering.management.product.biz.model.converter;

import com.meituan.catering.management.product.api.http.model.request.DisableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.EnableProductHttpRequest;
import com.meituan.catering.management.product.biz.model.request.SwitchProductBizRequest;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 10:41
 * @ClassName: SwitchProductBizRequestConverter
 */
public class SwitchProductBizRequestConverter {

    public static SwitchProductBizRequest toSwitchProductBizRequest(EnableProductHttpRequest request){
        SwitchProductBizRequest switchProductBizRequest = new SwitchProductBizRequest();
        switchProductBizRequest.setVersion(request.getVersion());
        switchProductBizRequest.setEnabled(true);
        return switchProductBizRequest;
    }

    public static SwitchProductBizRequest toSwitchProductBizRequest(DisableProductHttpRequest request){
        SwitchProductBizRequest switchProductBizRequest = new SwitchProductBizRequest();
        switchProductBizRequest.setVersion(request.getVersion());
        switchProductBizRequest.setEnabled(false);
        return switchProductBizRequest;
    }
}
