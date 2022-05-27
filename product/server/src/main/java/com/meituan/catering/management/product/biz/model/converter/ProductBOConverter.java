package com.meituan.catering.management.product.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.common.model.biz.AuditingBO;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import com.meituan.catering.management.product.dao.model.ProductDO;
import com.meituan.catering.management.product.dao.model.ProductMethodDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 从其他数据模型向商品BO的转换器
 */
public abstract class ProductBOConverter {

    public static ProductBO toProductBO(ProductDO productDO, List<ProductMethodDO> productMethodDOList,
                                  List<ProductAccessoryDO> productAccessoryDOList) {
        if (productDO == null) {
            return null;
        }

        ProductBO productBO = new ProductBO();

        productBO.setEnabled(productDO.getEnabled());
        productBO.setName(productDO.getName());
        productBO.setUnitPrice(productDO.getUnitPrice());
        productBO.setMinSalesQuantity(productDO.getMinSalesQuantity());
        productBO.setIncreaseSalesQuantity(productDO.getIncreaseSalesQuantity());
        productBO.setUnitOfMeasure(productDO.getUnitOfMeasure());
        productBO.setDescription(productDO.getDescription());
        productBO.setId(productDO.getId());
        productBO.setTenantId(productDO.getTenantId());
        productBO.setVersion(productDO.getVersion());
        productBO.setAuditing(toAuditingBO(productDO));
        if (productAccessoryDOList != null){
            List<ProductBO.AccessoryGroup> accessoryGroups = productBO.getAccessoryGroups();
            for (ProductAccessoryDO productAccessoryDO : productAccessoryDOList) {
                ProductBO.AccessoryGroup accessoryGroup = new ProductBO.AccessoryGroup();
                if (accessoryGroups.size() >= 1) {
                    ProductBO.AccessoryGroup accessoryGroupEnd = accessoryGroups.get(accessoryGroups.size() - 1);
                    if (accessoryGroupEnd.getName().equals(productAccessoryDO.getGroupName())) {
                        ProductBO.AccessoryGroup.Option option = toAccessoryOption(productAccessoryDO);
                        accessoryGroupEnd.getOptions().add(option);
                        option = null;
                        continue;
                    }
                }
                accessoryGroup.setName(productAccessoryDO.getGroupName());
                ProductBO.AccessoryGroup.Option option = toAccessoryOption(productAccessoryDO);
                accessoryGroup.getOptions().add(option);
                option = null;
                accessoryGroups.add(accessoryGroup);
                accessoryGroup = null;
            }
        }

        if (productMethodDOList!=null){
            List<ProductBO.MethodGroup> methodGroups = productBO.getMethodGroups();
            for (ProductMethodDO productMethodDO : productMethodDOList) {
                ProductBO.MethodGroup methodGroup = new ProductBO.MethodGroup();
                if (methodGroups.size() >= 1) {
                    ProductBO.MethodGroup methodGroupEnd = methodGroups.get(methodGroups.size()-1);
                    if (methodGroupEnd.getName().equals(productMethodDO.getGroupName())) {
                        ProductBO.MethodGroup.Option option = toMethodOption(productMethodDO);
                        methodGroupEnd.getOptions().add(option);
                        option = null;
                        continue;
                    }
                }
                methodGroup.setName(productMethodDO.getGroupName());
                ProductBO.MethodGroup.Option option = toMethodOption(productMethodDO);
                methodGroup.getOptions().add(option);
                option = null;
                methodGroups.add(methodGroup);
                methodGroup = null;
            }
        }


        return productBO;
    }


    public static List<ProductBO> toProductBOS(List<ProductDO> productDOS){
        if (productDOS.isEmpty()){
            return null;
        }
        ArrayList<ProductBO> productBOS = Lists.newArrayList();
        for (ProductDO productDO : productDOS) {
            ProductBO productBO = new ProductBO();
            productBO.setEnabled(productDO.getEnabled());
            productBO.setName(productDO.getName());
            productBO.setUnitPrice(productDO.getUnitPrice());
            productBO.setMinSalesQuantity(productDO.getMinSalesQuantity());
            productBO.setIncreaseSalesQuantity(productDO.getIncreaseSalesQuantity());
            productBO.setUnitOfMeasure(productDO.getUnitOfMeasure());
            productBO.setDescription(productDO.getDescription());
            productBO.setId(productDO.getId());
            productBO.setTenantId(productDO.getTenantId());
            productBO.setVersion(productDO.getVersion());
            productBO.setAuditing(toAuditingBO(productDO));
            productBOS.add(productBO);
            productBO = null;
        }
        return productBOS;
    }

    private static AuditingBO toAuditingBO(ProductDO productDO){
        AuditingBO auditingBO = new AuditingBO();
        auditingBO.setCreatedAt(productDO.getCreatedAt());
        auditingBO.setCreatedBy(productDO.getCreatedBy());
        auditingBO.setLastModifiedAt(productDO.getLastModifiedAt());
        auditingBO.setLastModifiedBy(productDO.getLastModifiedBy());

        return auditingBO;
    }


    private static ProductBO.AccessoryGroup.Option toAccessoryOption(ProductAccessoryDO productAccessoryDO) {

        ProductBO.AccessoryGroup.Option option = new ProductBO.AccessoryGroup.Option();
        option.setUnitOfMeasure(productAccessoryDO.getUnitOfMeasure());
        option.setName(productAccessoryDO.getName());
        option.setId(productAccessoryDO.getId());
        option.setUnitPrice(productAccessoryDO.getUnitPrice());

        return option;
    }

    private static ProductBO.MethodGroup.Option toMethodOption(ProductMethodDO productMethodDO) {
        ProductBO.MethodGroup.Option option = new ProductBO.MethodGroup.Option();
        option.setId(productMethodDO.getId());
        option.setName(productMethodDO.getName());

        return option;
    }

}
