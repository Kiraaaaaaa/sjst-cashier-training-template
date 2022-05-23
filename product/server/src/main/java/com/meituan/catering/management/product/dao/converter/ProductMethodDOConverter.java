package com.meituan.catering.management.product.dao.converter;

import com.meituan.catering.management.product.biz.model.request.SaveProductBizRequest;
import com.meituan.catering.management.product.dao.model.ProductAccessoryDO;
import com.meituan.catering.management.product.dao.model.ProductMethodDO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/22 13:57
 * @ClassName: ProductMethodDOConverter
 */
public class ProductMethodDOConverter {
    public static List<ProductMethodDO> toProductMethodDO(Long tenantId, Long productId, SaveProductBizRequest request){

        List<ProductMethodDO> productMethodDOS = new ArrayList<>();
        List<SaveProductBizRequest.MethodGroup> methodGroups = request.getMethodGroups();
        for (SaveProductBizRequest.MethodGroup methodGroup : methodGroups) {
            List<SaveProductBizRequest.MethodGroup.Option> options = methodGroup.getOptions();
            for (SaveProductBizRequest.MethodGroup.Option option : options) {
                ProductMethodDO productMethodDO = new ProductMethodDO();
                productMethodDO.setProductId(productId);
                productMethodDO.setTenantId(tenantId);
                productMethodDO.setName(option.getName());
                productMethodDO.setGroupName(methodGroup.getName());
                productMethodDOS.add(productMethodDO);
                productMethodDO = null;
            }
        }
        return productMethodDOS;
    }

}
