package com.leeframework.core.web.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leeframework.common.utils.DateUtil;
import com.leeframework.core.web.Globals;

/**
 * Cookie辅助工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:36:24
 */
public final class CookieHelper {

    private static final int DEFAULE_AGE = (int) (DateUtil.MILLIS_PER_DAY * 10 / 1000); // 默认周期

    /**
     * 设置 Cookie,默认10天
     * @author lee
     * @date 2016年4月28日 上午12:27:34
     * @param response
     * @param name
     * @param value
     * @see #setCookie(HttpServletResponse, String, String, int)
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, DEFAULE_AGE);
    }

    /**
     * 设置 Cookie,默认10天
     * @author lee
     * @date 2016年4月28日 上午12:23:44
     * @param response
     * @param name
     * @param value
     * @param path
     * @see CookieHelper#setCookie(HttpServletResponse, String, String, String, int)
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path) {
        setCookie(response, name, value, path, DEFAULE_AGE);
    }

    /**
     * 设置Cookie
     * @author lee
     * @date 2016年4月28日 上午12:22:33
     * @param response
     * @param name
     * @param value
     * @param maxAge
     * @see CookieHelper#setCookie(HttpServletResponse, String, String, String, int)
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 设置 Cookie
     * @param name 名称
     * @param value 值
     * @param maxAge 生存时间（单位秒）
     * @param uri 路径
     */

    /**
     * 设置 Cookie
     * @author lee
     * @date 2016年4月28日 上午12:21:05
     * @param response HttpServletResponse
     * @param name 名称
     * @param value 值
     * @param path 路径
     * @param maxAge 生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        try {
            cookie.setValue(URLEncoder.encode(value, Globals.UTF8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }

    /**
     * 获得cookie,不删除
     * @author lee
     * @date 2016年4月28日 上午12:33:02
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

    /**
     * 获得指定Cookie的值，并删除。
     * @author lee
     * @date 2016年4月28日 上午12:33:13
     * @param request
     * @param response
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, true);
    }

    /**
     * 获得指定Cookie的值
     * @author lee
     * @date 2016年4月28日 上午12:33:27
     * @param request
     * @param response
     * @param name
     * @param isRemove
     * @return
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), Globals.UTF8);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isRemove) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        return value;
    }

}
