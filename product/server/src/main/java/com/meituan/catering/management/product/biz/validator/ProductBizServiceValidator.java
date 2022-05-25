package com.meituan.catering.management.product.biz.validator;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.product.dao.mapper.ProductMapper;
import com.meituan.catering.management.product.dao.model.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/24 15:01
 * @ClassName: ProductBizServiceValidator
 */
@Component
public class ProductBizServiceValidator {
    @Autowired
    private ProductMapper productMapper;

    public void baseValid(Long tenantId, Long userId) throws BizException {
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
    }

    public void versionValid(Long tenantId, Long userId, Long id, Integer version){
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
        ProductDO productDO = productMapper.findById(tenantId, id);
        if (productDO == null){
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        Integer versionDO = productDO.getVersion();
        if (!versionDO.equals(version)){
            throw new BizException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
