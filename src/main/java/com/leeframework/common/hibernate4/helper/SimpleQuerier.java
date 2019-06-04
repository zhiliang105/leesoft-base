package com.leeframework.common.hibernate4.helper;

/**
 * 简易查询器,针对单表查询
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月19日 Time: 下午11:44:47
 * @version 1.0
 * @since version 1.0
 * @update
 * @see Querier
 */
public class SimpleQuerier extends Querier {

    private static String alias = "bean";
    private int propertyIndex = 0;
    private boolean isOrder = false;

    public static SimpleQuerier create(Class<?> clazz) {
        return new SimpleQuerier(clazz);
    }

    private SimpleQuerier(Class<?> clazz) {
        super(FROM + " " + clazz.getSimpleName() + " " + alias + " where 1=1 ");
    }

    /**
     * 添加单个查询条件,并使属性like某个值
     * @author lee
     * @date 2016年4月19日 下午11:48:02
     * @param property 属性名称
     * @param value 参数值 需要加 %% , 例如: "%+value+%"
     * @return
     */
    public SimpleQuerier appendLike(String property, String value) {
        appendCondition(property, value, F_LIKE);
        return this;
    }

    /**
     * 添加单个查询条件,并使属性等于某个值
     * @author lee
     * @date 2016年4月19日 下午11:48:02
     * @param property 属性名称
     * @param value 参数值
     * @return
     */
    public SimpleQuerier appendEq(String property, Object value) {
        appendCondition(property, value, F_EQ);
        return this;
    }

    /**
     * 根据属性值是否为空判定是否要追加查询条件
     * @author lee
     * @date 2016年4月19日 下午11:53:34
     * @param property 属性名称
     * @param value 参数值
     * @return
     */
    public SimpleQuerier appendEqNotNull(String property, Object value) {
        if (value != null && !value.toString().equals("")) {
            appendEq(property, value);
        }
        return this;
    }

    /**
     * 添加单个查询条件,并使属性大于或者等于某个值
     * @author lee
     * @date 2016年4月19日 下午11:48:02
     * @param property 属性名称
     * @param value 参数值
     * @param eq 是否等于
     * @return
     */
    public SimpleQuerier appendGt(String property, Object value, boolean eq) {
        appendCondition(property, value, eq ? F_GTE : F_GT);
        return this;
    }

    /**
     * 根据属性值是否为空判定是否要追加查询条件
     * @author lee
     * @date 2016年4月19日 下午11:53:34
     * @param property 属性名称
     * @param value 参数值
     * @param eq 是否等于
     * @return
     */
    public SimpleQuerier appendGtNotNull(String property, Object value, boolean eq) {
        if (value != null && !value.toString().equals("")) {
            appendGt(property, value, eq);
        }
        return this;
    }

    /**
     * 添加单个查询条件,并使属性小于或者等于某个值
     * @author lee
     * @date 2016年4月19日 下午11:48:02
     * @param property 属性名称
     * @param value 参数值
     * @param eq 是否等于
     * @return
     */
    public SimpleQuerier appendLt(String property, Object value, boolean eq) {
        appendCondition(property, value, eq ? F_LTE : F_LT);
        return this;
    }

    /**
     * 根据属性值是否为空判定是否要追加查询条件
     * @author lee
     * @date 2016年4月19日 下午11:53:34
     * @param property 属性名称
     * @param value 参数值
     * @param eq 是否等于
     * @return
     */
    public SimpleQuerier appendLtNotNull(String property, Object value, boolean eq) {
        if (value != null && !value.toString().equals("")) {
            appendLt(property, value, eq);
        }
        return this;
    }

    /**
     * 追加HQL是某个属性在两个值范围区间内
     * @author lee
     * @date 2016年4月20日 上午11:42:08
     * @param property 属性名称
     * @param value1
     * @param value2
     * @return
     */
    public SimpleQuerier appendBetween(String property, Object value1, Object value2) {
        appendGtNotNull(property, value1, true);
        appendLtNotNull(property, value2, true);
        return this;
    }

    /**
     * 追加排序
     * @author lee
     * @date 2016年4月20日 上午11:46:43
     * @param propertyName 属性名称
     * @param asc true：升序，false:降序
     * @return
     */
    public SimpleQuerier appendOrder(String propertyName, boolean asc) {
        String order = alias + "." + propertyName + (asc ? " asc" : " desc");
        if (!isOrder) {
            append(" order by " + order);
            isOrder = true;
        } else {
            append("," + order);
        }
        return this;
    }

    public String getAlias() {
        return alias;
    }

    private void appendCondition(String property, Object value, String op) {
        String param = property.toLowerCase();
        if (param.indexOf(".") > 0) {
            param = param.replace(".", "");
        }
        param += propertyIndex++;

        StringBuilder hql = new StringBuilder(" and ");
        hql.append(alias + "." + property + " ");
        hql.append(op + " :" + param);
        append(hql.toString(), param, value);
    }
}
