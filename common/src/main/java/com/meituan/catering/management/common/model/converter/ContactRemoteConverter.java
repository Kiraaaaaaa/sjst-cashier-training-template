package com.meituan.catering.management.common.model.converter;

import com.meituan.catering.management.common.model.api.thrift.ContactThriftModel;
import com.meituan.catering.management.common.model.biz.ContactBO;

/**
 * 从远程调用的Thrift模型向BO的转换器 - 联系方式
 */
public abstract class ContactRemoteConverter {

    public static ContactBO fromRemoteResponse(ContactThriftModel thriftModel) {
        if (thriftModel == null) {
            return null;
        }
        ContactBO bo = new ContactBO();
        bo.setName(thriftModel.getName());
        bo.setAddress(thriftModel.getAddress());
        bo.setTelephone(thriftModel.getTelephone());
        bo.setCellphone(thriftModel.getCellphone());
        return bo;
    }
}
