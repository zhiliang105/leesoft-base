package com.leeframework.common.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 字符串处理工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:45:35
 */
public class StringUtil {

    public static final String COMMA = ",";
    public static final String SEPARATOR = "/";
    public static final String SEPARATOR_WINDOWS = "\\";

    /**
     * 首字母转小写
     * @datetime 2018年6月10日 下午11:18:44
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     * @datetime 2018年6月10日 下午11:19:08
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 字符串转换,如果为空值,则返回空串,否则返回本省
     * @author lee
     * @date 2016年6月3日 下午2:00:12
     */
    public static String convertNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * 判断字符串中是否包含文本
     * @datetime 2017年12月6日 下午1:49:23
     */
    public static boolean hasText(String str) {
        if (isNotEmpty(str)) {
            CharSequence cstr = (CharSequence) str;
            int strLen = cstr.length();
            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空,判断条件：str == null || str.trim().equals("")
     * @author lee
     * @date 2016年6月3日 下午1:45:21
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().equals(""));
    }

    /**
     * 判断字符串不为空,并且不为空串
     * @datetime 2017年12月3日 上午3:47:28
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().equals("");
    }

    /**
     * 将特定的字符串分割成集合<code>ArrayList<String><code>对象
     * @author lee
     * @date 2016年6月8日 下午11:52:44
     * @param str 需要分割的字符串,用{@link #COMMA}作为分割标识符
     * @return <code>ArrayList<String><code>对象
     */
    public static List<String> split(String str) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        String[] strs = str.split(COMMA);
        return Arrays.asList(strs);
    }

}
