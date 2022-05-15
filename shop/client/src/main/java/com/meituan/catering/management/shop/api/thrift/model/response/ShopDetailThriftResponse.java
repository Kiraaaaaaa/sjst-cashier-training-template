package com.meituan.catering.management.shop.api.thrift.model.response;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.BaseResponse;
import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.api.thrift.ContactThriftModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import com.meituan.catering.management.shop.api.thrift.model.dto.ShopDetailThriftDTO;
import lombok.*;

/**
 * 门店详情的Thrift返回体
 * @author mac
 */



@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@ThriftStruct
public final class ShopDetailThriftResponse extends BaseResponse<ShopDetailThriftDTO> {

}