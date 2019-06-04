package com.leeframework.common.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Properties文件载入工具类. <br>
 * 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值 <br>
 * 但以System的Property优先.
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:40:41
 */
public class PropertiesLoader {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties;

    private Map<String, String> propertiesMap;

    public PropertiesLoader(String... resourcesPaths) {
        this.properties = loadProperties(resourcesPaths);

        this.propertiesMap = new HashMap<String, String>();
        Set<Object> keySet = this.properties.keySet();
        for (Object key : keySet) {
            if (key != null) {
                String keyStr = String.valueOf(key);
                String value = this.properties.getProperty(keyStr);
                this.propertiesMap.put(keyStr, value);
            }
        }

    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     * @author lee
     * @date 2016年4月29日 上午1:32:11
     * @param key
     * @return
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            logger.warn("No such element[{}] in properties!", key);
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     * @author lee
     * @date 2016年4月29日 上午1:32:20
     * @param key
     * @param defaultValue
     * @return
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     * @author lee
     * @date 2016年4月29日 上午1:32:27
     * @param key
     * @return
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            logger.warn("No such element[{}] in properties!", key);
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     * @author lee
     * @date 2016年4月29日 上午1:32:53
     * @param key
     * @param defaultValue
     * @return
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     * @author lee
     * @date 2016年4月29日 上午1:33:01
     * @param key
     * @return
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            logger.warn("No such element[{}] in properties!", key);
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     * @author lee
     * @date 2016年4月29日 上午1:33:07
     * @param key
     * @param defaultValue
     * @return
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     * @author lee
     * @date 2016年4月29日 上午1:33:19
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            logger.warn("No such element[{}] in properties!", key);
        }
        return value;
    }

    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     * @author lee
     * @date 2016年4月29日 上午1:33:34
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Property，但以System的Property优先,取不到返回空字符串.
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (this.propertiesMap.containsKey(key)) {
            return this.propertiesMap.get(key);
        }
        return null;
    }

    /**
     * 载入多个文件, 文件路径使用Spring Resource格式.
     */
    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();
        for (String location : resourcesPaths) {
            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(is);
            } catch (IOException ex) {
                logger.error("Could not load properties from path:" + location + ", " + ex.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return props;
    }
}
