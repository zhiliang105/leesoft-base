package com.leeframework.common.utils.clazz;

/**
 * 自定义类加载器
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:39:33
 */
public final class ClassLoaderHelper {
    private ClassLoaderHelper() {
    }

    public static ClassLoader getContextClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }
        if (cl == null) {
            cl = ClassLoaderHelper.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                }
            }
        }
        return cl;
    }
}
