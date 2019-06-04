package com.leeframework.common.hibernate4.helper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Hibernate对象更新器
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月21日 Time: 上午10:03:41
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class Updater {

    public final static String UPDATE = "update";
    public final static String WHERE = "where";

    protected String alias; // 别名
    protected StringBuilder hqlBuilder;

    protected boolean isWhere;
    protected List<Object> parameters;
    protected List<String> properties;
    protected int propertyIndex;

    protected Updater(Class<?> clazz) {
        this.properties = new ArrayList<String>();
        this.parameters = new ArrayList<Object>();
        this.isWhere = false;
        this.propertyIndex = 0;

        String className = clazz.getSimpleName();
        this.alias = className.toLowerCase() + "_";
        this.hqlBuilder = new StringBuilder();
        hqlBuilder.append(UPDATE).append(" ").append(className).append(" ").append(this.alias).append(" set ");
    }

    /**
     * 获取更新器实例
     * @author lee
     * @date 2016年4月21日 上午10:04:54
     * @param clazz
     * @return
     */
    public static Updater create(Class<?> clazz) {
        return new Updater(clazz);
    }

    /**
     * 创建Query,并设置好参数,
     * @author lee
     * @date 2016年4月21日 上午10:19:19
     * @param session
     * @return
     */
    public Query createQuery(Session session) {
        if (this.properties.size() == 0) {
            throw new RuntimeException("the hibernate custom updater not find property to update");
        }
        Query query = session.createQuery(getHql());
        for (int i = 0; i < this.properties.size(); i++) {
            query = query.setParameter(properties.get(i), parameters.get(i));
        }
        return query;
    }

    /**
     * 执行更新操作。
     * @author lee
     * @date 2016年4月21日 上午10:22:36
     * @param session
     * @return
     */
    public int executeUpdate(Session session) {
        return createQuery(session).executeUpdate();
    }

    /**
     * 获取生成的用于查询数据列表的HQL语句
     * @return HQL语句
     */
    public String getHql() {
        if (!isWhere) {
            hqlBuilder = hqlBuilder.deleteCharAt(hqlBuilder.length() - 1);
        }
        return hqlBuilder.toString();
    }

    /**
     * 添加set设置更新的数据值条件
     * @author lee
     * @date 2016年4月21日 上午10:12:34
     * @param property 实体属性
     * @param value set后的值
     * @return
     */
    public Updater setProperty(String property, Object value) {
        String param = getParamName(property);
        hqlBuilder.append(this.alias).append(".").append(property).append("=:" + param + ",");
        this.addParameter(param, value);
        return this;
    }

    /**
     * 更新语句的where条件(等于),此方法的调用必须在{@link #setProperty(String, String)}方法设置完成之后设置
     * @author lee
     * @date 2016年4月21日 上午10:17:56
     * @param property
     * @param value
     * @return
     */
    public Updater whereEq(String property, Object value) {
        whereCommon(property, value, Querier.F_EQ);
        return this;
    }

    /**
     * 更新语句的where条件(大于),此方法的调用必须在{@link #setProperty(String, String)}方法设置完成之后设置
     * @author lee
     * @date 2016年4月21日 上午10:26:32
     * @param property
     * @param value
     * @param isEq
     * @return
     */
    public Updater WhereGt(String property, Object value, boolean isEq) {
        whereCommon(property, value, isEq ? Querier.F_GTE : Querier.F_GT);
        return this;
    }

    /**
     * 更新语句的where条件(小于),此方法的调用必须在{@link #setProperty(String, String)}方法设置完成之后设置
     * @author lee
     * @date 2016年4月21日 上午10:26:32
     * @param property
     * @param value
     * @param isEq
     * @return
     */
    public Updater WhereLt(String property, Object value, boolean isEq) {
        whereCommon(property, value, isEq ? Querier.F_LTE : Querier.F_LT);
        return this;
    }

    private void whereCommon(String property, Object value, String op) {
        if (!isWhere) {
            hqlBuilder = hqlBuilder.deleteCharAt(hqlBuilder.length() - 1);
            hqlBuilder.append(" ");
            hqlBuilder.append(WHERE).append(" ");
            isWhere = true;
        } else {
            hqlBuilder.append(" and ");
        }
        String param = getParamName(property);
        hqlBuilder.append(this.alias).append(".").append(property).append(op + ":" + param);
        this.addParameter(param, value);
    }

    private void addParameter(String property, Object o) {
        this.properties.add(property);
        this.parameters.add(o);
    }

    /**
     * 获取参数的名称
     * @author lee
     * @date 2016年4月21日 上午10:16:32
     * @param property 属性名称
     * @return
     */
    private String getParamName(String property) {
        String temp = property.indexOf(".") >= 0 ? property.replaceAll("\\.", "") : property;
        temp += propertyIndex++;
        return temp;
    }

}
