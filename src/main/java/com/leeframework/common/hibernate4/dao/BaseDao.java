package com.leeframework.common.hibernate4.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.leeframework.common.hibernate4.entity.BaseEntity;
import com.leeframework.common.hibernate4.entity.IdEntity;
import com.leeframework.common.hibernate4.helper.Querier;
import com.leeframework.common.model.Page;
import com.leeframework.common.model.view.PagingParameter;
import com.leeframework.common.utils.Assert;
import com.leeframework.common.utils.StringUtil;
import com.leeframework.common.utils.SystemUtil;
import com.leeframework.common.utils.clazz.BeanUtil;
import com.leeframework.common.utils.clazz.GenericsUtil;
import com.leeframework.core.exception.DaoRuntimeException;

/**
 * HIBERNATE基础类型处理DAO支持类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月19日 下午2:51:34
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<E extends IdEntity> extends CommonDao {
    private Class<?> entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());

    public void delete(Collection<E> entitys) {
        if (entitys != null) {
            try {
                for (E entity : entitys) {
                    getSession().delete(entity);
                    flush();
                }
                log.debug("Delete entity collection {} successfully!", entityClass.getSimpleName());
            } catch (Exception e) {
                log.error("Failed to delete entity collection {}", entityClass.getSimpleName());
                throw new DaoRuntimeException(e);
            }
        }

    }

    public void delete(E entity) {
        try {
            getSession().delete(entity);
            flush();
            log.debug("Delete entity {} successfully!", entityClass.getSimpleName());
        } catch (Exception e) {
            log.error("Failed to delete entity {}", entityClass.getSimpleName());
            throw new DaoRuntimeException(e);
        }
    }

    public void delete(Serializable id) {
        E e = get(id);
        if (e != null) {
            try {
                getSession().delete(e);
                flush();
                log.debug("Delete entity {} successful", entityClass.getSimpleName());
            } catch (HibernateException e1) {
                log.error("Failed to delete entity {}", entityClass.getSimpleName());
                throw new DaoRuntimeException(e1);
            }
        } else {
            log.warn("According to the ID for the entity is empty, do not delete");
        }
    }

    public void delete(Serializable[] ids) {
        if (ids != null) {
            int length = ids.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    delete(ids[i]);
                }
                flush();
            }
        }
    }

    public List<E> find() {
        return find(false);
    }

    public List<E> find(boolean cacheable) {
        return createCriteria().setCacheable(cacheable).list();
    }

    public Page<E> find(Criteria criteria, PagingParameter pageParams) {
        return find(criteria, pageParams, false);
    }

    public Page<E> find(Criteria criteria, PagingParameter pageParams, boolean cacheable) {
        addQueryParams(criteria, pageParams);
        criteria.setCacheable(cacheable);
        return (Page<E>) super.find(entityClass, criteria, pageParams.getPageNumberParam(), pageParams.getPageSizeParam());
    }

    public Page<E> find(PagingParameter pageParams) {
        return find(pageParams, false);
    }

    public Page<E> find(PagingParameter pageParams, boolean cacheable) {
        return find(createCriteria(), pageParams, cacheable);
    }

    public List<E> find(Querier querier) {
        return querier.createQuery(getSession()).list();
    }

    public List<E> find(Criteria criteria) {
        return criteria.list();
    }

    public Page<E> find(Querier querier, int pageNo, int pageSize) {
        int totalCount = getQueryCount(querier);
        Page<E> p = new Page<E>(pageNo, pageSize, totalCount, null);
        if (totalCount < 1) {
            p.setRecordList(Collections.emptyList());
            return p;
        }
        Query query = querier.createQuery(getSession());
        query.setFirstResult((p.getPageNo() - 1) * p.getPageSize());
        query.setMaxResults(p.getPageSize());
        List<E> list = query.list();
        p.setRecordList(list);
        return p;
    }

    public List<E> find(String hql, Object... values) {
        Assert.hasText(hql);
        return createQuery(hql, values).list();
    }

    public List<E> findByCriteria(Criterion... criterion) {
        return createCriteria(entityClass, criterion).list();
    }

    public List<E> findByProperty(String property, Object value) {
        Assert.hasText(property);
        return createCriteria(entityClass, Restrictions.eq(property, value)).list();
    }

    public E findUniqueByProperty(String property, Object value) {
        return (E) super.findUniqueByProperty(entityClass, property, value);
    }

    public E get(Serializable id) {
        return (E) createCriteria(entityClass, Restrictions.eq("id", id)).uniqueResult();
    }

    public List<E> get(Serializable[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }
        return createCriteria(entityClass, Restrictions.in("id", ids)).list();
    }

    public List<E> getList(List<String> ids) {
        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }
        return this.get(ids.toArray(new String[ids.size()]));
    }

    /**
     * 获取Class
     * @author lee
     * @date 2016年5月7日 下午3:41:19
     * @return
     */
    public final Class<?> getEntityClass() {
        return entityClass;
    }

    /**
     * 获取实体名称
     * @author lee
     * @date 2016年5月7日 下午3:41:48
     * @return
     */
    public final String getEntityName() {
        return entityClass.getSimpleName();
    }

    public E load(Serializable id) {
        return (E) getSession().load(entityClass, id);
    }

    public Serializable save(E entity) {
        try {
            Serializable id = getSession().save(initIdValue(entity));
            flush();
            log.debug("Save entity {} successfully!", entityClass.getSimpleName());
            return id;
        } catch (Exception e) {
            log.error("Failed to save entity {}", entityClass.getSimpleName());
            throw new DaoRuntimeException(e);
        }
    }

    public void saveOnBatch(List<E> entitys) {
        if (entitys != null) {
            try {
                for (int i = 0, size = entitys.size(); i < size; i++) {
                    getSession().save(initIdValue(entitys.get(i)));
                    if (i % 500 == 0) {
                        flush();
                    }
                }
                flush();
                log.debug("Batch saved [{}] successfully!", entityClass.getSimpleName());

            } catch (Exception e) {
                log.error("Failed to Batch saved {}", entityClass.getSimpleName());
                throw new DaoRuntimeException(e);
            }
        }
    }

    public void saveOrUpdate(E entity) {
        try {
            getSession().saveOrUpdate(entity);
            flush();
            log.debug("SaveOrUpdate entity {} successfully!", entityClass.getSimpleName());
        } catch (Exception e) {
            log.error("Failed to saveOrUpdate entity {}", entityClass.getSimpleName());
            throw new DaoRuntimeException(e);
        }
    }

    public void update(E entity) {
        try {
            getSession().update(entity);
            flush();
            log.debug("Update the entity {} successfully!", entityClass.getSimpleName());
        } catch (Exception e) {
            log.error("Failed to update entity {}", entityClass.getSimpleName());
            throw new DaoRuntimeException(e);
        }
    }

    public boolean validate(String property, String value) {
        Querier querier = Querier.create("select * from " + entityClass.getSimpleName() + " bean ");
        querier.append(" where bean." + property + "=:p", "p", value);
        int records = ((Number) querier.createQueryRowCount(getSession()).uniqueResult()).intValue();
        return records > 0;
    }

    /*
     * 解析查询条件
     */
    protected void addQueryParams(Criteria criteria, PagingParameter pageParams) {
        String searchText = pageParams.getSearchTextParam();
        if (StringUtil.isNotEmpty(searchText)) {
            List<String> fields = pageParams.getFieldList(entityClass);
            int size = fields.size();
            if (size > 0) {
                Disjunction dis = Restrictions.disjunction();
                for (int i = 0; i < size; i++) {
                    String filed = fields.get(i);
                    dis.add(Restrictions.like(filed, searchText, MatchMode.ANYWHERE));
                }
                criteria.add(dis);
            }
        }

        String sortName = pageParams.getSortNameParam();
        if (StringUtil.isNotEmpty(sortName)) {
            String sortOrder = pageParams.getSortOrderParam();
            if (StringUtil.isNotEmpty(sortOrder)) {
                if (sortOrder.trim().equals("asc")) {
                    criteria.addOrder(Order.asc(sortName));
                } else if (sortOrder.trim().equals("desc")) {
                    criteria.addOrder(Order.desc(sortName));
                }
            }
        }
    }

    protected Criteria createCriteria() {
        return createCriteria(entityClass);
    }

    /**
     * 初始化ID值,UUID
     * @datetime 2017年12月18日 下午3:28:19
     */
    private E initIdValue(E entity) {
        String id = BaseEntity.ID;
        Object idValue = BeanUtil.getFieldValue(entity, id);
        if (idValue == null) {
            BeanUtil.setFieldValue(entity, id, SystemUtil.getUUID());
        }
        return entity;
    }

}
