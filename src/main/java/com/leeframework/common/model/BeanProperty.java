package com.leeframework.common.model;

/**
 * Bean属性实体
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:39:02
 */
public class BeanProperty {

    private Class<?> clazz;// 属性的类型
    private String name;// 属性名称
    private Object value;// 属性的值

    public BeanProperty() {
    }

    public BeanProperty(Class<?> clazz, String name, Object value) {
        this.clazz = clazz;
        this.name = name;
        this.value = value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
