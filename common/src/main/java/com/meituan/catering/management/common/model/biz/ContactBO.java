package com.meituan.catering.management.common.model.biz;

import lombok.Data;

/**
 * 联系方式
 */
@Data
public class ContactBO {

    private String telephone;

    private String cellphone;

    private String name;

    private String address;
}
