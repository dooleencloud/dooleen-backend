package com.dooleen.service.general.project.privilege.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
 * @CreateDate : 2020-08-07 11:20:39
 * @Description : 项目权限关系表实体
 * @Author : name
 * @Update : 2020-08-07 11:20:39
 */
@Data
@TableName("general_project_privilege_relation")
@ApiModel
@ToString(callSuper = true)
public class GeneralProjectPrivilegeRelationEntity extends BaseEntity implements Serializable  {

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
	@Length(max=20,message="项目ID长度不能大于20")
	@NotBlank(message = "项目ID不能为空")
	private String projectId;	  
   
	
	/**
	* 资源类型
	*/
	@DataName(name = "资源类型")
	@ApiModelProperty(value = "资源类型", position = 3)
	@Length(max=10,message="资源类型长度不能大于10")
	@NotBlank(message = "资源类型不能为空")
	private String resourceType;	  
   
	
	/**
	* 资源ID
	*/
	@DataName(name = "资源ID")
	@ApiModelProperty(value = "资源ID", position = 4)
	@Length(max=20,message="资源ID长度不能大于20")
	@NotBlank(message = "资源ID不能为空")
	private String resourceId;	  
 }
