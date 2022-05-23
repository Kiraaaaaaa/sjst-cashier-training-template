package com.meituan.catering.management.product.dao.converter;

import com.meituan.catering.management.product.biz.model.request.CreateProductBizRequest;
import com.meituan.catering.management.product.biz.model.request.UpdateProductBizRequest;
import com.meituan.catering.management.product.dao.model.ProductDO;

import java.util.Date;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 13:56
 * @ClassName: ProductDOConverter
 */
public class ProductDOConverter {

    public static ProductDO toProductDO(Long userId,Long tenantId,Long id,UpdateProductBizRequest request){
        if (request == null){
            return null;
        }
        ProductDO productDO = new ProductDO();
        productDO.setId(id);
        productDO.setName(request.getName());
        productDO.setUnitPrice(request.getUnitPrice());
        productDO.setUnitOfMeasure(request.getUnitOfMeasure());
        productDO.setMinSalesQuantity(request.getMinSalesQuantity());
        productDO.setIncreaseSalesQuantity(request.getIncreaseSalesQuantity());
        productDO.setDescription(request.getDescription());
        productDO.setVersion(request.getVersion());
        productDO.setLastModifiedBy(userId);
        productDO.setLastModifiedAt(new Date());
        productDO.setTenantId(tenantId);

        return productDO;
    }

    public static ProductDO toProductDO(Long userId, Long tenantId, CreateProductBizRequest request){


        if (request == null){
            return null;
        }

        ProductDO productDO = new ProductDO();
        productDO.setName(request.getName());
        productDO.setUnitPrice(request.getUnitPrice());
        productDO.setUnitOfMeasure(request.getUnitOfMeasure());
        productDO.setMinSalesQuantity(request.getMinSalesQuantity());
        productDO.setIncreaseSalesQuantity(request.getIncreaseSalesQuantity());
        productDO.setDescription(request.getDescription());
        productDO.setLastModifiedBy(userId);
        productDO.setLastModifiedAt(new Date());
        productDO.setTenantId(tenantId);
        productDO.setCreatedAt(new Date());
        productDO.setCreatedBy(userId);
        productDO.setEnabled(true);

        return productDO;
    }

}
