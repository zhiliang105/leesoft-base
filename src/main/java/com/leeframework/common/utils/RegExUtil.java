package com.leeframework.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 正则表达式工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月10日 下午11:35:27
 */
public class RegExUtil {

    /**
     * 要求大小写都匹配正则表达式
     * @param pattern 正则表达式模式
     * @param str 要匹配的字串
     */
    public static final boolean ereg(String pattern, String str) throws PatternSyntaxException {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            return m.find();
        } catch (PatternSyntaxException e) {
            throw e;
        }
    }

    /**
     * 匹配且替换字串
     * @param pattern 正则表达式模式
     * @param newstr 要替换匹配到的新字串
     * @param str 原始字串
     * @return 匹配后的字符串
     */

    public static final String eregReplace(String pattern, String newstr, String str) throws PatternSyntaxException {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            return m.replaceAll(newstr);
        } catch (PatternSyntaxException e) {
            throw e;
        }
    }

    /**
     * 转义正则表达式字符(之所以需要将\和$字符用escapeDollarBackslash方法的方式是因为用repalceAll是不行的，简单的试试"$".repalceAll("\\$","\\\\$")你会发现这个调用会导致数组越界错误)
     * @param pattern为正则表达式模式
     * @param str 原始字串
     */
    public static String escapeDollarBackslash(String original) {
        StringBuffer buffer = new StringBuffer(original.length());
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c == '\\' || c == '$') {
                buffer.append("\\").append(c);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * 提取指定字串的函数 功能主要是把查找到的元素
     * @param pattern为正则表达式模式
     * @param str 原始字串
     */
    public static final String fetchStr(String pattern, String str) {
        String returnValue = null;
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            while (m.find()) {
                returnValue = m.group();
            }
            return returnValue;
        } catch (PatternSyntaxException e) {
            return returnValue;
        }
    }

    /**
     * 判断是否为邮箱
     * @datetime 2018年6月11日 上午12:07:59
     */
    public static boolean isEmail(String str) {
        String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return matches(str, reg);
    }

    /**
     * 判断是否为身份证
     * @datetime 2018年6月11日 上午12:07:59
     */
    public static boolean isIdCard(String str) {
        String reg = "(^\\d{18}$)|(^\\d{15}$)";
        return matches(str, reg);
    }

    /**
     * 判断是否为js函数
     * @datetime 2018年6月10日 下午11:39:41
     */
    public static boolean isJSFunction(String str) {
        String reg = "function(\\s+\\w+\\(|\\().*\\)\\s*\\{.*\\}";
        return matches(str, reg);
    }

    /**
     * 判断是否为手机号码
     * @datetime 2018年6月11日 上午12:02:57
     */
    public static boolean isPhone(String str) {
        String reg = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return matches(str, reg);
    }

    /**
     * 正则匹配
     * @datetime 2018年6月11日 上午12:03:38
     */
    public static boolean matches(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 匹配所有符合模式要求的字串并加到字符串数组中
     * @param pattern为正则表达式模式
     * @param str 原始字串
     */
    public static final String[] regMatchAll2Array(String pattern, String str) throws PatternSyntaxException {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            String[] array = new String[m.groupCount()];
            int i = 0;
            while (m.find()) {
                array[i] = m.group();
                i++;
            }
            return array;
        } catch (PatternSyntaxException e) {
            throw e;
        }
    }

    /**
     * 匹配所有符合模式要求的字串
     * @param pattern为正则表达式模式
     * @param str 原始字串
     */
    public static final List<String> regMatchAll2Vector(String pattern, String str) throws PatternSyntaxException {
        List<String> list = new ArrayList<String>();
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            while (m.find()) {
                list.add(m.group());
            }
            return list;
        } catch (PatternSyntaxException e) {
            throw e;
        }
    }

    /**
     * 模块标记分析函数 功能主要是把查找到的元素
     * @param pattern为正则表达式模式
     * @param str 原始字串
     */
    public static final String[] splitTags(String pattern, String str) {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            String[] array = new String[m.groupCount()];
            int i = 0;
            while (m.find()) {
                array[i] = eregReplace("(\\[\\#)|(\\#\\])", "", m.group());
                i++;
            }
            return array;
        } catch (PatternSyntaxException e) {
            throw e;
        }
    }

    /**
     * 主要用于模板中模块标记分析函数 把查找到的元素
     * @param pattern为正则表达式模式
     * @param str 原始字串
     */
    public static final List<String> splitTags2Vector(String pattern, String str) throws PatternSyntaxException {
        List<String> list = new ArrayList<String>();
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            while (m.find()) {
                list.add(eregReplace("(\\[\\#)|(\\#\\])", "", m.group()));
            }
            return list;
        } catch (PatternSyntaxException e) {
            throw e;
        }
    }
}
