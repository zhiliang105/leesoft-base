package com.leeframework.core.web.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * Spring上下文管理类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 下午10:18:00
 */
public final class SpringContext {

    private static ApplicationContext context;

    private SpringContext() {

    }

    private static void assertContextInjected() {
        if (context == null) {
            throw new RuntimeException("ApplicationContext属性未注入");
        }
    }

    public static boolean containsBean(String name) {
        assertContextInjected();
        return context.containsBean(name);
    }

    public static Object getBean(String name) throws BeansException {
        assertContextInjected();
        return context.getBean(name);
    }

    public static Object getBean(Class<?> requiredType) throws BeansException {
        assertContextInjected();
        return context.getBean(requiredType);
    }

    public static Object getBean(String name, Class<?> requiredType) throws BeansException {
        assertContextInjected();
        return context.getBean(name, requiredType);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        assertContextInjected();
        return context.isSingleton(name);
    }

    public static void setContext(ApplicationContext context) {
        SpringContext.context = context;
    }
}
