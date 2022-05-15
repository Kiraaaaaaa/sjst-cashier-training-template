package com.meituan.catering.management.common.model.enumeration;

/**
 * @author mac
 */

public enum ErrorCode implements IError {
    PARAM_ERROR(630001, "参数错误"),
    SYSTEM_ERROR(630002, "系统异常"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" + "code=" + code + ", message='" + message + '\'' + '}';
    }

}
