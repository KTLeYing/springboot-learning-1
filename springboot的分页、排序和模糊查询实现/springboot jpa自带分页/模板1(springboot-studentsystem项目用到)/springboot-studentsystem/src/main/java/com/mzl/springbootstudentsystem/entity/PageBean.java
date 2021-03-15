package com.mzl.springbootstudentsystem.entity;

import java.util.List;

/**
 * @ClassName :   PageBean
 * @Description: 分页实体类封装
 * @Author: 21989
 * @CreateDate: 2020/7/26 18:13
 * @Version: 1.0
 */
public class PageBean<T> {

    private Integer curPage;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private Integer start;
    private List<T> pageList;

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getStart() {
        return start;
    }

    public List<T> getPageList() {
        return pageList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", start=" + start +
                ", pageList=" + pageList +
                '}';
    }
}
