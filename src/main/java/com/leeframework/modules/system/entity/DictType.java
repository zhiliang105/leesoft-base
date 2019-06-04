package com.leeframework.modules.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.leeframework.common.hibernate4.entity.BaseTreeEntity;

/**
 * 数据字典类型
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月22日 下午6:26:58
 */
@Entity
@DynamicUpdate
@Table(name = "t_sys_dict_type")
public class DictType extends BaseTreeEntity<DictType> {
    private static final long serialVersionUID = 1L;

}
