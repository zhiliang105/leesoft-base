package com.leeframework.core.security.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

import com.leeframework.core.web.helper.RequestHelper;

/**
 * 自定义LogoutFilter
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月30日 上午10:22:08
 */
public class CustomerLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    @Override
    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return super.getSubject(request, response);
    }

    /**
     * 重写该方法,实现ajax退出操作
     */
    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        boolean isAjax = RequestHelper.isAjaxRequest(req);
        if (isAjax) {
            // TODO 暂时不采用ajax退出的方式,直接链接退出
        } else {
            WebUtils.issueRedirect(request, response, redirectUrl);
        }
    }

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        return super.getRedirectUrl(request, response, subject);
    }

    @Override
    public String getRedirectUrl() {
        return super.getRedirectUrl();
    }

    public void setRedirectUrl(String redirectUrl) {
        super.setRedirectUrl(redirectUrl);
    }

}
