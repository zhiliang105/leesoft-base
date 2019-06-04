package com.leeframework.modules.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.leeframework.common.hibernate4.entity.BaseEntity;

/**
 * 系统角色表
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月22日 下午7:57:39
 */
@Entity
@DynamicUpdate
@Table(name = "t_sys_role")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private Boolean isSystem; // 是否系统内置角色
    private Integer sort;
    private String type; // 角色分类

    @Column(unique = true)
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Column(name = "is_system")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public Integer getSort() {
        return sort;
    }

    public String getType() {
        return type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setType(String type) {
        this.type = type;
    }

}
