package com.leeframework.common.hibernate4.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeframework.common.utils.clazz.BeanUtil;

/**
 * 树形结构的实体基础类<br>
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年5月7日 Time: 下午4:59:04
 * @version 1.0
 * @since version 1.0
 * @update
 */
@MappedSuperclass
public abstract class BaseTreeEntity<T> extends BaseEntity {
    private static final long serialVersionUID = -4327746011754328327L;

    public static final String PARENT = "parent";
    public static final String PARENTIDS = "parentIds";
    public static final String ICON = "icon";
    public static final String CHILDREN = "children";

    private String code;
    private String name;
    private Integer sort;
    protected T parent; // 父级编号
    protected String parentIds; // 所有父级编号
    protected Set<T> children = new HashSet<T>(); // 下级节点

    public BaseTreeEntity() {
    }

    public BaseTreeEntity(String id) {
        super(id);
    }

    /**
     * 获取父级节点的ID
     * @datetime 2017年11月29日 下午10:10:09
     */
    public String findParentId() {
        String id = null;
        if (parent != null) {
            id = (String) BeanUtil.getFieldValue(parent, ID);
        }
        return id;
    }

    @Column(nullable = false)
    public String getCode() {
        return code;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    public T getParent() {
        return parent;
    }

    public Integer getSort() {
        return sort;
    }

    @Column(name = "parent_ids")
    public String getParentIds() {
        return parentIds;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 级联删除
    @OrderBy(value = "sort asc")
    public Set<T> getChildren() {
        return children;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public void setChildren(Set<T> children) {
        this.children = children;
    }

}
