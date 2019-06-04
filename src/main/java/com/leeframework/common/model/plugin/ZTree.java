package com.leeframework.common.model.plugin;

/**
 * ZTree视图数据节点模型
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年12月18日 下午8:09:26
 */
public class ZTree {

    private Object id;
    private Object pId;
    private String name;
    private boolean open = true;
    private boolean checked;

    public ZTree() {
    }

    public ZTree(Object id, Object pId, String name) {
        this(id, pId, name, true, false);
    }

    public ZTree(Object id, Object pId, String name, boolean open, boolean checked) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.checked = checked;
    }

    public Object getId() {
        return id;
    }

    public Object getpId() {
        return pId;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public void setpId(Object pId) {
        this.pId = pId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
