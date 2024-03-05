package com.dooleen.common.core.config.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Paging<T> implements Serializable {
    /**
     * 当前页数
     */
    private int page = 1;

    /**
     * 每页条数
     * 此参数为0时，不分页
     */
    private int pageSize = 20;

    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 数据
     */
    private List<T> data;

    public void setPageSize(int pageSize) {
        if (0 < pageSize) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 20;
        }
    }

    /**
     * 指定 page 页数据的起始条目数
     *
     * @return
     */
    public int calculateSkip() {
        return (page - 1) * pageSize;
    }
}
