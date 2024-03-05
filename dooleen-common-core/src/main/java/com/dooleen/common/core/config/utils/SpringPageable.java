package com.dooleen.common.core.config.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class SpringPageable implements Pageable, Serializable {
    private static final long serialVersionUID = -3258839839160856613L;

    private PageModel page;

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }


    @Override
    public int getPageNumber() {
        return page.getPagenumber();
    }

    @Override
    public int getPageSize() {
        return page.getPagesize();
    }

    @Override
    public long getOffset() {
        return (page.getPagenumber() - 1) * page.getPagesize();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
