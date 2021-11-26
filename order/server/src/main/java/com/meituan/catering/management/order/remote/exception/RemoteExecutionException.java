package com.meituan.catering.management.order.remote.exception;

import java.text.MessageFormat;

/**
 * 远程调用发生通用性错误异常
 */
public class RemoteExecutionException extends BaseRemoteException {

    public static final String TEMPLATE = "远程调用请求请求[{0}]失败";

    public static final String DEFAULT_ENTITY_NAME = "对象";

    public RemoteExecutionException() {
        super(MessageFormat.format(TEMPLATE, DEFAULT_ENTITY_NAME));
    }

    public RemoteExecutionException(String entityName) {
        super(MessageFormat.format(TEMPLATE, entityName));
    }
}
