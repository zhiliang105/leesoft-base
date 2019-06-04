package com.leeframework.core.web.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeframework.common.utils.clazz.BeanUtil;

/**
 * 过滤器基类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月6日 下午1:19:51
 */
public abstract class BaseFilter implements Filter {
    protected Logger log = LoggerFactory.getLogger(getClass());

    private FilterConfig filterConfig;

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        doFilterInternal(httpRequest, httpResponse, filterChain);
    }

    @Override
    public final void init(FilterConfig filterConfig) throws ServletException {
        if (log.isDebugEnabled()) {
            log.debug("Initializing filter '" + filterConfig.getFilterName() + "'");
        }
        this.filterConfig = filterConfig;

        // 初始化过滤器参数
        Enumeration<?> en = this.filterConfig.getInitParameterNames();
        while (en.hasMoreElements()) {
            String property = (String) en.nextElement();
            String value = this.filterConfig.getInitParameter(property);

            Field thisField = BeanUtil.getDeclaredField(this.getClass(), property);
            if (thisField != null) {
                Object realValue = null;
                try {
                    Class<?> fieldType = thisField.getType();
                    if (fieldType.equals(String.class)) {
                        realValue = value;
                    } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                        realValue = Integer.valueOf(value);
                    } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                        realValue = Double.valueOf(value);
                    } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                        realValue = Boolean.valueOf(value);
                    }
                } catch (Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn("Filter parameter[" + property + "=" + value + "] initialization failed", e);
                    }
                }
                if (realValue != null) {
                    BeanUtil.setFieldValue(this, property, realValue);
                }
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Initialization parameters '" + property + "' in the filter are invalid");
                }
            }

        }

        initFilter();

        if (log.isDebugEnabled()) {
            log.debug("Filter '" + filterConfig.getFilterName() + "' configured successfully");
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 过滤器其他的一些初始化操作
     * @datetime 2017年12月6日 下午1:36:11
     */
    protected void initFilter() throws ServletException {
    }

    /**
     * 过滤器过滤操作实现方法
     * @datetime 2017年12月6日 下午1:37:12
     */
    protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException;

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

}
