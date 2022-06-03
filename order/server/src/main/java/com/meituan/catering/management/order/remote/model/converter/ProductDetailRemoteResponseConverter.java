package com.meituan.catering.management.order.remote.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.order.remote.model.response.ProductDetailRemoteResponse;
import com.meituan.catering.management.product.api.thrift.model.dto.ProductDetailThriftDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/6/1 10:26
 * @ClassName: ProductDetailRemoteResponseConverter
 */
public class ProductDetailRemoteResponseConverter {

    public static ProductDetailRemoteResponse toProductDetailRemoteResponse(ProductDetailThriftDTO thriftDTO){

        ProductDetailRemoteResponse response = new ProductDetailRemoteResponse();
        response.setId(thriftDTO.getId());
        response.setTenantId(thriftDTO.getTenantId());
        response.setVersion(thriftDTO.getVersion());
        response.setName(thriftDTO.getName());
        response.setUnitPrice(thriftDTO.getUnitPrice());
        response.setUnitOfMeasure(thriftDTO.getUnitOfMeasure());
        response.setMinSalesQuantity(thriftDTO.getMinSalesQuantity());
        response.setIncreaseSalesQuantity(thriftDTO.getIncreaseSalesQuantity());
        response.setDescription(thriftDTO.getDescription());
        response.setEnabled(thriftDTO.getEnabled());

        response.getAuditing().setLastModifiedAtTimestamp(thriftDTO.getAuditing().getLastModifiedAtTimestamp());
        response.getAuditing().setCreatedBy(thriftDTO.getAuditing().getCreatedBy());
        response.getAuditing().setCreatedAtTimestamp(thriftDTO.getAuditing().getCreatedAtTimestamp());
        response.getAuditing().setLastModifiedBy(thriftDTO.getAuditing().getLastModifiedBy());

        List<ProductDetailThriftDTO.AccessoryGroup> accessoryGroups = thriftDTO.getAccessoryGroups();
        for (ProductDetailThriftDTO.AccessoryGroup accessoryGroup : accessoryGroups) {
            response.getAccessoryGroups().add(buildAccessory(accessoryGroup));
        }

        List<ProductDetailThriftDTO.MethodGroup> methodGroups = thriftDTO.getMethodGroups();
        for (ProductDetailThriftDTO.MethodGroup methodGroup : methodGroups) {
            response.getMethodGroups().add(buildMethodGroup(methodGroup));
        }

        return response;
    }

    public static List<ProductDetailRemoteResponse> toProductDetailRemoteResponse(List<ProductDetailThriftDTO> thriftDTOS){

        ArrayList<ProductDetailRemoteResponse> list = Lists.newArrayList();
        for (ProductDetailThriftDTO thriftDTO : thriftDTOS) {
            list.add(toProductDetailRemoteResponse(thriftDTO));
        }
        return list;
    }

    private static ProductDetailRemoteResponse.MethodGroup buildMethodGroup(ProductDetailThriftDTO.MethodGroup thriftMethod){
        if (thriftMethod == null){
            return null;
        }

        ProductDetailRemoteResponse.MethodGroup methodGroup = new ProductDetailRemoteResponse.MethodGroup();
        methodGroup.setName(thriftMethod.getName());

        List<ProductDetailRemoteResponse.MethodGroup.Option> methodGroupOptions = methodGroup.getOptions();
        List<ProductDetailThriftDTO.MethodGroup.Option> options = thriftMethod.getOptions();
        for (ProductDetailThriftDTO.MethodGroup.Option option : options) {
            ProductDetailRemoteResponse.MethodGroup.Option optionResp = new ProductDetailRemoteResponse.MethodGroup.Option();
            optionResp.setId(option.getId());
            optionResp.setName(option.getName());
            methodGroupOptions.add(optionResp);
            optionResp = null;
        }
        return methodGroup;
    }

    private static ProductDetailRemoteResponse.AccessoryGroup buildAccessory(ProductDetailThriftDTO.AccessoryGroup thriftAccessory){
        if (thriftAccessory == null){
            return null;
        }

        ProductDetailRemoteResponse.AccessoryGroup accessoryGroup = new ProductDetailRemoteResponse.AccessoryGroup();
        accessoryGroup.setName(thriftAccessory.getName());

        List<ProductDetailRemoteResponse.AccessoryGroup.Option> accessoryGroupOptions = accessoryGroup.getOptions();
        List<ProductDetailThriftDTO.AccessoryGroup.Option> options = thriftAccessory.getOptions();
        for (ProductDetailThriftDTO.AccessoryGroup.Option option : options) {
            ProductDetailRemoteResponse.AccessoryGroup.Option optionResp = new ProductDetailRemoteResponse.AccessoryGroup.Option();
            optionResp.setUnitPrice(option.getUnitPrice());
            optionResp.setUnitOfMeasure(option.getUnitOfMeasure());
            optionResp.setId(option.getId());
            optionResp.setName(option.getName());
            accessoryGroupOptions.add(optionResp);
        }
        return accessoryGroup;
    }
}
