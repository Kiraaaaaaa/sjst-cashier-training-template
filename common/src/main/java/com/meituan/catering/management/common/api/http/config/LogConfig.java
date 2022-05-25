package com.meituan.catering.management.common.api.http.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/23 16:21
 * @ClassName: LogConfig
 */
public class LogConfig {

    public static void writeLog(Exception e,Class name){
        final Logger LOG = LoggerFactory.getLogger(name);

        StackTraceElement stackTrace = e.getStackTrace()[0];
        String className = stackTrace.getClassName();
        String fileName = stackTrace.getFileName();
        int lineNumber = stackTrace.getLineNumber();
        String methodName = stackTrace.getMethodName();

        LOG.error("类名:{},文件名:{},行数:{},办法名:{}",className,fileName,lineNumber,methodName);

    }

}
