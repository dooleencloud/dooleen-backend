package com.dooleen.service.system.group.role.entity;

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
 * @CreateDate : 2020-06-12 06:11:52
 * @Description : 系统用户组角色关系管理实体
 * @Author : name
 * @Update : 2020-06-12 06:11:52
 */
@Data
@TableName("sys_group_role_relation")
@ApiModel
@ToString(callSuper = true)
public class SysGroupRoleRelationEntity extends BaseEntity implements Serializable  {

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
	* 用户组ID
	*/
	@DataName(name = "用户组ID")
	@ApiModelProperty(value = "用户组ID", position = 2)
	@NotBlank(message = "用户组ID不能为空")
	private String userGroupId;	  
   
	/**
	* 角色ID
	*/
	@DataName(name = "角色ID")
	@ApiModelProperty(value = "角色ID", position = 3)
	@NotBlank(message = "角色ID不能为空")
	private String roleId;	  
 }
