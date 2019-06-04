package com.leeframework.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.leeframework.core.web.helper.RequestHelper;

/**
 * 全局异常处理器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月29日 下午11:33:02
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        // 是否为ajax请求
        boolean isAjax = RequestHelper.isAjaxRequest(request);

        // shiro未授权将会抛出AuthorizationException异常,会被springmvc捕获,在此统一处理
        if (ex instanceof org.apache.shiro.authz.AuthorizationException) {
            return handleAuthorizationException(isAjax, request, response, handler);
        } else {
            return handleSystemExeption(isAjax, request, response, handler, ex);
        }

        // TODO 上面两个方法的处理逻辑编写

    }

    /**
     * 处理系统异常
     * @author lee
     * @date 2016年5月28日 下午5:37:44
     */
    private ModelAndView handleSystemExeption(boolean isAjax, HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        if (ex instanceof DaoRuntimeException) {
            log.error("DaoRuntimeException", ex);
        } else if (ex instanceof ServiceRuntimeException) {
            log.error("ServiceRuntimeException", ex);
        } else {
            log.error("RuntimeException", ex);
        }

        if (isAjax) {
            response.setStatus(500);
        }

        return new ModelAndView("/common/error_500");
    }

    /**
     * 处理未授权
     * @author lee
     * @date 2016年5月28日 下午5:37:28
     */
    private ModelAndView handleAuthorizationException(boolean isAjax, HttpServletRequest request, HttpServletResponse response, Object handler) {
        return new ModelAndView("/common/error_403");
    }
}
