package com.leeframework.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.leeframework.common.hibernate4.dao.BaseDao;
import com.leeframework.common.hibernate4.entity.IdEntity;
import com.leeframework.common.model.Page;
import com.leeframework.common.model.view.BootstrapTableParams;

/**
 * Service基础业务逻辑处理实现类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月18日 下午7:52:49
 */
@Transactional(readOnly = true)
public abstract class BaseService<E extends IdEntity, D extends BaseDao<E>> extends CommonService {

    protected D baseDao;

    @Autowired
    protected void setBaseDao(D baseDao) {
        this.baseDao = baseDao;
    }

    @Transactional(readOnly = false)
    public void delete(Collection<E> entitys) {
        baseDao.delete(entitys);
    }

    @Transactional(readOnly = false)
    public void delete(E entity) {
        baseDao.delete(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Serializable id) {
        baseDao.delete(id);
    }

    @Transactional(readOnly = false)
    public void delete(Serializable[] ids) {
        baseDao.delete(ids);

    }

    public List<E> find() {
        return baseDao.find();
    }

    public Page<E> find(BootstrapTableParams pageParams) {
        return baseDao.find(pageParams);
    }

    public List<E> findByProperty(String property, Object value) {
        return baseDao.findByProperty(property, value);
    }

    public E findUniqueByProperty(String property, Object value) {
        return baseDao.findUniqueByProperty(property, value);
    }

    public E get(Serializable id) {
        return baseDao.get(id);
    }

    public List<E> get(Serializable[] ids) {
        return baseDao.get(ids);
    }

    public List<E> getList(List<String> ids) {
        return baseDao.getList(ids);
    }

    @Transactional(readOnly = false)
    public Serializable save(E entity) {
        return baseDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(E entity) {
        this.baseDao.saveOrUpdate(entity);
    }

    @Transactional(readOnly = false)
    public void saveOnBatch(List<E> entitys) {
        baseDao.saveOnBatch(entitys);
    }

    @Transactional(readOnly = false)
    public void update(E entity) {
        baseDao.update(entity);
    }

    public boolean validate(String property, String value) {
        return baseDao.validate(property, value);
    }

}
