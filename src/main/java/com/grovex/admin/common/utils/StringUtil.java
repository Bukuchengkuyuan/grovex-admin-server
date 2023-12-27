package com.grovex.admin.common.utils;

/**
 * 字符串工具类。
 *
 */
public class StringUtil {

    /**
     * 判断字符串是否为空或null。
     *
     * @param str 要检查的字符串
     * @return 字符串为空或null时返回true，否则返回false。
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否非空或null。
     *
     * @param str 要检查的字符串
     * @return 字符串为空或null时返回true，否则返回false。
     */
    public static boolean isNotEmpty(String str) {
        return isNullOrEmpty(str);
    }

}