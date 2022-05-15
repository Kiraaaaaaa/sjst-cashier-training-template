package com.meituan.catering.management.common.exception;

import com.meituan.catering.management.common.model.enumeration.IError;

/**
 * @author mac
 */
public class BizRuntimeException extends RuntimeException{
    private IError errorCode;

    public BizRuntimeException(IError errorCode) {
        super(errorCode.getMessage());
        this.setErrorCode(errorCode);
    }

    public BizRuntimeException(Integer code, String msg) {
        super(msg);
        this.setErrorCode(new BizRuntimeException.BaseErrorCode(code, msg));
    }

    public IError getErrorCode() {
        return this.errorCode;
    }

    private void setErrorCode(IError errorCode) {
        this.errorCode = errorCode;
    }

    class BaseErrorCode implements IError {
        private Integer errorCode;
        private String errorMsg;

        BaseErrorCode(Integer errorCode, String errorMsg) {
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
        }

        public int getCode() {
            return this.errorCode;
        }

        public String getMessage() {
            return this.errorMsg;
        }
    }
}
