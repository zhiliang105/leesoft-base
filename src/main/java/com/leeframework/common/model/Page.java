package com.leeframework.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * @author Lee[lee@leesoft.cn]
 * @datetime 2018年5月25日 上午2:43:19
 */
public  class Page<T> {

    private int pageNo; // 当前页
    private int pageSize; // 每页显示多少条
    private int total; // 总记录数
    private List<T> recordList; // 本页的数据列表
    private int pages; // 总页数
    private int beginPageNo;
    private int endPageNo;

    public Page() {
    }

    /**
     * 将一个全部的数据列表集合包装成一个分页对象,只包装了总记录和数据信息,不包含起始结束页和总页数
     */
    public Page(int pageNo, int pageSize, List<T> totalList) {
        int page = pageNo;
        int rows = pageSize;

        // 从总列表中提取的分页数据列表
        List<T> result = new ArrayList<T>();

        int index = (page - 1) * rows;
        for (int i = index; i < rows + index; i++) {
            if (i < totalList.size()) {
                result.add(totalList.get(i));
            }
        }

        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = totalList.size();
        this.recordList = result;

    }

    /**
     * 将制定的查询结果包装成一个分页对象
     */
    public Page(int pageNo, int pageSize, int total, List<T> recordList) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.recordList = recordList;

        // 计算总页码
        pages = (total + pageSize - 1) / pageSize;

        if (pages <= 10) {
            beginPageNo = 1;
            endPageNo = pages;
        } else {
            beginPageNo = pageNo - 4;
            endPageNo = pageNo + 5;
            if (beginPageNo < 1) {
                beginPageNo = 1;
                endPageNo = 10;
            }
            if (endPageNo > pages) {
                endPageNo = pages;
                beginPageNo = pages - 10 + 1;
            }
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public int getPages() {
        return pages;
    }

    public int getBeginPageNo() {
        return beginPageNo;
    }

    public int getEndPageNo() {
        return endPageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setBeginPageNo(int beginPageNo) {
        this.beginPageNo = beginPageNo;
    }

    public void setEndPageNo(int endPageNo) {
        this.endPageNo = endPageNo;
    }

}
