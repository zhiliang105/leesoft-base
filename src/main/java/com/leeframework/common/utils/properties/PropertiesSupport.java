package com.leeframework.common.utils.properties;

/**
 * 属性文件处理基础支持类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月3日 下午10:53:24
 */
public abstract class PropertiesSupport {

    /**
     * 全局的属性文件加载对象<br>
     * sysConfig.properties,jdbc.properties
     */
    public static final PropertiesLoader LOADER = new PropertiesLoader("sysConfig.properties", "jdbc.properties");

}
