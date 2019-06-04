package com.leeframework.common.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.leeframework.common.hibernate4.dao.BaseTreeDao;
import com.leeframework.common.hibernate4.entity.BaseTreeEntity;
import com.leeframework.common.model.plugin.ZTree;

/**
 * 树形结构的实体基础处理业务实现
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月18日 下午8:08:00
 */
@Transactional(readOnly = true)
public abstract class BaseTreeService<E extends BaseTreeEntity<E>, D extends BaseTreeDao<E>> extends BaseService<E, D> {

    public List<ZTree> convert() {
        return convert(null);
    }

    public List<ZTree> convert(ZTree root, String... properties) {
        List<ZTree> tree = new ArrayList<ZTree>();

        // TODO 结合实际的页面视图控件,组装视图数据

        return tree;
    }

    /**
     * 重写save方法
     */
    @Transactional(readOnly = false)
    @Override
    public Serializable save(E entity) {
        return super.save(resetEntityParentIds(entity));
    }

    /**
     * 重写update方法
     */
    @Transactional(readOnly = false)
    @Override
    public void update(E entity) {
        super.update(resetEntityParentIds(entity));
    }

    /**
     * 添加获取修改树形结构实体,重置其父节点数据
     * @datetime 2018年6月18日 上午11:43:29
     */
    private E resetEntityParentIds(E entity) {
        if (entity.getParent() == null || entity.findParentId() == null) {
            entity.setParentIds("0,");
        } else {
            E parent = super.get(entity.findParentId());
            entity.setParent(parent);
            String parentIds = parent.getParentIds() + entity.getParent().getId() + ",";
            entity.setParentIds(parentIds);
        }
        return entity;
    }

}
