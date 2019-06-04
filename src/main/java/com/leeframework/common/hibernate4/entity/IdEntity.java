package com.leeframework.common.hibernate4.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 实体的ID映射
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月29日 下午9:59:20
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {
    private static final long serialVersionUID = 4025646643704008605L;

    public final static String ID = "id";

    protected String id; // 实体类的ID

    public IdEntity() {
    }

    public IdEntity(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "id", length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
