package com.meituan.catering.management.product.biz.model.converter;
import com.meituan.catering.management.product.api.http.model.request.CreateProductHttpRequest;
import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 13:06
 * @ClassName: CreateProductBizRequestConverter
 */
public class CreateProductBizRequestConverter {

    public static CreateProductBizRequest toCreateProductBizRequest(CreateProductHttpRequest request){
        CreateProductBizRequest createProductBizRequest = new CreateProductBizRequest();
        createProductBizRequest.setName(request.getName());
        createProductBizRequest.setUnitPrice(request.getUnitPrice());
        createProductBizRequest.setMinSalesQuantity(request.getMinSalesQuantity());
        createProductBizRequest.setIncreaseSalesQuantity(request.getIncreaseSalesQuantity());
        createProductBizRequest.setUnitOfMeasure(request.getUnitOfMeasure());
        createProductBizRequest.setDescription(request.getDescription());

        List<CreateProductHttpRequest.AccessoryGroup> accessoryGroups = request.getAccessoryGroups();
        for (CreateProductHttpRequest.AccessoryGroup accessoryGroup : accessoryGroups) {
            createProductBizRequest.getAccessoryGroups().add(toAccessoryGroupBiz(accessoryGroup));
        }

        List<CreateProductHttpRequest.MethodGroup> methodGroups = request.getMethodGroups();
        for (CreateProductHttpRequest.MethodGroup methodGroup : methodGroups) {
            createProductBizRequest.getMethodGroups().add(toMethodGroupBiz(methodGroup));
        }

        return createProductBizRequest;
    }

    private static CreateProductBizRequest.AccessoryGroup toAccessoryGroupBiz(
            CreateProductHttpRequest.AccessoryGroup accessoryGroup){
        if (accessoryGroup == null){
            return null;
        }
        CreateProductBizRequest.AccessoryGroup accessoryGroupBiz = new CreateProductBizRequest.AccessoryGroup();
        accessoryGroupBiz.setName(accessoryGroup.getName());
        List<CreateProductHttpRequest.AccessoryGroup.Option> options = accessoryGroup.getOptions();
        for (CreateProductHttpRequest.AccessoryGroup.Option option : options) {
            CreateProductBizRequest.AccessoryGroup.Option optionBiz = new CreateProductBizRequest.AccessoryGroup.Option();
            optionBiz.setName(option.getName());
            optionBiz.setUnitPrice(option.getUnitPrice());
            optionBiz.setUnitOfMeasure(option.getUnitOfMeasure());
            accessoryGroupBiz.getOptions().add(optionBiz);
            optionBiz = null;
        }
        return accessoryGroupBiz;
    }

    private static CreateProductBizRequest.MethodGroup toMethodGroupBiz(
            CreateProductHttpRequest.MethodGroup methodGroup){
        if (methodGroup == null){
            return null;
        }
        CreateProductBizRequest.MethodGroup methodGroupBiz = new CreateProductBizRequest.MethodGroup();
        methodGroupBiz.setName(methodGroup.getName());
        List<CreateProductHttpRequest.MethodGroup.Option> options = methodGroup.getOptions();
        for (CreateProductHttpRequest.MethodGroup.Option option : options) {
            CreateProductBizRequest.MethodGroup.Option optionBiz= new CreateProductBizRequest.MethodGroup.Option();
            optionBiz.setName(option.getName());
            methodGroupBiz.getOptions().add(optionBiz);
            optionBiz = null;
        }
        return methodGroupBiz;
    }
}
