package com.meituan.catering.management.order.remote.model.response;

import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import lombok.Data;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品详情的RPC返回体
 */
@Data
public class ProductDetailRemoteResponse {

    public Long id;

    public Long tenantId;

    public Integer version;

    public final AuditingThriftModel auditing = new AuditingThriftModel();

    public String name;

    public Double unitPrice;

    public String unitOfMeasure;

    public Double minSalesQuantity;

    public Double increaseSalesQuantity;

    public String description;

    public Boolean enabled;

    public final List<MethodGroup> methodGroups = new LinkedList<>();

    public final List<AccessoryGroup> accessoryGroups = new LinkedList<>();

    /**
     * 商品做法组Thrift返回体定义
     */
    @Data
    public static final class MethodGroup {

        public String name;

        public final List<Option> options = new LinkedList<>();

        /**
         * 商品做法组选项Thrift返回体定义
         */
        @Data
        public static final class Option {

            public Long id;

            public String name;

        }
    }


    /**
     * 商品加料组Thrift返回体定义
     */
    @Data
    public static final class AccessoryGroup {

        public String name;

        public final List<Option> options = new LinkedList<>();

        /**
         * 商品加料BO定义
         */
        @Data
        public static final class Option {

            public Long id;

            public String name;

            public Double unitPrice;

            public String unitOfMeasure;

        }
    }
}