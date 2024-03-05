package com.dooleen.service.system.org.user.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-11 10:50:02
 * @Description : 系统组织用户关系管理实体
 * @Author : name
 * @Update : 2020-06-11 10:50:02
 */
@Data
@TableName("sys_org_user_relation")
@ApiModel
@ToString(callSuper = true)
public class SysOrgUserRelationEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键")
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	/**
	* 所属机构号
	*/
	@DataName(name = "所属机构号")
	@ApiModelProperty(value = "所属机构号", position = 2)
	@NotBlank(message = "所属机构号不能为空")
	private String belongOrgNo;	  
   
	/**
	* 所属机构名称
	*/
	@DataName(name = "所属机构名称")
	@ApiModelProperty(value = "所属机构名称", position = 3)
	@NotBlank(message = "所属机构名称不能为空")
	private String belongOrgName;	  
   
	/**
	* 用户ID
	*/
	@DataName(name = "用户ID")
	@ApiModelProperty(value = "用户ID", position = 4)
	@NotBlank(message = "用户ID不能为空")
	private String userId;	  
   
	/**
	* 用户名
	*/
	@DataName(name = "用户名")
	@ApiModelProperty(value = "用户名", position = 5)
	@NotBlank(message = "用户名不能为空")
	private String userName;	  
   
	/**
	* 真实姓名
	*/
	@DataName(name = "真实姓名")
	@ApiModelProperty(value = "真实姓名", position = 6)
	@NotBlank(message = "真实姓名不能为空")
	private String realName;	  
 }
