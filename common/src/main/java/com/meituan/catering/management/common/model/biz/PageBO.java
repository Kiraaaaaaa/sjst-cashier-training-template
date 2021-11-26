package com.meituan.catering.management.common.model.biz;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 业务对象的分页信息
 */
@Data
public class PageBO<T> {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer totalPageCount;

    private Integer totalCount;

    private final List<T> records = new LinkedList<>();

}
