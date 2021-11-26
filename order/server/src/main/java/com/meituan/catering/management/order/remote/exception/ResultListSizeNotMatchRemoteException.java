package com.meituan.catering.management.order.remote.exception;

import java.text.MessageFormat;

/**
 * 远程调用返回的结果列表数量和请求的数量不相等的异常
 */
public class ResultListSizeNotMatchRemoteException extends BaseRemoteException {

    public static final String TEMPLATE = "远程调用请求[{0}]返回的结果列表数量和请求的数量不相等";

    public static final String DEFAULT_ENTITY_NAME = "对象";

    public ResultListSizeNotMatchRemoteException() {
        super(MessageFormat.format(TEMPLATE, DEFAULT_ENTITY_NAME));
    }

    public ResultListSizeNotMatchRemoteException(String entityName) {
        super(MessageFormat.format(TEMPLATE, entityName));
    }
}
