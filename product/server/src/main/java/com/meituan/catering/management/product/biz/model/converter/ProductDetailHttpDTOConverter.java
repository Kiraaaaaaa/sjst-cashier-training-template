package com.meituan.catering.management.product.biz.model.converter;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.product.api.http.model.dto.ProductDetailHttpDTO;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.dao.model.ProductDO;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/21 16:37
 * @ClassName: ProductDetailHttpDTOConverter
 */
public class ProductDetailHttpDTOConverter {

    public static ProductDetailHttpDTO toProductDetailHttpDTO(ProductBO productBO){
        if (productBO == null){
            return null;
        }

        ProductDetailHttpDTO productDetailHttpDTO = new ProductDetailHttpDTO();
        productDetailHttpDTO.setId(productBO.getId());
        productDetailHttpDTO.setVersion(productBO.getVersion());
        productDetailHttpDTO.setTenantId(productBO.getTenantId());
        productDetailHttpDTO.setEnabled(productBO.getEnabled());
        productDetailHttpDTO.setName(productBO.getName());
        productDetailHttpDTO.setUnitPrice(productBO.getUnitPrice());
        productDetailHttpDTO.setMinSalesQuantity(productBO.getMinSalesQuantity());
        productDetailHttpDTO.setIncreaseSalesQuantity(productBO.getIncreaseSalesQuantity());
        productDetailHttpDTO.setUnitOfMeasure(productBO.getUnitOfMeasure());
        productDetailHttpDTO.setDescription(productBO.getDescription());

        AuditingHttpModel auditing = productDetailHttpDTO.getAuditing();
        auditing.setLastModifiedBy(productBO.getAuditing().getLastModifiedBy());
        auditing.setCreatedBy(productBO.getAuditing().getCreatedBy());
        auditing.setCreatedAt(productBO.getAuditing().getCreatedAt());
        auditing.setLastModifiedAt(productBO.getAuditing().getLastModifiedAt());


        for (ProductBO.AccessoryGroup accessoryGroup : productBO.getAccessoryGroups()) {
            productDetailHttpDTO.getAccessoryGroups().add(toAccessoryGroup(accessoryGroup));
        }
        for (ProductBO.MethodGroup methodGroup : productBO.getMethodGroups()) {
            productDetailHttpDTO.getMethodGroups().add(toMethodGroup(methodGroup));
        }

        return productDetailHttpDTO;
    }

    private static ProductDetailHttpDTO.AccessoryGroup toAccessoryGroup(ProductBO.AccessoryGroup accessoryBO){

        ProductDetailHttpDTO.AccessoryGroup accessoryGroup = new ProductDetailHttpDTO.AccessoryGroup();
        accessoryGroup.setName(accessoryBO.getName());
        for (ProductBO.AccessoryGroup.Option optionBO : accessoryBO.getOptions()) {
            ProductDetailHttpDTO.AccessoryGroup.Option option = new ProductDetailHttpDTO.AccessoryGroup.Option();
            option.setId(optionBO.getId());
            option.setName(optionBO.getName());
            option.setUnitPrice(optionBO.getUnitPrice());
            option.setUnitOfMeasure(optionBO.getUnitOfMeasure());
            accessoryGroup.getOptions().add(option);
            option = null;
        }
        return accessoryGroup;
    }

    private static ProductDetailHttpDTO.MethodGroup toMethodGroup(ProductBO.MethodGroup methodBO){
        ProductDetailHttpDTO.MethodGroup methodGroup = new ProductDetailHttpDTO.MethodGroup();
        methodGroup.setName(methodBO.getName());
        for (ProductBO.MethodGroup.Option optionBO : methodBO.getOptions()) {
            ProductDetailHttpDTO.MethodGroup.Option option = new ProductDetailHttpDTO.MethodGroup.Option();
            option.setId(optionBO.getId());
            option.setName(optionBO.getName());
            methodGroup.getOptions().add(option);
            option = null;
        }
        return methodGroup;
    }

}
