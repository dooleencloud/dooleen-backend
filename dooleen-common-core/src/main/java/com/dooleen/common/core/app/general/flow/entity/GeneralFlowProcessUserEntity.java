package com.dooleen.common.core.app.general.flow.entity;

import java.io.Serializable;

import com.dooleen.common.core.common.entity.BaseEntity;

import lombok.Data;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-01 15:29:17
 * @Description : 流程处理记录管理实体
 * @Author : name
 * @Update : 2020-07-01 15:29:17
 */
@Data
@ToString(callSuper = true)
public class GeneralFlowProcessUserEntity extends BaseEntity implements Serializable  {
    /**
    * id
    */
	public String userName; 
	
	/**
	* 租户ID
	*/
	public String realName;
   	  
 }
