package com.meituan.catering.management.product.biz.validator;

import com.meituan.catering.management.common.exception.BizException;
import com.meituan.catering.management.common.model.enumeration.ErrorCode;
import com.meituan.catering.management.product.api.http.model.request.SearchProductHttpRequest;
import com.meituan.catering.management.product.dao.mapper.ProductMapper;
import com.meituan.catering.management.product.dao.model.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public void versionValid(Long tenantId, Long userId, Long id, Integer version) {
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
        ProductDO productDO = productMapper.findById(tenantId, id);
        if (productDO == null) {
            throw new BizException(ErrorCode.UPDATE_ERROR);
        }
        Integer versionDO = productDO.getVersion();
        if (!versionDO.equals(version)) {
            throw new BizException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void sqlValid(Long tenantId, Long userId, SearchProductHttpRequest request) {
        Pattern sqlPattern = Pattern.compile(
                "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete"
                        + "|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)");
        if (userId < 0 || tenantId < 0) {
            throw new BizException(ErrorCode.PARAM_ERROR);
        }
        List<SearchProductHttpRequest.SortField> sortFields = request.getSortFields();
        if (sortFields != null && sortFields.size() != 0) {
            for (SearchProductHttpRequest.SortField sortField : sortFields) {
                Matcher matcher = sqlPattern.matcher(sortField.toString());
                if (matcher.find()){
                    throw new BizException(ErrorCode.ILLEGAL_CODE_ERROR);
                }
            }
        }

    }
}
