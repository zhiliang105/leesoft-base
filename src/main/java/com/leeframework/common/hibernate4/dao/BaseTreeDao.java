package com.leeframework.common.hibernate4.dao;

import java.util.List;

import com.leeframework.common.hibernate4.entity.BaseTreeEntity;
import com.leeframework.common.hibernate4.helper.Querier;

/**
 * HIBERNATE树形处理DAO支持类
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月14日 Time: 下午4:49:27
 * @version 1.0
 * @since version 1.0
 * @update
 */
public abstract class BaseTreeDao<E extends BaseTreeEntity<E>> extends BaseDao<E> {

    public List<E> findTree(boolean cacheable) {
        Querier querier = Querier.create("from " + getEntityName() + " bean order by bean.sort").setCacheable(cacheable);
        return find(querier);
    }
}
