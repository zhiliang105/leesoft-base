package com.leeframework.common.hibernate4.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.leeframework.common.hibernate4.DynamicResultTransformer;
import com.leeframework.common.hibernate4.helper.Querier;
import com.leeframework.common.hibernate4.helper.Updater;
import com.leeframework.common.model.Page;
import com.leeframework.common.utils.Assert;
import com.leeframework.common.utils.clazz.BeanUtil;

/**
 * Hibernate dao基类<br>
 * hibernate通用dao操作类,提供hql和sql的基本操作
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月19日 下午2:47:33
 */
@SuppressWarnings("unchecked")
public abstract class CommonDao {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Session.clear()
     * @datetime 2017年12月18日 下午7:25:05
     */
    public void clear() {
        getSession().clear();
    }

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     * @author lee
     * @date 2016年5月7日 下午3:56:33
     * @param entityClass 实体Class
     * @param criterions 可变参数,条件组合
     * @return Criteria
     */
    public Criteria createCriteria(Class<?> entityClass, Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     * @author lee
     * @date 2016年4月20日 下午1:50:04
     * @param hql 基本hql语句
     * @param values 参数值
     */
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 创建sql查询对象
     * @author lee
     * @date 2016年4月20日 下午1:51:00
     * @param sql sql语句
     * @param values 参数值
     */
    public SQLQuery createSQLQuery(String sql, Object... values) {
        Assert.hasText(sql);
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                sqlQuery.setParameter(i, values[i]);
            }
        }
        return sqlQuery;
    }

    /**
     * 创建指定结果转移类型的SQLQuery
     * @date 2016-11-11 下午03:02:02
     */
    public Query createSQLQueryTransformer(Class<?> clazz, String sql, Object... values) {
        SQLQuery sqlQuery = createSQLQuery(sql, values);
        return sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
    }

    /**
     * 执行更新
     * @datetime 2018年5月25日 下午9:02:12
     */
    public int execute(Updater updater) {
        int r = updater.executeUpdate(getSession());
        flush();
        return r;
    }

    /**
     * 直接执行hql的更新操作
     * @author lee
     * @date 2016年4月20日 下午9:26:23
     * @param hql hql语句
     * @param values 可变组合参数
     */
    public int executeUpdate(String hql, Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 直接执行sql的更新操作
     * @author lee
     * @date 2016年5月7日 下午3:58:54
     * @param sql sql语句
     * @param values 可变组合参数
     */
    public int executeUpdateSQL(String sql, Object... values) {
        return createSQLQuery(sql, values).executeUpdate();
    }

    /**
     * 查询分页数据
     * @datetime 2018年5月25日 下午8:12:16
     */
    @SuppressWarnings({ "rawtypes" })
    public <T> Page<T> find(Class<T> clazz, Criteria criteria, int pageNo, int pageSize) {
        String orderEntriesPro = "orderEntries";

        CriteriaImpl impl = (CriteriaImpl) criteria;
        Projection projection = impl.getProjection(); // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        ResultTransformer transformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries;
        try {
            orderEntries = (List) BeanUtil.getFieldValue(impl, orderEntriesPro);
            BeanUtil.setFieldValue(impl, orderEntriesPro, new ArrayList());
        } catch (Exception e) {
            throw new RuntimeException("cannot read/write 'orderEntries' from CriteriaImpl", e);
        }

        int totalCount = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        Page<T> p = new Page<T>(pageNo, pageSize, totalCount, null);
        if (totalCount < 1) {
            p.setRecordList(new ArrayList());
            return p;
        }

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        criteria.setProjection(projection);
        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            criteria.setResultTransformer(transformer);
        }
        try {
            BeanUtil.setFieldValue(impl, orderEntriesPro, orderEntries);
        } catch (Exception e) {
            throw new RuntimeException("set 'orderEntries' to CriteriaImpl faild", e);
        }
        criteria.setFirstResult((p.getPageNo() - 1) * p.getPageSize());
        criteria.setMaxResults(p.getPageSize());
        p.setRecordList(criteria.list());
        return p;
    }

    /**
     * 根据原生的sql语句进行查询,并将查询结果转换成对应的实体对象返回list集合,
     * @date 2016年4月20日 下午2:04:31
     * @param clazz 实体类
     * @param sql sql语句
     * @param values 参数值
     * @return 结果集合
     */
    public <T> List<T> find(Class<T> clazz, String sql, Object... values) {
        SQLQuery sqlQuery = createSQLQuery(sql, values);
        return sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz)).list();
    }

    /**
     * 根据原生的sql语句进行分页查询
     * @author lee
     * @date 2016年4月20日 下午2:34:25
     * @param clazz 实体类class
     * @param sql 原生的sql语句
     * @param values 参数值
     * @param pageNo 第几页
     * @param pageSize 每页记录
     */
    public <T> Page<T> find(Class<T> clazz, String sql, Object[] values, int pageNo, int pageSize) {
        String listQueryString = sql;
        String countQueryString = " select count(*) " + removeSelect(removeOrders(sql));
        int totalCount = ((Number) createSQLQuery(countQueryString).uniqueResult()).intValue();
        Page<T> p = new Page<T>(pageNo, pageSize, totalCount, null);

        SQLQuery sqlQuery = createSQLQuery(listQueryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                sqlQuery.setParameter(i, values[i]);
            }
        }
        List<T> list = sqlQuery.setFirstResult((p.getPageNo() - 1) * p.getPageSize()).setMaxResults(p.getPageSize())
                .setResultTransformer(Transformers.aliasToBean(clazz)).list();
        p.setRecordList(list);
        return p;
    }

    /**
     * 查询列表数据
     * @datetime 2018年5月25日 下午8:47:20
     */
    public <T> List<T> findByExample(T entity) {
        Assert.notNull(entity, "Example entity must not be null");
        Criteria executableCriteria = createCriteria(entity.getClass());
        executableCriteria.add(Example.create(entity));
        return executableCriteria.list();
    }

    /**
     * 查询分页数据
     * @datetime 2018年5月25日 下午8:12:26
     */
    public <T> Page<T> findByExample(T entity, int pageNo, int pageSize) {
        Assert.notNull(entity, "Example entity must not be null");
        Criteria executableCriteria = createCriteria(entity.getClass());
        executableCriteria.add(Example.create(entity));
        return (Page<T>) find(entity.getClass(), executableCriteria, pageNo, pageSize);
    }

    /**
     * 按属性查找对象列表.
     * @datetime 2018年5月25日 下午5:03:22
     */
    public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (List<T>) createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据原生的sql语句查询List集合,查询的结果不指定具体实体,动态生成实体bean
     * @author lee
     * @date 2016年4月20日 下午2:52:16
     * @param sql
     * @param values
     * @return
     */
    public List<Object> findDynamic(String sql, Object... values) {
        SQLQuery sqlQuery = createSQLQuery(sql, values);
        sqlQuery.setResultTransformer(new DynamicResultTransformer());
        return sqlQuery.list();
    }

    /**
     * 根据原生的sql语句查询Pagination对象
     * @datetime 2017年12月20日 上午1:54:27
     */
    @SuppressWarnings("rawtypes")
    public Page findDynamic(String sql, Object[] values, int pageNo, int pageSize) {
        String listQueryString = sql;
        String countQueryString = " select count(*) " + removeSelect(removeOrders(sql));
        int totalCount = ((Number) createSQLQuery(countQueryString).uniqueResult()).intValue();
        Page p = new Page(pageNo, pageSize, totalCount, null);

        SQLQuery sqlQuery = createSQLQuery(listQueryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                sqlQuery.setParameter(i, values[i]);
            }
        }
        List list = sqlQuery.setFirstResult((p.getPageNo() - 1) * p.getPageSize()).setMaxResults(p.getPageSize())
                .setResultTransformer(new DynamicResultTransformer()).list();
        p.setRecordList(list);
        return p;
    }

    /**
     * 通过HQL查询唯一对象
     * @author lee
     * @date 2016年4月19日 下午3:08:09
     */
    public Object findUnique(String hql, Object... values) {
        return createQuery(hql, values).uniqueResult();
    }

    /**
     * 根据实体名字获取唯一记录
     * @datetime 2018年5月25日 下午5:00:57
     */
    public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * Session.flush()
     * @date 2016年5月7日 下午2:55:34
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * Session.flush()和Session.clear()
     * @datetime 2017年12月18日 下午7:25:38
     */
    public void flushAndClear() {
        flush();
        clear();
    }

    /**
     * 获得Querier的记录总数
     * @datetime 2018年5月19日 下午2:48:14
     */
    public int getQueryCount(Querier querier) {
        Query query = querier.createQueryRowCount(getSession());
        return ((Number) query.iterate().next()).intValue();
    }

    /**
     * 获取记录总数
     * @author lee
     * @date 2016年5月7日 下午4:00:24
     */
    public int getQueryCount(String hql) {
        Query query = createQuery(hql);
        return ((Number) query.iterate().next()).intValue();
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 获取Hibernate实体映射的表名称
     * @date 2016-10-25 下午10:11:44
     * @param clazz Hibernate管理的实体
     * @return 实体映射的表的名称
     */
    public String getTableName(Class<?> clazz) {
        AbstractEntityPersister entityPersister = (AbstractEntityPersister) sessionFactory.getClassMetadata(clazz);
        return entityPersister.getTableName();
    }

    /**
     * 移除查询语句中的order by 子句
     * @author lee
     * @date 2016年4月20日 下午2:21:30
     * @param queryString 查询语句
     * @return
     */
    public static String removeOrders(String queryString) {
        Assert.hasText(queryString);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
        Matcher m = p.matcher(queryString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 移除查询语句中的select子句
     * @author lee
     * @date 2016年4月20日 下午2:22:47
     * @param queryString
     * @return
     */
    public static String removeSelect(String queryString) {
        Assert.hasText(queryString);
        int beginPos = queryString.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " queryString : " + queryString + " must has a keyword 'from'");
        return queryString.substring(beginPos);
    }
}
