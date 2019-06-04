package com.leeframework.common.model.view;

import java.util.List;

/**
 * 分页请求参数接口
 * <p>
 * 为适应前端不用类型的数据表格,后端定义各个表格对应的分页请求参数,在dao层通过接口调用数据查询参数
 * </p>
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年7月17日 下午10:38:30
 */
public interface PagingParameter {

    public static final int DEFAULT_SIZE = 30;

    /**
     * 获取页码参数
     * @datetime 2018年7月17日 下午11:07:08
     */
    public int getPageNumberParam();

    /**
     * 获取每页大小
     * @datetime 2018年7月17日 下午11:10:37
     */
    public int getPageSizeParam();

    /**
     * 获取排序字段
     * @datetime 2018年7月17日 下午11:13:21
     */
    public String getSortNameParam();

    /**
     * 获取排序方式
     * @datetime 2018年7月17日 下午11:13:43
     * @return
     */
    public String getSortOrderParam();

    /**
     * 获取关键字
     * @datetime 2018年7月17日 下午11:14:14
     */
    public String getSearchTextParam();

    /**
     * 获取表格中对应的字段名称
     * @datetime 2018年7月17日 下午11:14:56
     */
    public List<String> getFieldList(Class<?> clazz);
}
