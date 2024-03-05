package com.dooleen.common.core.common.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysHistoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 租户id
    */
    private String tenantId;

    /**
    * 修改数据id
    */
    private String updateDataId;
    
    /**
    * 类名
    */
    private String className;

    /**
    * 修改之前数据对象
    */
    private String updateBeforeDataObj;

    /**
    * 修改之后数据对象
    */
    private String updateAfterDataObj;
    
    /**
    * 修改内容
    */
    private String updateContent;
    
    /**
    * 修改之前来源数据对象
    */
    private String updateBeforeSourceDataObj;

    /**
    * 修改之后来源数据对象
    */
    private String updateAfterSourceDataObj;
    
    /**
    * 修改人用户名
    */
	public String updateUserName;
    
    /**
    * 修改人姓名
    */
	public String updateRealName;
	
    /**
    * 修改时间
    */
	public String updateDatetime;
	
    /**
    * 来源数据签名
    */
	public String sourceDataSign;

    public SysHistoryInfo() {
    }

}
