package com.meituan.catering.management.order.remote.exception;

import java.text.MessageFormat;

/**
 * 远程调用返回的列表为空的异常
 */
public class ResultListEmptyRemoteException extends BaseRemoteException {

    public static final String TEMPLATE = "远程调用请求[{0}]返回的结果列表为空";

    public static final String DEFAULT_ENTITY_NAME = "对象";

    public ResultListEmptyRemoteException() {
        super(MessageFormat.format(TEMPLATE, DEFAULT_ENTITY_NAME));
    }

    public ResultListEmptyRemoteException(String entityName) {
        super(MessageFormat.format(TEMPLATE, entityName));
    }
}
