package com.leeframework.modules.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeframework.common.hibernate4.entity.BaseTreeEntity;

/**
 * 组织机构表
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月29日 下午10:18:19
 */
@Entity
@DynamicUpdate
@Table(name = "t_sys_origanization")
public class Origanization extends BaseTreeEntity<Origanization> {
    private static final long serialVersionUID = 1L;

    private String type;
    private String address;
    private Long mainManagerId;// 主要负责人ID
    private String mainManagerName;// 主要负责人姓名
    private Long assistManagerId;// 副负责人ID
    private String assistManagerName;// 副负责人姓名

    private Set<User> users = new HashSet<User>();

    public Origanization() {
    }

    public Origanization(String id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    @Column(name = "mainManager_id")
    public Long getMainManagerId() {
        return mainManagerId;
    }

    @Column(name = "mainManager_name")
    public String getMainManagerName() {
        return mainManagerName;
    }

    @Column(name = "assistManager_id")
    public Long getAssistManagerId() {
        return assistManagerId;
    }

    @Column(name = "assistManager_name")
    public String getAssistManagerName() {
        return assistManagerName;
    }

    /**
     * 一对多关联,如果需要级联删除的,可以用mappedBy属性指定关系的维护方<br>
     * 如果级联不删除,清除外键(比如删除部门,设置用户表的部门外键为空),则用@JoinColumn设置,因为用mappedBy属性的话删除部门表报错,除非级联删除用户表
     * @datetime 2018年6月21日 下午12:31:16
     */
    // @OneToMany(mappedBy = "origanization", fetch = FetchType.LAZY)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    @OrderBy(value = "userName asc")
    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMainManagerId(Long mainManagerId) {
        this.mainManagerId = mainManagerId;
    }

    public void setMainManagerName(String mainManagerName) {
        this.mainManagerName = mainManagerName;
    }

    public void setAssistManagerId(Long assistManagerId) {
        this.assistManagerId = assistManagerId;
    }

    public void setAssistManagerName(String assistManagerName) {
        this.assistManagerName = assistManagerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
