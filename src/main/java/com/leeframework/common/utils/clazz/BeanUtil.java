package com.leeframework.common.utils.clazz;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeframework.common.model.BeanProperty;
import com.leeframework.common.utils.Assert;

/**
 * Bean操作工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:38:18
 */
public class BeanUtil {
    private static Logger log = LoggerFactory.getLogger(BeanUtil.class);

    public final static String PRE_GET = "get";
    public final static String PRE_SET = "set";
    public final static String SERIALVERSIONUID = "serialVersionUID";

    /**
     * 判断类是否拥有某个属性
     * @datetime 2018年5月27日 下午9:58:12
     */
    public static boolean checkDeclaredField(Class<?> clazz, String fieldName) {
        return getDeclaredField(clazz, fieldName) != null;
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     * @author lee
     * @date 2016年4月20日 下午1:22:03
     * @param clazz Class
     * @param fieldName 属性名称
     * @return {@link java.lang.reflect.Field}
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Assert.notNull(clazz);
        Assert.hasText(fieldName);
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     * @author lee
     * @date 2016年4月20日 下午1:21:38
     * @param object
     * @param fieldName
     * @return
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Assert.notNull(object);
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     * @author lee
     * @date 2016年4月20日 下午1:21:10
     * @param object bean对象
     * @param fieldName 属性名称
     * @return
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Assert.notNull(object);
        Assert.hasText(fieldName);
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new RuntimeException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            log.error("Failed to get field value:{} in {}", fieldName, object.getClass(), e);
        }
        return result;
    }

    /**
     * 获取属性的get方法名称
     * @author lee
     * @date 2016年4月20日 下午2:43:16
     * @param name
     * @return
     */
    public static String getReadMethod(String name) {
        return PRE_GET + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
    }

    /**
     * 获取属性值,通过getter方法获取
     * @author lee
     * @date 2016年5月19日 上午11:04:09
     * @param bean 实体对象
     * @param propName 属性名称
     * @return
     */
    public static Object getSimpleProperty(Object bean, String propName) {
        Object value = null;
        try {
            value = bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
        } catch (Exception e) {
            log.error("Failed to get property value:{} in {}", propName, bean.getClass(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return value;
    }

    /**
     * 强制转换fileld可访问.
     * @author lee
     * @date 2016年4月20日 下午1:23:53
     * @param field
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 判断属性是否为静态属性
     * @author lee
     * @date 2016年6月3日 下午3:42:20
     * @param field
     * @return
     */
    public static boolean isStaticField(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     * @author lee
     * @date 2016年4月20日 下午1:24:25
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new RuntimeException("Could not find field [" + fieldName + "] on target [" + object.getClass() + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            log.error("Failed to set property value:{} in {}", fieldName, object.getClass(), e);
        }
    }

    /**
     * 读取一个实体bean的所有属性(包含名称，类型，和属性值),只读当前实体的属性
     * @author lee
     * @date 2016年6月1日 下午4:27:58
     * @param bean 需要被解析的实体bean
     * @return {@link BeanProperty}集合
     */
    public static List<BeanProperty> getBeanProperties(Object bean) {
        Assert.notNull(bean);
        List<BeanProperty> properties = new ArrayList<BeanProperty>();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (!isStaticField(field)) {
                String name = field.getName();
                if (!SERIALVERSIONUID.equals(name)) {
                    Class<?> clazz = field.getType();
                    Object value = getSimpleProperty(bean, name);
                    properties.add(new BeanProperty(clazz, name, value));
                }
            }
        }
        return properties;
    }

    /**
     * 读取一个实体bean的所有属性(包含父类)(包含名称，类型，和属性值)
     * @author lee
     * @date 2016年6月1日 下午4:27:58
     * @param bean 需要被解析的实体bean
     * @param includes 父类中包含的属性,本类中的属性将全部被解析,如果该参数为空,则获取所有父类的属性,否则值获取父类中该参数中设置的属性
     * @return {@link BeanProperty}集合
     */
    public static List<BeanProperty> getBeanPropertiesIncludeSuper(Object bean, List<String> includes) {
        List<BeanProperty> properties = new ArrayList<BeanProperty>();
        properties.addAll(getBeanProperties(bean));
        for (Class<?> superClass = bean.getClass().getSuperclass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!isStaticField(field)) {
                    String name = field.getName();
                    if (!SERIALVERSIONUID.equals(name)) {
                        if (includes == null || (includes != null && includes.contains(name))) {
                            Class<?> clazz = field.getType();
                            makeAccessible(field);
                            Object value = null;
                            try {
                                value = field.get(bean);
                            } catch (IllegalAccessException e) {
                                log.error("Failed to get field value:{} in {}", name, bean.getClass(), e);
                                throw new RuntimeException(e.getMessage(), e);
                            }
                            properties.add(new BeanProperty(clazz, name, value));
                        }
                    }
                }
            }
        }
        return properties;
    }

    /**
     * 对象拷贝, 将一个对象里面非空的值拷贝到同一个类的另一个实例对象中
     * @datetime 2018年5月30日 下午2:19:38
     * @param source 原始对象
     * @param target 目标对象
     * @param encludeFileds 拷贝的过程中,不需要拷贝的属性
     */
    public static void copyBeanNotNull2SameBean(Object source, Object target, String... encludeFields) {
        Assert.notNull(source);
        Assert.notNull(target);

        if (!source.getClass().equals(target.getClass())) {
            throw new RuntimeException("The original object and the target object are not the same class");
        }

        List<BeanProperty> beanFields = getBeanPropertiesIncludeSuper(source, null);
        List<String> encludeFieldsList = new ArrayList<String>();
        if (encludeFields != null) {
            for (String eField : encludeFields) {
                encludeFieldsList.add(eField);
            }
        }

        for (BeanProperty bp : beanFields) {
            String fieldName = bp.getName();
            if (!encludeFieldsList.contains(fieldName)) {
                Class<?> fieldType = bp.getClass();
                Object fieldValue = bp.getValue();
                if (fieldValue != null) {
                    if (fieldType.equals(List.class) || fieldType.equals(Set.class)) {
                        Collection<?> collection = (Collection<?>) fieldValue;
                        if (!collection.isEmpty()) {
                            setFieldValue(target, fieldName, fieldValue);
                        }
                    } else if (fieldType.equals(Map.class)) {
                        Map<?, ?> map = (Map<?, ?>) fieldValue;
                        if (!map.isEmpty()) {
                            setFieldValue(target, fieldName, fieldValue);
                        }
                    } else {
                        setFieldValue(target, fieldName, fieldValue);
                    }
                }
            }

        }
    }
}
