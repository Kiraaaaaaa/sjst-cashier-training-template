package com.meituan.catering.management.product.biz.model.converter;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.product.api.thrift.model.dto.ProductDetailThriftDTO;
import com.meituan.catering.management.product.biz.model.ProductBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/26 16:43
 * @ClassName: ProductDetailThriftDTOConverter
 */
public class ProductDetailThriftDTOConverter {

    public static ProductDetailThriftDTO toProductDetailThriftDTO(ProductBO productBO){

        if (productBO == null){
            return null;
        }
        ProductDetailThriftDTO productDetailThriftDTO = new ProductDetailThriftDTO();
        productDetailThriftDTO.setId(productBO.getId());
        productDetailThriftDTO.setVersion(productBO.getVersion());
        productDetailThriftDTO.setTenantId(productBO.getTenantId());
        productDetailThriftDTO.setEnabled(productBO.getEnabled());
        productDetailThriftDTO.setName(productBO.getName());
        productDetailThriftDTO.setUnitPrice(productBO.getUnitPrice().doubleValue());

        if (Objects.nonNull(productBO.getMinSalesQuantity())){
            productDetailThriftDTO.setMinSalesQuantity(productBO.getMinSalesQuantity().doubleValue());
        }
        if (Objects.nonNull(productBO.getIncreaseSalesQuantity())){
            productDetailThriftDTO.setIncreaseSalesQuantity(productBO.getIncreaseSalesQuantity().doubleValue());
        }
        productDetailThriftDTO.setUnitOfMeasure(productBO.getUnitOfMeasure());
        productDetailThriftDTO.setDescription(productBO.getDescription());

        AuditingThriftModel auditing = productDetailThriftDTO.getAuditing();
        auditing.setLastModifiedBy(productBO.getAuditing().getLastModifiedBy());
        auditing.setCreatedBy(productBO.getAuditing().getCreatedBy());
        if (productBO.getAuditing().getCreatedAt()!=null){
            auditing.setCreatedAtTimestamp(productBO.getAuditing().getCreatedAt().getTime());
        }
        if (productBO.getAuditing().getLastModifiedAt()!=null){
            auditing.setLastModifiedAtTimestamp(productBO.getAuditing().getLastModifiedAt().getTime());
        }

        for (ProductBO.AccessoryGroup accessoryGroup : productBO.getAccessoryGroups()) {
            productDetailThriftDTO.getAccessoryGroups().add(toAccessoryGroup(accessoryGroup));
        }
        for (ProductBO.MethodGroup methodGroup : productBO.getMethodGroups()) {
            productDetailThriftDTO.getMethodGroups().add(toMethodGroup(methodGroup));
        }

        return productDetailThriftDTO;
    }

    public static List<ProductDetailThriftDTO> toProductDetailThriftDTOList(List<ProductBO> productBOS){

        ArrayList<ProductDetailThriftDTO> list = Lists.newArrayList();
        for (ProductBO productBO : productBOS) {
            list.add(toProductDetailThriftDTO(productBO));
        }
        return list;
    }

    private static ProductDetailThriftDTO.AccessoryGroup toAccessoryGroup(ProductBO.AccessoryGroup accessoryBO){

        ProductDetailThriftDTO.AccessoryGroup accessoryGroup = new ProductDetailThriftDTO.AccessoryGroup();
        accessoryGroup.setName(accessoryBO.getName());
        for (ProductBO.AccessoryGroup.Option optionBO : accessoryBO.getOptions()) {
            ProductDetailThriftDTO.AccessoryGroup.Option option = new ProductDetailThriftDTO.AccessoryGroup.Option();
            option.setId(optionBO.getId());
            option.setName(optionBO.getName());
            option.setUnitPrice(optionBO.getUnitPrice().doubleValue());
            option.setUnitOfMeasure(optionBO.getUnitOfMeasure());
            accessoryGroup.getOptions().add(option);
            option = null;
        }
        return accessoryGroup;
    }

    private static ProductDetailThriftDTO.MethodGroup toMethodGroup(ProductBO.MethodGroup methodBO){
        ProductDetailThriftDTO.MethodGroup methodGroup = new ProductDetailThriftDTO.MethodGroup();
        methodGroup.setName(methodBO.getName());
        for (ProductBO.MethodGroup.Option optionBO : methodBO.getOptions()) {
            ProductDetailThriftDTO.MethodGroup.Option option = new ProductDetailThriftDTO.MethodGroup.Option();
            option.setId(optionBO.getId());
            option.setName(optionBO.getName());
            methodGroup.getOptions().add(option);
            option = null;
        }
        return methodGroup;
    }

}
