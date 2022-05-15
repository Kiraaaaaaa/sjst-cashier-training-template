package com.meituan.catering.management.common.model.api;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;
import lombok.Data;

/**
 * @author mac
 */
@Data
@ThriftStruct
public class BaseResponse<T> {
    @ThriftField(1)
    private Status status;

    @ThriftField(2)
    private T data;

    public BaseResponse(Status status,T data) {
        this.status=status;
        this.data=data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
