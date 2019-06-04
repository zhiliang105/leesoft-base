package com.leeframework.common.model.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.leeframework.common.utils.StringUtil;
import com.leeframework.common.utils.clazz.BeanUtil;

/**
 * 表格分页请求参数-bootstrap-table请求参数
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月25日 上午3:28:08
 */
public class BootstrapTableParams implements PagingParameter {

    private Integer pageNumber = 1;
    private Integer pageSize = DEFAULT_SIZE;
    private String sortName;
    private String sortOrder;
    private String searchText; // 关键字查询
    private String fields;// table显示的属性字段多个字段之间用,号隔开

    public BootstrapTableParams() {
    }

    public BootstrapTableParams(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumberParam() {
        return this.pageNumber;
    }

    @Override
    public int getPageSizeParam() {
        return this.pageSize;
    }

    @Override
    public String getSortNameParam() {
        return this.sortName;
    }

    @Override
    public String getSortOrderParam() {
        return this.sortOrder;
    }

    @Override
    public String getSearchTextParam() {
        return this.searchText;
    }

    @Override
    public List<String> getFieldList(Class<?> clazz) {
        List<String> fields = StringUtil.split(this.fields);
        List<String> inFields = new ArrayList<String>();
        for (String f : fields) {
            Field field = BeanUtil.getDeclaredField(clazz, f);
            if (field != null) {
                if (field.getType().equals(String.class)) { // 只匹配字符串类型
                    inFields.add(f);
                }
            }
        }
        return inFields;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

}
