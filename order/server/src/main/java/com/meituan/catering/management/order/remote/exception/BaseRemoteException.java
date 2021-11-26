package com.meituan.catering.management.order.remote.exception;

/**
 * 远程调用异常基类
 */
public class BaseRemoteException extends RuntimeException {

    public BaseRemoteException(String message) {
        super(message);
    }

    public BaseRemoteException(String message, Throwable cause) {
        super(message, cause);
    }
}
