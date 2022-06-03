package com.meituan.catering.management.order.remote.model.response;

import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import com.meituan.catering.management.common.model.api.thrift.ContactThriftModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.common.model.enumeration.ManagementTypeEnum;
import lombok.Data;

/**
 * 门店详情的RPC返回体
 */
@Data
public class ShopDetailRemoteResponse {
    public Long id;

    public Long tenantId;

    public final AuditingThriftModel auditing = new AuditingThriftModel();

    public String businessNo;

    public String name;

    public BusinessTypeEnum businessType;

    public ContactThriftModel contact = new ContactThriftModel();

    public ManagementTypeEnum managementType;

    public final OpeningHoursTimeRange openingHours = new OpeningHoursTimeRange();

    public String businessArea;

    public String comment;

    public Boolean enabled;

    public Integer version;


    @Data
    public final static class OpeningHoursTimeRange {

        public String openTime;

        public String closeTime;

    }
}