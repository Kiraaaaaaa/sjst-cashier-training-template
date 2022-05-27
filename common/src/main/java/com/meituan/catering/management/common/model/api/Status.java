package com.meituan.catering.management.common.model.api;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.Data;
import org.apache.http.HttpStatus;

/**
 * @author mac
 */
@ThriftStruct
public final class Status implements HttpStatus {

    private Integer code = 0;

    private String msg = "成功";

    public Status() {
    }

    public Status(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @ThriftField(
            value = 1,
            requiredness = com.facebook.swift.codec.ThriftField.Requiredness.REQUIRED
    )
    public Integer getCode() {
        return code;
    }

    @ThriftField
    public void setCode(Integer code) {
        this.code = code;
    }

    @ThriftField(
            value = 2,
            requiredness = com.facebook.swift.codec.ThriftField.Requiredness.REQUIRED
    )
    public String getMsg() {
        return msg;
    }

    @ThriftField
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public boolean isSuccess() {
        return this.code != null && this.code == 0;
    }

    public boolean isFailed() {
        return this.code == null || this.code != 0;
    }
}
