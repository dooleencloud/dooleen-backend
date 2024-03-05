	package com.dooleen.service.general.smart.report.catalog.entity;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
 * @CreateDate : 2020-08-05 14:49:49
 * @Description : 报表目录管理实体
 * @Author : name
 * @Update : 2020-08-05 14:49:49
 */
@Data
@TableName("general_report_catalog")
@ApiModel
@ToString(callSuper = true)
public class GeneralReportCatalogEntity extends BaseEntity implements Serializable  {

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
	* 目录名称
	*/
	@DataName(name = "目录名称")
	@ApiModelProperty(value = "目录名称", position = 2)
	@Length(max=50,message="目录名称长度不能大于50")
	private String catalogName;	  
   
	
	/**
	* 上级目录ID
	*/
	@DataName(name = "上级目录ID")
	@ApiModelProperty(value = "上级目录ID", position = 3)
	@Length(max=20,message="上级目录ID长度不能大于20")
	private String parentCatalogId;	  
   
	
	/**
	* 上级目录名称
	*/
	@DataName(name = "上级目录名称")
	@ApiModelProperty(value = "上级目录名称", position = 4)
	@Length(max=50,message="上级目录名称长度不能大于50")
	private String parentCatalogName;	  
   
	
	/**
	* 拥有人ID
	*/
	@DataName(name = "拥有人ID")
	@ApiModelProperty(value = "拥有人ID", position = 5)
	@Length(max=20,message="拥有人ID长度不能大于20")
	private String ownerId;	  
   
	
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号")
	@ApiModelProperty(value = "排序序号", position = 6)
    @DecimalMax(value="9999999999",message="排序序号不能大于9999999999")
	@DecimalMin(value="0",message="排序序号不能小于0")
	private int orderSeq;	  
	
	/**
	* 读取标志
	*/
	@TableField(exist = false)
	@DataName(name = "读取标志")
	@ApiModelProperty(value = "读取标志", position = 6)
	private String readFlag;
 }
