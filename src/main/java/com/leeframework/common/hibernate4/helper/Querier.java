package com.leeframework.common.hibernate4.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * hibernate 查询器<br>
 * 参考：jeecms Finder
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月19日 Time: 下午2:48:13
 * @version 1.0
 * @since version 1.0
 * @update
 */
@SuppressWarnings("rawtypes")
public class Querier {

    public static final String DISTINCT = "distinct";
    public static final String FROM = "from";
    public static final String GROUP_BY = "group";
    public static final String HQL_FETCH = "fetch";
    public static final String ORDER_BY = "order";
    public static final String ROW_COUNT = "select count(*) ";

    public static final String F_EQ = "=";
    public static final String F_GT = ">";
    public static final String F_LT = "<";
    public static final String F_GTE = ">=";
    public static final String F_LTE = "<=";
    public static final String F_LIKE = "like";

    private StringBuilder hqlBuilder;
    private boolean cacheable = false;

    private List<String> params = new ArrayList<String>();
    private List<String> paramsArray = new ArrayList<String>();
    private List<String> paramsList = new ArrayList<String>();

    private List<Type> types = new ArrayList<Type>();
    private List<Type> typesArray = new ArrayList<Type>();
    private List<Type> typesList = new ArrayList<Type>();

    private List<Object> values = new ArrayList<Object>();
    private List<Object[]> valuesArray = new ArrayList<Object[]>();
    private List<Collection> valuesList = new ArrayList<Collection>();

    protected Querier() {
        hqlBuilder = new StringBuilder();
    }

    protected Querier(String hql) {
        hqlBuilder = new StringBuilder(hql);
    }

    /**
     * 创建一个空的查询器
     * @author lee
     * @date 2016年4月19日 下午2:55:08
     * @return
     */
    public static Querier create() {
        return new Querier();
    }

    /**
     * 创建一个具体制定hql的查询器
     * @author lee
     * @date 2016年4月19日 下午2:55:17
     * @param hql
     * @return
     */
    public static Querier create(String hql) {
        return new Querier(hql);
    }

    /**
     * 追加Hql语句
     * @author lee
     * @date 2016年4月20日 上午11:00:17
     * @param hql
     * @return
     */
    public Querier append(String hql) {
        hqlBuilder.append(hql);
        return this;
    }

    /**
     * 追加HQL查询语句,并传递参数和参数值
     * @author lee
     * @date 2016年4月19日 下午11:38:19
     * @param hql 追加的Hql语句
     * @param param 参数名称
     * @param value 参数值
     * @return
     */
    public Querier append(String hql, String param, Object value) {
        append(hql);
        setParam(param, value);
        return this;
    }

    /**
     * 追加HQL查询语句
     * @author lee
     * @date 2016年4月20日 上午11:06:33
     * @param hql 追加的Hql语句
     * @param paramMap 参数属性值Map
     * @return
     */
    public Querier append(String hql, Map<String, Object> paramMap) {
        append(hql);
        setParams(paramMap);
        return this;
    }

    /**
     * 追加HQL查询语句,并传递参数和数组类型的值
     * @author lee
     * @date 2016年4月20日 上午10:14:09
     * @param hql 追加的Hql语句
     * @param param 参数名称
     * @param values 参数值
     * @return
     */
    public Querier appendArray(String hql, String param, Object[] values) {
        append(hql);
        setParamList(param, values);
        return this;
    }

    /**
     * 追加HQL查询语句,并传递参数和集合类型的值
     * @author lee
     * @date 2016年4月20日 上午10:14:09
     * @param hql 追加的Hql语句
     * @param param 参数名称
     * @param values 参数值
     * @return
     */
    public Querier appendList(String hql, String param, Collection values) {
        append(hql);
        setParamList(param, values);
        return this;
    }

    /**
     * 追加HQL查询语句,并根据判断的参数值是否为空传递参数和参数值
     * @author lee
     * @date 2016年4月19日 下午11:39:17
     * @param hql 追加的Hql语句
     * @param param 参数名称
     * @param value 参数值 是否需要判定参数值是否为空
     * @return
     */
    public Querier appendNotNull(String hql, String param, Object value) {
        if (value != null && !value.toString().equals("")) {
            append(hql, param, value);
        }
        return this;
    }

    /**
     * 参数值不为空的时候追加hql查询条件
     * @author lee
     * @date 2016年4月20日 上午10:14:09
     * @param hql 追加的Hql语句
     * @param param 参数名称
     * @param values 参数值
     * @return
     */
    public Querier appendNotNull(String hql, String param, Object[] values) {
        if (values != null && values.length > 0) {
            append(hql, param, values);
        }
        return this;
    }

    /**
     * 参数值不为空的时候追加hql查询条件
     * @author lee
     * @date 2016年4月20日 上午10:14:09
     * @param hql 追加的Hql语句
     * @param param 参数名称
     * @param values 参数值
     * @return
     */
    public Querier appendNotNull(String hql, String param, Collection<Object> values) {
        if (values != null && values.size() > 0) {
            append(hql, param, values);
        }
        return this;
    }

