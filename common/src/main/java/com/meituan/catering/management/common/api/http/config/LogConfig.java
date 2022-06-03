package com.meituan.catering.management.common.api.http.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/23 16:21
 * @ClassName: LogConfig
 */
@Component
public class LogConfig {

    public void writeLog(Exception e,Class name){
        final Logger LOG = LoggerFactory.getLogger(name);

        StackTraceElement stackTrace = e.getStackTrace()[0];
        String className = stackTrace.getClassName();
        String fileName = stackTrace.getFileName();
        int lineNumber = stackTrace.getLineNumber();
        String methodName = stackTrace.getMethodName();
        String message = e.getMessage();
        LOG.error("类名:{},文件名:{},行数:{},方法名:{}",className,fileName,lineNumber,methodName);

    }

}
