package com.dooleen.common.core.app.system.user.role.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2020-06-07 21:45:18
 * @Description : 系统用户角色关系管理实体
 * @Author : name
 * @Update : 2020-06-07 21:45:18
 */
@Data
@TableName("sys_user_role_relation")
@ApiModel
@ToString(callSuper = true)
public class SysUserRoleRelationEntity extends BaseEntity implements Serializable  {

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
	* 项目ID
	*/
	@DataName(name = "项目ID")
	@ApiModelProperty(value = "项目ID", position = 2)
	@TableField(exist = false)
	private String projectId;	
	
	/**
	* 用户ID
	*/
	@DataName(name = "用户ID")
	@ApiModelProperty(value = "用户ID", position = 2)
	@NotBlank(message = "用户ID不能为空")
	private String userId;	  
   
	/**
	* 角色ID
	*/
	@DataName(name = "角色ID")
	@ApiModelProperty(value = "角色ID", position = 3)
	@NotBlank(message = "角色ID不能为空")
	private String roleId;	
	/**
	* 等级，预留字段
	*/
	@DataName(name = "等级")
	@ApiModelProperty(value = "等级", position = 3)
	private String level;	
 }
