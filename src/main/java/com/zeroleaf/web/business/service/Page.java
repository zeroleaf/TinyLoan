package com.zeroleaf.web.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/5.
 */
public class Page<T> {

    private int  pageNumber;
    private int  pageSize;
    private long totalNumber;

    private List<T> content = new ArrayList<>();

    public Page(int pageNumber, int pageSize, long totalNumber) {
        this.pageNumber  = pageNumber;
        this.pageSize    = pageSize;
        this.totalNumber = totalNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void addContent(Collection<T> tCollection) {
        content.addAll(tCollection);
    }
}
