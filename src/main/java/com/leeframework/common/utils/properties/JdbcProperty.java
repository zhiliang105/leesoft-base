package com.leeframework.common.utils.properties;

/**
 * JDBC属性文件属性操作工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:40:17
 */
public class JdbcProperty extends PropertiesSupport {

    public static String getDriver() {
        return LOADER.getProperty("jdbc.driverClassName");
    }

    public static String getUrl() {
        return LOADER.getProperty("jdbc.url");
    }

    public static String getUsername() {
        return LOADER.getProperty("jdbc.username");
    }

    public static String getPassword() {
        return LOADER.getProperty("jdbc.password");
    }
}
