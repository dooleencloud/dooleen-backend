package com.dooleen.service.general.biz.demand.entity;

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
 * @CreateDate : 2020-07-03 15:42:10
 * @Description : 业务需求管理实体
 * @Author : name
 * @Update : 2020-07-03 15:42:10
 */
@Data
@TableName("general_biz_demand")
@ApiModel
@ToString(callSuper = true)
public class GeneralBizDemandEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true)
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
	* 需求编号
	*/
	@DataName(name = "需求编号", isRecordHistory = true)
	@ApiModelProperty(value = "需求编号", position = 2)
	@Length(max=30,message="需求编号长度不能大于30")
	private String demandNo;	  
   
	
	/**
	* 需求名称
	*/
	@DataName(name = "需求名称", isRecordHistory = true)
	@ApiModelProperty(value = "需求名称", position = 3)
	@Length(max=50,message="需求名称长度不能大于50")
	private String demandName;	  
   
	
	/**
	* 需求文档名称
	*/
	@DataName(name = "需求文档名称", isRecordHistory = true)
	@ApiModelProperty(value = "需求文档名称", position = 4)
	@Length(max=50,message="需求文档名称长度不能大于50")
	private String demandFileName;	  
   
	
	/**
	* 需求所属用户名
	*/
	@DataName(name = "需求所属用户名", isRecordHistory = true)
	@ApiModelProperty(value = "需求所属用户名", position = 5)
	@Length(max=50,message="需求所属用户名长度不能大于50")
	private String demandBelongUserName;	  
   
	
	/**
	* 需求状态
	*/
	@DataName(name = "需求状态", isRecordHistory = true)
	@ApiModelProperty(value = "需求状态", position = 6)
	@Length(max=30,message="需求状态长度不能大于30")
	private String demandStatus;	
	
 }
