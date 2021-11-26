package com.meituan.catering.management.shop.api.http.model.request;

import com.meituan.catering.management.common.model.enumeration.BusinessTypeEnum;
import com.meituan.catering.management.shop.api.http.model.enumeration.ManagementTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;

/**
 * 搜索门店的Http请求体
 */
@Data
@ApiModel("搜索门店的Http请求体")
public class SearchShopHttpRequest {

    @NotNull
    @Min(1)
    @Max(1000)
    @ApiModelProperty("分页码，从1开始计算，默认为1")
    private Integer pageIndex = 1;

    @NotNull
    @Min(1)
    @Max(100)
    @ApiModelProperty("分页大小，默认为10")
    private Integer pageSize = 10;

    @Valid
    @ApiModelProperty("搜索条件")
    private final Condition condition = new Condition();

    @Data
    @ApiModel("搜索门店的搜索条件")
    public static class Condition {

        @Length(max = 50)
        @ApiModelProperty("搜索关键字，包括：门店名、门店联系方式（座机，手机，地址，联系人名）,进行模糊搜索")
        private String keyword;

        @ApiModelProperty("支持搜索的门店的管理类型列表")
        private Set<ManagementTypeEnum> managementTypes = new LinkedHashSet<>();

        @ApiModelProperty("支持搜索的门店的业态类型列表")
        private Set<BusinessTypeEnum> businessTypes = new LinkedHashSet<>();

        @ApiModelProperty("支持搜索的门店开放关闭状态")
        private Boolean enabled;

    }

    @Valid
    @ApiModelProperty("排序字段列表")
    private final List<SortField> sortFields = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("排序字段请求")
    public static class SortField {

        @NotBlank
        @Length(max = 50)
        @ApiModelProperty("排序字段名")
        private String field;

        @NotNull
        @ApiModelProperty("排序方向：true：升序，false：降序。默认为降序")
        private Boolean asc = FALSE;

    }
}