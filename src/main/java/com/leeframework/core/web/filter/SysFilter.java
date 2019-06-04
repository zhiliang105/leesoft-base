package com.leeframework.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leeframework.common.utils.UserAgentUtil;
import com.leeframework.common.utils.properties.SysConfigProperty;
import com.leeframework.core.web.helper.RequestHelper;

import eu.bitwalker.useragentutils.Browser;

/**
 * 系统过滤器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月6日 下午12:44:59
 */
public class SysFilter extends BaseFilter {

    private String encoding;
    private boolean forceEncoding = false;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
                                                                                                                       IOException {
        if (!checkBrowser(request, response)) {
            return;
        }

        // 取消缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 编码定义
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(this.encoding);
            if (this.forceEncoding) {
                response.setCharacterEncoding(this.encoding);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkBrowser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 浏览器检测
        if (UserAgentUtil.isComputer(request)) {
            boolean flag = SysConfigProperty.isCheckeBrowserVersion();
            if (flag) {
                boolean isAjax = RequestHelper.isAjaxRequest(request);
                if (!isAjax) {

                    // 过滤掉静态资源
                    String servletPath = request.getServletPath();
                    if (!servletPath.startsWith(SysConfigProperty.getStaticDir())) {
                        boolean isLteIE8 = UserAgentUtil.isLteIE8(request);
                        if (isLteIE8) {
                            Browser browser = UserAgentUtil.getBrowser(request);
                            if (log.isWarnEnabled()) {
                                log.warn("The current browser version for {}, the system does not support", browser);
                            }
                            request.getRequestDispatcher("/WEB-INF/pages/common/error_browser.jsp").forward(request, response);
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

}
