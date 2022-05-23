package com.meituan.catering.management.product.dao.converter;

import com.meituan.catering.management.product.biz.model.request.SaveProductBizRequest;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 13:57
 * @ClassName: ProductAccessoryDOCon
 *
 */
public class ProductAccessoryDOConverter {

    public static List<ProductAccessoryDO> toProductAccessoryDO(Long tenantId, Long productId, SaveProductBizRequest request){

        List<ProductAccessoryDO> productAccessoryDOS = new ArrayList<>();
        List<SaveProductBizRequest.AccessoryGroup> accessoryGroups = request.getAccessoryGroups();
        for (SaveProductBizRequest.AccessoryGroup accessoryGroup : accessoryGroups) {
            List<SaveProductBizRequest.AccessoryGroup.Option> options = accessoryGroup.getOptions();
            for (SaveProductBizRequest.AccessoryGroup.Option option : options) {
                ProductAccessoryDO productAccessoryDO = new ProductAccessoryDO();
                productAccessoryDO.setProductId(productId);
                productAccessoryDO.setTenantId(tenantId);
                productAccessoryDO.setName(option.getName());
                productAccessoryDO.setGroupName(accessoryGroup.getName());
                productAccessoryDO.setUnitPrice(option.getUnitPrice());
                productAccessoryDO.setUnitOfMeasure(option.getUnitOfMeasure());
                productAccessoryDOS.add(productAccessoryDO);
                productAccessoryDO = null;
            }
        }
        return productAccessoryDOS;
    }
}
