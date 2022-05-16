package com.meituan.catering.management.common.helper;

import com.meituan.catering.management.common.model.api.Status;
import com.meituan.catering.management.common.model.enumeration.IError;

/**
 * @author mac
 */
public class StatusHelper {
    private static final int SUCCESS_CODE = 0;

    private StatusHelper() {
    }

    public static boolean isSuccess(Status status) {
        return status != null && status.getCode() == 0;
    }

    public static boolean isFailure(Status status) {
        return status == null || status.getCode() != 0;
    }

    public static Status success() {
        return success("success");
    }

    public static Status success(String msg) {
        Status status = new Status();
        status.setCode(0);
        status.setMsg(msg);
        return status;
    }

    public static Status failure(IError iError) {
        return failure(iError.getCode(), iError.getMessage());
    }

    public static Status failure(int code, String message) {
        Status status = new Status();
        status.setCode(code);
        status.setMsg(message);
        return status;
    }
}
