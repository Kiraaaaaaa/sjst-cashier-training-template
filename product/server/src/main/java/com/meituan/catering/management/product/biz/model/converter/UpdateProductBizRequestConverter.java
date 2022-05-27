package com.meituan.catering.management.product.biz.model.converter;

import com.meituan.catering.management.product.api.http.model.request.EnableProductHttpRequest;
import com.meituan.catering.management.product.api.http.model.request.UpdateProductHttpRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 14:48
 * @ClassName: UpdateProductBizRequestConverter
 */
public class UpdateProductBizRequestConverter {

    public static UpdateProductBizRequest toUpdateProductBizRequest(UpdateProductHttpRequest request){
        UpdateProductBizRequest updateProductBizRequest = new UpdateProductBizRequest();
        updateProductBizRequest.setName(request.getName());
        updateProductBizRequest.setUnitPrice(request.getUnitPrice());
        updateProductBizRequest.setMinSalesQuantity(request.getMinSalesQuantity());
        updateProductBizRequest.setIncreaseSalesQuantity(request.getIncreaseSalesQuantity());
        updateProductBizRequest.setUnitOfMeasure(request.getUnitOfMeasure());
        updateProductBizRequest.setDescription(request.getDescription());
        updateProductBizRequest.setVersion(request.getVersion());

        List<UpdateProductHttpRequest.AccessoryGroup> accessoryGroups = request.getAccessoryGroups();
        for (UpdateProductHttpRequest.AccessoryGroup accessoryGroup : accessoryGroups) {
            updateProductBizRequest.getAccessoryGroups().add(toAccessoryGroupBiz(accessoryGroup));
        }

        List<UpdateProductHttpRequest.MethodGroup> methodGroups = request.getMethodGroups();
        for (UpdateProductHttpRequest.MethodGroup methodGroup : methodGroups) {
            updateProductBizRequest.getMethodGroups().add(toMethodGroupBiz(methodGroup));
        }

        return updateProductBizRequest;
    }

    public static UpdateProductBizRequest toUpdateProductBizRequest(EnableProductHttpRequest request){
        UpdateProductBizRequest updateProductBizRequest = new UpdateProductBizRequest();
        updateProductBizRequest.setVersion(request.getVersion());
        return updateProductBizRequest;
    }

    private static UpdateProductBizRequest.AccessoryGroup toAccessoryGroupBiz(
            UpdateProductHttpRequest.AccessoryGroup accessoryGroup){
        if (accessoryGroup == null){
            return null;
        }
        UpdateProductBizRequest.AccessoryGroup accessoryGroupBiz = new UpdateProductBizRequest.AccessoryGroup();
        accessoryGroupBiz.setName(accessoryGroup.getName());
        List<UpdateProductHttpRequest.AccessoryGroup.Option> options = accessoryGroup.getOptions();
        for (UpdateProductHttpRequest.AccessoryGroup.Option option : options) {
            UpdateProductBizRequest.AccessoryGroup.Option optionBiz = new UpdateProductBizRequest.AccessoryGroup.Option();
            optionBiz.setId(option.getId());
            optionBiz.setName(option.getName());
            optionBiz.setUnitPrice(option.getUnitPrice());
            optionBiz.setUnitOfMeasure(option.getUnitOfMeasure());
            accessoryGroupBiz.getOptions().add(optionBiz);
            optionBiz = null;
        }
        return accessoryGroupBiz;
    }

    private static UpdateProductBizRequest.MethodGroup toMethodGroupBiz(
            UpdateProductHttpRequest.MethodGroup methodGroup){
        if (methodGroup == null){
            return null;
        }
        UpdateProductBizRequest.MethodGroup methodGroupBiz = new UpdateProductBizRequest.MethodGroup();
        methodGroupBiz.setName(methodGroup.getName());
        List<UpdateProductHttpRequest.MethodGroup.Option> options = methodGroup.getOptions();
        for (UpdateProductHttpRequest.MethodGroup.Option option : options) {
            UpdateProductBizRequest.MethodGroup.Option optionBiz= new UpdateProductBizRequest.MethodGroup.Option();
            optionBiz.setId(option.getId());
            optionBiz.setName(option.getName());
            methodGroupBiz.getOptions().add(optionBiz);
            optionBiz = null;
        }
        return methodGroupBiz;
    }
}
