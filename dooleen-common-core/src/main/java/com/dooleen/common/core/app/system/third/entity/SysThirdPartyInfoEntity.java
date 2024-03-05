package com.dooleen.common.core.app.system.third.entity;

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
 * @CreateDate : 2020-08-18 15:47:33
 * @Description : 第三方配置管理实体
 * @Author : name
 * @Update : 2020-08-18 15:47:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_third_party_info")
@ApiModel
@ToString(callSuper = true)
public class SysThirdPartyInfoEntity extends BaseEntity implements Serializable  {

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
	* 类型
	*/
	@DataName(name = "类型")
	@ApiModelProperty(value = "类型", position = 2)
	@Length(max=30,message="类型长度不能大于30")
	@NotBlank(message = "类型不能为空")
	private String type;	  
   
	
	/**
	* 第三方配置内容
	*/
	@DataName(name = "第三方配置内容")
	@ApiModelProperty(value = "第三方配置内容", position = 3)
	private String thirdPartyConfigContent;	  
 }
