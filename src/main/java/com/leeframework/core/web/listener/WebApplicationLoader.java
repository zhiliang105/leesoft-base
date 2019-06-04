package com.leeframework.core.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.leeframework.core.web.helper.SpringContext;

/**
 * WEB应用程序加载处理<br>
 * 首先加载初始化spring容器,同时初始化web应用的相关信息
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月29日 下午1:58:57
 */
public abstract class WebApplicationLoader extends ContextLoaderListener {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        if (log.isDebugEnabled()) {
            log.debug("The web application is initializing...");
        }
        this.initSpringContent(event);
        this.initWebApplication(event);
    }

    @Override
    public final void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
        if (log.isDebugEnabled()) {
            log.debug("The web application is destroyed");
        }
    }

    private void initSpringContent(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        SpringContext.setContext(ctx);
    }

    /**
     * WEB应用程序初始化入口
     * @datetime 2017年12月3日 下午10:25:19
     * @param servletContext
     */
    public abstract void initWebApplication(ServletContextEvent event);
}
