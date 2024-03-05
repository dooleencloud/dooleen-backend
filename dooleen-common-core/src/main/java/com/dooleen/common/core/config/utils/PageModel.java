package com.dooleen.common.core.config.utils;

import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class PageModel implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private Integer pagenumber = 1;
    private Integer pagesize = 10;
    private Sort sort;

    public Integer getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(Integer pagenumber) {
        this.pagenumber = pagenumber;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
