package com.leeframework.common.hibernate4.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 实体通用属性
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月29日 下午10:01:19
 */
@MappedSuperclass
public abstract class BaseEntity extends IdEntity implements Serializable {
    private static final long serialVersionUID = 4025646643704008605L;

    public final static String ID = "id";
    public final static String REMARK = "remark";
    public final static String ENABLE = "enable";
    public final static String CANDELETE = "canDelete";
    public final static String CREATE_TIME = "createTime";
    public final static String CREATE_ID = "createId";
    public final static String CREATE_NAME = "createName";
    public final static String UPDATE_TIME = "updateTime";
    public final static String UPDATE_ID = "updateId";
    public final static String UPDATE_NAME = "updateName";

    protected Date createTime; // 创建时间
    protected String createId;// 创建人ID
    protected String createName;// 创建人
    protected Date updateTime;// 更新时间
    protected String updateId;// 更新人ID
    protected String updateName;// 更新人
    protected String remark;// 描述
    protected Boolean enable = true;// 是否可用,只有为false的时候不可用
    protected Boolean canDelete = true;// 是否能被删除,只有为false的时候不能被删除

    public BaseEntity() {
    }

    public BaseEntity(String id) {
        super(id);
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Column(name = "create_id")
    public String getCreateId() {
        return createId;
    }

    @Column(name = "create_name")
    public String getCreateName() {
        return createName;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Column(name = "update_id")
    public String getUpdateId() {
        return updateId;
    }

    @Column(name = "update_name")
    public String getUpdateName() {
        return updateName;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    @Column(name = "enable")
    public Boolean getEnable() {
        return enable;
    }

    @Column(name = "can_delete")
    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

}
