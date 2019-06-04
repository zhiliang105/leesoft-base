package com.leeframework.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.leeframework.common.utils.DateUtil;

/**
 * Controller日志拦截器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月29日 下午11:46:33
 */
public class ControllerLogInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(ControllerLogInterceptor.class);

    private static final ThreadLocal<Long> STARTTIME_THREAD_LOCAL = new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (log.isInfoEnabled()) {
            long startTime = System.currentTimeMillis();
            STARTTIME_THREAD_LOCAL.set(startTime); // 线程绑定变量（该数据只有当前请求的线程可见）
            String handlerMsg = getHandlerInfo(handler);
            log.info("Execute[Controller]-Handler[{}], URI:[{}], StartTime:[{}]", handlerMsg, request.getRequestURI(),
                    DateUtil.datetimeFormat(startTime));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO 保存日志信息到数据库

        if (log.isInfoEnabled()) {
            long startTime = STARTTIME_THREAD_LOCAL.get();// 得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis(); // 结束时间

            String handlerMsg = getHandlerInfo(handler);
            log.info("Execute[Controller]-Handler[{}], URI:[{}], EndTime:[{}], Time:[{}]", handlerMsg, request.getRequestURI(),
                    DateUtil.datetimeFormat(endTime), (endTime - startTime) + "ms");
        }
    }

    private String getHandlerInfo(Object handler) {
        String handlerMsg = "";
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            handlerMsg = hm.getBean().getClass() + ":" + hm.getMethod().getName();
        }
        return handlerMsg;
    }
}
