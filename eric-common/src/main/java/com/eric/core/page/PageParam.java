package com.eric.core.page;

import com.github.pagehelper.IPage;

public class PageParam implements IPage {

    private Integer pageNum = 1;

    private Integer pageSize = 5;

    /**
     * 排序，比如 id desc
     */
    private String orderBy;

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public Integer getPageNum() {
        return pageNum;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public String getOrderBy() {
        return orderBy;
    }

}
