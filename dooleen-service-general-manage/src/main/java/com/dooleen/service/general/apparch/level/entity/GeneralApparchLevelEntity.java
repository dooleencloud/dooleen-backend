package com.dooleen.service.general.apparch.level.entity;

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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-31 16:35:21
 * @Description : 应用架构层级管理实体
 * @Author : name
 * @Update : 2020-08-31 16:35:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_apparch_level")
@ApiModel
@ToString(callSuper = true)
public class GeneralApparchLevelEntity  extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true,  isRecordHistory = true)
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
	* 架构层级
	*/
	@DataName(name = "架构层级", isRecordHistory = true)
	@ApiModelProperty(value = "架构层级", position = 2)
	@Length(max=100,message="架构层级长度不能大于100")
	@NotBlank(message = "架构层级不能为空")
	private String architectureLevel;	  
   
	
	/**
	* 应用群组
	*/
	@DataName(name = "应用群组", isRecordHistory = true)
	@ApiModelProperty(value = "应用群组", position = 3)
	@Length(max=100,message="应用群组长度不能大于100")
	private String appGroup;	  
   
	
	/**
	* 功能组
	*/
	@DataName(name = "功能组", isRecordHistory = true)
	@ApiModelProperty(value = "功能组", position = 4)
	@Length(max=2000,message="功能组长度不能大于2000")
	private String functionGroup;	  
   
	
	/**
	* 所属部门
	*/
	@DataName(name = "所属部门", isRecordHistory = true)
	@ApiModelProperty(value = "所属部门", position = 5)
	@Length(max=30,message="所属部门长度不能大于30")
	private String belongDeptName;	  
   
	
	/**
	* 负责人
	*/
	@DataName(name = "负责人", isRecordHistory = true)
	@ApiModelProperty(value = "负责人", position = 6)
	@Length(max=50,message="负责人长度不能大于50")
	private String respsbUserName;	
	


	/**
	* 负责人名
	*/
	@DataName(name = "负责人名")
	@ApiModelProperty(value = "负责人名", position = 6)
	@Length(max=50,message="负责人名长度不能大于50")
	private String respsbRealName;
 }
