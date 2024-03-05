package com.dooleen.service.system.org.info.entity;

import java.io.Serializable;
import java.util.List;

import com.dooleen.common.core.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 00:05:24
 * @Description : 组织机构管理实体
 * @Author : name
 * @Update : 2020-06-04 00:05:24
 */
@Data
@ApiModel
@ToString(callSuper = true)
public class SysOrgInfoVO extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;
	/**
	* id
	*/
	private String id;
	
	/**
	* 机构号
	*/
	private String tenantId;	  
	
	/**
	* 机构号
	*/
	private String orgNo;	  
  
	/**
	* 机构名称
	*/
	private String orgName;	  
   
	/**
	* 上级机构ID
	*/
	private String parentOrgNo;	  
	
	/**
	* 是否有子节点
	*/
	private List<SysOrgInfoVO> children;	 
	
	/**
	* 是否有子节点
	*/
	private String hasChildren;	 
	
 }