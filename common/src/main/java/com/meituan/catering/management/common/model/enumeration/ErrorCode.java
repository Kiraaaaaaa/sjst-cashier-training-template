package com.meituan.catering.management.common.model.enumeration;

/**
 * @author mac
 */

public enum ErrorCode implements IError {
    PARAM_ERROR(630001, "参数错误"),
    SYSTEM_ERROR(630002, "系统异常"),
    UPDATE_ERROR(630003, "更新数据失败"),
    REPEAT_ERROR(630004, "请勿多次提交"),
    OPEN_ERROR(630005, "打开失败"),
    CLOSE_ERROR(630006, "关闭失败"),
    ILLEGAL_CODE_ERROR(630007, "非法字符"),
    INSET_ERROR(630008, "插入失败"),
    PREPARE_ERROR(630009,"制作失败"),
    PRODUCE_ERROR(630010,"出餐失败"),
    BILL_ERROR(630011,"支付失败"),
    ADJUST_ERROR(630012,"加退菜失败");

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
