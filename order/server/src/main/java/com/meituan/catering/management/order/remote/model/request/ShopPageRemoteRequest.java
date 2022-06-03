package com.meituan.catering.management.order.remote.model.request;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.common.model.enumeration.ManagementTypeEnum;
import com.meituan.catering.management.order.api.http.model.request.SearchCateringOrderHttpRequest;
import lombok.Data;
import net.jcip.annotations.NotThreadSafe;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/31 15:08
 * @ClassName: ShopPageRemoteRequest
 */
@Data
@ThriftStruct
public class ShopPageRemoteRequest {

    public Integer pageIndex;

    public Integer pageSize;

    public final Condition condition = new Condition();

    public final List<SortField> sortFields = new LinkedList<>();

    @Data
    @ThriftStruct
    public static class Condition{

        public String keyword;

        public Set<ManagementTypeEnum> managementTypes = new LinkedHashSet<>();

        public Set<BusinessTypeEnum> businessTypes = new LinkedHashSet<>();

        public Boolean enabled;
    }

    @Data
    @ThriftStruct
    public static class SortField {

        public String field;

        public Boolean asc;
    }
}
