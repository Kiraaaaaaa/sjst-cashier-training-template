package com.meituan.catering.management.product.api.thrift.model.response;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import com.meituan.catering.management.common.model.api.thrift.AuditingThriftModel;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 商品详情的Thrift返回体定义
 */
@Data
@ThriftStruct
public final class ProductDetailThriftResponse {

    @ThriftField(value = 10, name = "物理ID")
    public Long id;

    @ThriftField(value = 20, name = "租户ID")
    public Long tenantId;

    @ThriftField(value = 30, name = "版本号")
    public Integer version;

    @ThriftField(value = 40, name = "审计信息")
    public AuditingThriftModel auditing = new AuditingThriftModel();

    @ThriftField(value = 50, name = "名称")
    public String name;

    @ThriftField(value = 60, name = "单价")
    public Double unitPrice;

    @ThriftField(value = 70, name = "计量单位")
    public String unitOfMeasure;

    @ThriftField(value = 80, name = "起售量")
    public Double minSalesQuantity;

    @ThriftField(value = 90, name = "增售量")
    public Double increaseSalesQuantity;

    @ThriftField(value = 100, name = "描述")
    public String description;

    @ThriftField(value = 110, name = "上架状态")
    public Boolean enabled;

    @ThriftField(value = 1100, name = "做法组")
    public List<MethodGroup> methodGroups = new LinkedList<>();

    @ThriftField(value = 1200, name = "加料组")
    public List<AccessoryGroup> accessoryGroups = new LinkedList<>();

    /**
     * 商品做法组Thrift返回体定义
     */
    @Data
    @ThriftStruct
    public static final class MethodGroup {

        @ThriftField(value = 10, name = "做法名称")
        public String name;

        @ThriftField(value = 20, name = "做法项")
        public List<Option> options = new LinkedList<>();

        /**
         * 商品做法组选项Thrift返回体定义
         */
        @Data
        @ThriftStruct
        public static final class Option {

            @ThriftField(value = 10, name = "物理ID")
            public Long id;

            @ThriftField(value = 20, name = "做法名称")
            public String name;

        }
    }


    /**
     * 商品加料组Thrift返回体定义
     */
    @Data
    @ThriftStruct
    public static final class AccessoryGroup {

        @ThriftField(value = 10, name = "加料名称")
        public String name;

        @ThriftField(value = 20, name = "加料项")
        public List<Option> options = new LinkedList<>();

        /**
         * 商品加料BO定义
         */
        @Data
        @ThriftStruct
        public static final class Option {

            @ThriftField(value = 10, name = "物理ID")
            public Long id;

            @ThriftField(value = 20, name = "做法名称")
            public String name;

            @ThriftField(value = 30, name = "标准单价")
            public Double unitPrice;

            @ThriftField(value = 40, name = "标准计量单位")
            public String unitOfMeasure;

        }
    }
}