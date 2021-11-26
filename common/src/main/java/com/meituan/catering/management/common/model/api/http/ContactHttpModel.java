package com.meituan.catering.management.common.model.api.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 联系方式DTO
 */
@Data
@ApiModel("联系方式的Http请求体")
public class ContactHttpModel {

    @ApiModelProperty("电话号码")
    @Length(min = 7, max = 20)
    private String telephone;

    @ApiModelProperty("手机号码")
    @Length(min = 11, max = 20)
    private String cellphone;

    @ApiModelProperty("姓名")
    @Length(min = 2, max = 10)
    private String name;

    @ApiModelProperty("地址")
    @Length(min = 1, max = 50)
    private String address;
}
