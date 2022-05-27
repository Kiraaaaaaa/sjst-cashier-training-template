package com.meituan.catering.management.common.model.enumeration;


import org.springframework.http.HttpStatus;

/**
 * @author mac
 */
public interface IError {
    /**
     * @return
     */
    int getCode();

    /**
     * @return
     */
    String getMessage();
}
