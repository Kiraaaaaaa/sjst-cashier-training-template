package com.meituan.catering.management.common.model.converter;

import com.meituan.catering.management.common.model.api.http.UserContextHttpRequest;
import com.meituan.catering.management.common.model.api.thrift.UserContextThriftRequest;
import com.meituan.catering.management.common.model.biz.UserContextBO;

/**
 * 从其他数据模型向用户上下文BO的转换器
 */
public abstract class UserContextBOConverter {

    public static UserContextBO fromHttpRequest(Long tenantId, Long userId) {
        if (tenantId == null || userId == null) {
            return null;
        }
        UserContextBO bo = new UserContextBO();
        bo.setTenantId(tenantId);
        bo.setUserId(userId);
        return bo;
    }

    public static UserContextBO fromHttpRequest(UserContextHttpRequest httpRequest) {
        if (httpRequest == null) {
            return null;
        }
        UserContextBO bo = new UserContextBO();
        bo.setTenantId(httpRequest.getTenantId());
        bo.setUserId(httpRequest.getUserId());
        return bo;
    }

    public static UserContextBO fromThriftRequest(UserContextThriftRequest thriftRequest) {
        if (thriftRequest == null) {
            return null;
        }
        UserContextBO bo = new UserContextBO();
        bo.setTenantId(thriftRequest.getTenantId());
        bo.setUserId(thriftRequest.getUserId());
        return bo;
    }
}
