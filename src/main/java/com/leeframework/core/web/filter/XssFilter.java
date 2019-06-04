package com.leeframework.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 方式xss攻击过滤器<br>
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年6月10日 下午4:47:16
 */
public class XssFilter extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
                                                                                                                       IOException {

        filterChain.doFilter(new XssHttpServletRequestWrapper(request), response);

    }

}