    public String getHql() {
        return hqlBuilder.toString();
    }

    public String getRowCountHql() {
        return getRowCountBaseHql(ORDER_BY);
    }

    /**
     * 创建查询对象Query
     * @author lee
     * @date 2016年4月19日 下午5:12:45
     * @param session
     * @return
     */
    public Query createQuery(Session session) {
        Query query = setParamsToQuery(session.createQuery(getHql()));
        if (cacheable) {
            query.setCacheable(true);
        }
        return query;
    }

    /**
     * 创建Count查询Query
     * @author lee
     * @date 2016年4月19日 下午11:30:50
     * @param session
     * @return
     */
    public Query createQueryRowCount(Session session) {
        Query query = setParamsToQuery(session.createQuery(getRowCountHql()));
        if (cacheable) {
            query.setCacheable(true);
        }
        return query;
    }

    /**
     * 获取是否需要缓存
     * @author lee
     * @date 2016年4月19日 下午5:11:08
     * @return
     */
    public boolean isCacheable() {
        return cacheable;
    }

    /**
     * 设置是否需要缓存
     * @author lee
     * @date 2016年4月19日 下午5:11:19
     * @param cacheable
     */
    public Querier setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
        return this;
    }

    /**
     * 将querier中的参数设置到query中。
     * @author lee
     * @date 2016年4月19日 下午10:55:40
     * @param query
     * @return
     */
    private Query setParamsToQuery(Query query) {
        for (int i = 0; i < params.size(); i++) {
            if (types.get(i) == null) {
                query.setParameter(params.get(i), values.get(i));
            } else {
                query.setParameter(params.get(i), values.get(i), types.get(i));
            }
        }
        for (int i = 0; i < paramsList.size(); i++) {
            if (typesList.get(i) == null) {
                query.setParameterList(paramsList.get(i), valuesList.get(i));
            } else {
                query.setParameterList(paramsList.get(i), valuesList.get(i), typesList.get(i));
            }
        }
        for (int i = 0; i < paramsArray.size(); i++) {
            if (typesArray.get(i) == null) {
                query.setParameterList(paramsArray.get(i), valuesArray.get(i));
            } else {
                query.setParameterList(paramsArray.get(i), valuesArray.get(i), typesArray.get(i));
            }
        }
        return query;
    }

    private String getRowCountBaseHql(String indexKey) {
        String hql = hqlBuilder.toString();

        int fromIndex = hql.toLowerCase().indexOf(FROM);
        String projectionHql = hql.substring(0, fromIndex);

        hql = hql.substring(fromIndex);
        String rowCountHql = hql.replace(HQL_FETCH, "");

        int index = rowCountHql.indexOf(indexKey);
        if (index > 0) {
            rowCountHql = rowCountHql.substring(0, index);
        }
        return wrapProjection(projectionHql) + rowCountHql;
    }

    /**
     * 设置参数
     * @param param 参数名称
     * @param value 参数值
     * @return
     * @see Query#setParameter(String, Object)
     */
    private void setParam(String param, Object value) {
        setParam(param, value, null);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     * @param param
     * @param value
     * @param type
     * @return
     * @see Query#setParameter(String, Object, Type)
     */
    private void setParam(String param, Object value, Type type) {
        this.params.add(param);
        this.values.add(value);
        this.types.add(type);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     * @param name
     * @param vals
     * @return
     * @see Query#setParameterList(String, Collection)
     */
    private void setParamList(String name, Collection vals) {
        setParamList(name, vals, null);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     * @param name
     * @param vals
     * @param type
     * @return
     * @see Query#setParameterList(String, Collection, Type))
     */
    private void setParamList(String name, Collection vals, Type type) {
        this.paramsList.add(name);
        this.valuesList.add(vals);
        this.typesList.add(type);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     * @param name
     * @param vals
     * @return
     * @see Query#setParameterList(String, Object[])
     */
    private void setParamList(String name, Object[] vals) {
        setParamList(name, vals, null);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     * @param name
     * @param vals
     * @param type
     * @return
     * @see Query#setParameterList(String, Object[], Type)
     */
    private void setParamList(String name, Object[] vals, Type type) {
        this.paramsArray.add(name);
        this.valuesArray.add(vals);
        this.typesArray.add(type);
    }

    /**
     * 设置参数。与hibernate的Query接口一致。
     * @param paramMap
     * @return
     * @see Query#setProperties(Map)
     */
    private void setParams(Map<String, Object> paramMap) {
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            setParam(entry.getKey(), entry.getValue());
        }
    }

    private String wrapProjection(String projection) {
        if (projection.indexOf("select") == -1) {
            return ROW_COUNT;
        } else {
            return projection.replace("select", "select count(") + ") ";
        }
    }

}
