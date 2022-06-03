package com.meituan.catering.management.product.biz.model.converter;

import com.google.common.collect.Lists;
import com.meituan.catering.management.common.model.biz.AuditingBO;
import com.meituan.catering.management.product.biz.model.ProductBO;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import com.meituan.catering.management.product.dao.model.ProductDO;
import com.meituan.catering.management.product.dao.model.ProductMethodDO;
import io.lettuce.core.XAddArgs;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

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

        productBO.getAccessoryGroups().addAll(Objects.requireNonNull(buildAccessory(productAccessoryDOList)));
        productBO.getMethodGroups().addAll(Objects.requireNonNull(buildMethod(productMethodDOList)));

        return productBO;
    }

    public static List<ProductBO> toProductBOS(List<ProductDO> productDOS, List<ProductAccessoryDO> accessory, List<ProductMethodDO> method) {
        if (CollectionUtils.isEmpty(productDOS)) {
            return null;
        }
        Collection<ProductAccessoryDO> accessoryDOS = CollectionUtils.emptyIfNull(accessory);
        Collection<ProductMethodDO> methodDOS = CollectionUtils.emptyIfNull(method);
        List<ProductBO> productBOS = Lists.newArrayList();
        for (ProductDO productDO : productDOS) {

            List<ProductAccessoryDO> accessoryDOList = Lists.newArrayList();

            for (ProductAccessoryDO accessoryDO : accessoryDOS) {
                if (productDO.getId().equals(accessoryDO.getProductId())){
                    accessoryDOList.add(accessoryDO);
                }
            }
            List<ProductMethodDO> methodDOList = Lists.newArrayList();
            for (ProductMethodDO methodDO : methodDOS) {
                if (productDO.getId().equals(methodDO.getProductId())){
                    methodDOList.add(methodDO);
                }
            }
            productBOS.add(toProductBO(productDO,methodDOList,accessoryDOList));
            accessoryDOList = null;
            methodDOList = null;
        }
        return productBOS;
    }

    private static AuditingBO toAuditingBO(ProductDO productDO) {
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

    private static List<ProductBO.AccessoryGroup> buildAccessory(List<ProductAccessoryDO> accessoryDOS) {
        ArrayList<ProductBO.AccessoryGroup> list = Lists.newArrayList();
        for (ProductAccessoryDO accessoryDO : accessoryDOS) {
            ProductBO.AccessoryGroup accessoryGroup = new ProductBO.AccessoryGroup();
            if (list.size() >= 1) {
                ProductBO.AccessoryGroup accessoryLast = list.get(list.size() - 1);
                if (accessoryLast.getName().equals(accessoryDO.getGroupName())) {
                    ProductBO.AccessoryGroup.Option option = toAccessoryOption(accessoryDO);
                    accessoryLast.getOptions().add(option);
                    option = null;
                    continue;
                }
            }
            accessoryGroup.setName(accessoryDO.getGroupName());
            ProductBO.AccessoryGroup.Option option = toAccessoryOption(accessoryDO);
            accessoryGroup.getOptions().add(option);
            option = null;
            list.add(accessoryGroup);
            accessoryGroup = null;
        }
        return list;
    }

    private static List<ProductBO.MethodGroup> buildMethod(List<ProductMethodDO> methodDOS) {
        ArrayList<ProductBO.MethodGroup> list = Lists.newArrayList();
        for (ProductMethodDO methodDO : methodDOS) {
            ProductBO.MethodGroup methodGroup = new ProductBO.MethodGroup();
            if (list.size() >= 1) {
                ProductBO.MethodGroup methodLast = list.get(list.size() - 1);
                if (methodLast.getName().equals(methodDO.getGroupName())) {
                    ProductBO.MethodGroup.Option option = toMethodOption(methodDO);
                    methodLast.getOptions().add(option);
                    option = null;
                    continue;
                }
            }
            methodGroup.setName(methodDO.getGroupName());
            ProductBO.MethodGroup.Option option = toMethodOption(methodDO);
            methodGroup.getOptions().add(option);
            option = null;
            list.add(methodGroup);
            methodGroup = null;
        }
        return list;
    }
}
