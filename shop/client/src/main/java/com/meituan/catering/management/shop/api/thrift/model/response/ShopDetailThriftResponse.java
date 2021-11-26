package com.meituan.catering.management.shop.api.thrift.model.response;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.api.thrift.ContactThriftModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import lombok.Data;

/**
 * 门店详情的Thrift返回体
 */
@Data
@ThriftStruct
public final class ShopDetailThriftResponse {

    @ThriftField(10)
    public Long id;

    @ThriftField(20)
    public Long tenantId;

    @ThriftField(30)
    public AuditingThriftModel auditing = new AuditingThriftModel();

    @ThriftField(40)
    public String businessNo;

    @ThriftField(50)
    public String name;

    @ThriftField(60)
    public BusinessTypeEnum businessType;

    @ThriftField(70)
    public ContactThriftModel contact = new ContactThriftModel();

    @ThriftField(80)
    public ManagementTypeEnum managementType;

    @ThriftField(90)
    public OpeningHoursTimeRange openingHours = new OpeningHoursTimeRange();

    @ThriftField(100)
    public String businessArea;

    @ThriftField(110)
    public String comment;

    @ThriftField(120)
    public Boolean enabled;

    @ThriftField(130)
    public Integer version;

    /**
     * 营业时间段DTO
     *
     * @author dulinfeng
     */
    @Data
    @ThriftStruct("营业时间段")
    public static final class OpeningHoursTimeRange {

        @ThriftField(value = 10, name = "开始营业时间")
        public String openTime;

        @ThriftField(value = 20, name = "结束营业时间")
        public String closeTime;

    }
}