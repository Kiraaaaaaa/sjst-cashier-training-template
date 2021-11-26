package com.meituan.catering.management.common.model.converter;

import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.common.model.biz.UserContextBO;

/**
 * 从其他数据模型向用户上下文Thrift的转换器
 */
public abstract class UserContextThriftConverter {

    public static UserContextThriftRequest fromBO(UserContextBO bo) {
        if (bo == null) {
            return null;
        }
        UserContextThriftRequest thriftRequest = new UserContextThriftRequest();
        thriftRequest.setTenantId(bo.getTenantId());
        thriftRequest.setUserId(bo.getUserId());
        return thriftRequest;
    }
}
