package com.meituan.catering.management.order.remote.model.response;

import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.common.model.enumeration.ManagementTypeEnum;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/31 14:58
 * @ClassName: ShopPageRemoteResponse
 */
@Data
@ThriftStruct
public class ShopPageRemoteResponse {
    public Integer pageIndex;

    public Integer pageSize;

    public Integer totalCount;

    public Integer totalPageCount;

    public final List<Record> records = new LinkedList<>();

    @Data
    @ThriftStruct
    public static class Record {

        public Long id;

        public Long tenantId;

        public Integer version;

        public final AuditingHttpModel auditing = new AuditingHttpModel();

        public String businessNo;

        public String name;

        public ManagementTypeEnum managementType;

        public BusinessTypeEnum businessType;

        public final ContactHttpModel contact = new ContactHttpModel();

        public final OpeningHoursTimeRange openingHours = new OpeningHoursTimeRange();

        public String businessArea;

        public String comment;

        public Boolean enabled;

        @Data
        @ThriftStruct
        public final static class OpeningHoursTimeRange {

            public String openTime;

            public String closeTime;

        }

    }
}
