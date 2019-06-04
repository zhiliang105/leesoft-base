package com.leeframework.common.model.view;

import java.util.List;
import java.util.Map;

import com.leeframework.common.model.Page;

/**
 * 页面表格数据包装<br>
 * bootstrap-table数据包装, 只有设置服务端分页的时候返回此格式才有效<br>
 * easyui-datagrid同样有效
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月25日 上午2:15:05
 */
public class PageData {

    private int total;// 总记录数
    private List<?> rows; // 列表数据
    private Map<String, Object> attributes;// 其他参数

    public PageData() {
    }

    public PageData(Page<?> page) {
        this.total = page.getTotal();
        this.rows = page.getRecordList();
    }

    public int getTotal() {
        return total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
