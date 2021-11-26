package com.meituan.catering.management.shop.api.http.model.response;

import com.meituan.catering.management.common.model.api.http.AuditingHttpModel;
import com.meituan.catering.management.common.model.api.http.ContactHttpModel;
import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 门店分页信息Http返回体
 */
@Data
@ApiModel("门店分页信息Http返回体")
public class ShopPageHttpResponse {

    @ApiModelProperty("分页码")
    private Integer pageIndex;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("总条目数")
    private Integer totalCount;

    @ApiModelProperty("总页码数")
    private Integer totalPageCount;

    @ApiModelProperty("当前页的门店信息列表")
    private final List<Record> records = new LinkedList<>();

    @Data
    @ApiModel("门店分页信息项")
    public static class Record {

        @ApiModelProperty("物理ID")
        private Long id;

        @ApiModelProperty("租户ID")
        private Long tenantId;

        @ApiModelProperty("版本号")
        private Integer version;

        @ApiModelProperty("审计信息")
        private final AuditingHttpModel auditing = new AuditingHttpModel();

        @ApiModelProperty("业务号")
        private String businessNo;

        @ApiModelProperty("名称")
        private String name;

        @ApiModelProperty("管理类型")
        private ManagementTypeEnum managementType;

        @ApiModelProperty("业态类型")
        private BusinessTypeEnum businessType;

        @ApiModelProperty("联系方式")
        private final ContactHttpModel contact = new ContactHttpModel();

        @ApiModelProperty("营业时间段")
        private final OpeningHoursTimeRange openingHours = new OpeningHoursTimeRange();

        @ApiModelProperty("营业面积")
        private String businessArea;

        @ApiModelProperty("备注")
        private String comment;

        @ApiModelProperty("是否开放营业")
        private Boolean enabled;

        @Data
        @ApiModel("营业时间段")
        public static class OpeningHoursTimeRange {

            private String openTime;

            private String closeTime;

        }

    }
}