package com.meituan.catering.management.common.exception;

import com.meituan.catering.management.common.model.enumeration.IError;

/**
 * @author mac
 */
public class BizException extends Exception{
    private IError errorCode;

    public BizException(IError errorCode) {
        super(errorCode.getMessage());
        this.setErrorCode(errorCode);
    }

    public BizException(Integer code, String msg) {
        super(msg);
        this.setErrorCode(new BizException.BaseErrorCode(code, msg));
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

        @Override
        public int getCode() {
            return this.errorCode;
        }

        @Override
        public String getMessage() {
            return this.errorMsg;
        }
    }
}
