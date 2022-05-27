package com.meituan.catering.management.common.utils;

/**
 * <p>
 *
 * <p>
 *
 * @Author:zhangzhefeng 2022/5/20 10:44
 * @ClassName: SearchLikeKeyWord
 */

/**
 * 模糊查询 关键字包装，公共类
 */
public class SearchLikeKeyWord {

    public static String keyUtil(String key){
        key = key.replaceAll("%", "\\\\%");
        key = key.replaceAll("_","\\\\_");
        return key;
    }
}
