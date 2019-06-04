package com.leeframework.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域问题
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月29日 下午5:19:20
 */
public class CorsFilter extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
                                                                                                                       IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,PUT,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Accept,Origin,X-Requested-With,Content-Type,X-Auth-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);

    }

}
