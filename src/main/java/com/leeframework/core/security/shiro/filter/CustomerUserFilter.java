package com.leeframework.core.security.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeframework.core.web.helper.RequestHelper;

/**
 * 自定义UserFilter
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月30日 上午10:22:33
 */
public class CustomerUserFilter extends UserFilter {
    private final static Logger log = LoggerFactory.getLogger(CustomerUserFilter.class);

    protected void redirectToLogin(ServletRequest req, ServletResponse resp) throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        boolean isAjax = RequestHelper.isAjaxRequest(request);
        if (log.isWarnEnabled()) {
            log.warn("Login timeout, forwarded to the login page to login again,ajax[{}]", isAjax);
        }
        if (isAjax) {
            response.setStatus(908);
        } else {
            WebUtils.issueRedirect(request, response, getLoginUrl());
        }
    }
}
