package com.dooleen.common.core.common.entity;


import java.util.Date;

import com.dooleen.common.core.aop.annos.DataName;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class HistoryLog {
    private static final long serialVersionUID = 1L;
    //
    @DataName(name = "")
    private Integer id;


    @DataName(name = "操作人")
    private String username;

    //操作日期
    @DataName(name = "操作日期")
    private Date modifyDate;

    //操作名词
    @DataName(name = "操作名词")
    private String modifyName;

    //操作对象
    @DataName(name = "操作对象")
    private String modifyObject;

    //操作内容

    @DataName(name = "操作内容")
    private String modifyContent;

    //ip

    @DataName(name = "IP")
    private String modifyIp;

    private Object oldObject;

    private Object newObject; 
}