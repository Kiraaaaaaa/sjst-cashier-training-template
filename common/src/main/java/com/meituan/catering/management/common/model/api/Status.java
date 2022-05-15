package com.meituan.catering.management.common.model.api;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.Data;

/**
 * @author mac
 */
@Data
@ThriftStruct
public class Status {

    @ThriftField(1)
    private Integer code = 0;

    @ThriftField(2)
    private String msg = "成功";

    public Status() {
    }

    public Status(Integer code, String msg) {
        this.code = code;
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
