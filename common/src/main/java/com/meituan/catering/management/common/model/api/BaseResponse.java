package com.meituan.catering.management.common.model.api;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.*;

/**
 * @author mac
 */

@ThriftStruct
public class BaseResponse<T> {
    private Status status;
    private T data;

    @ThriftField(
            value = 1,
            requiredness = com.facebook.swift.codec.ThriftField.Requiredness.REQUIRED
    )
    public Status getStatus() {
        return status;
    }

    @ThriftField
    public void setStatus(Status status) {
        this.status = status;
    }

    @ThriftField(
            value = 2,
            requiredness = com.facebook.swift.codec.ThriftField.Requiredness.OPTIONAL
    )
    public T getData() {
        return data;
    }

    @ThriftField
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
