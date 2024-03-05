package com.dooleen.service.general.biz.category.entity;

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
 * @CreateDate : 2020-08-26 11:12:36
 * @Description : 业务种类管理实体
 * @Author : name
 * @Update : 2020-08-26 11:12:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_biz_category")
@ApiModel
@ToString(callSuper = true)
public class GeneralBizCategoryEntity  extends BaseEntity implements Serializable  {

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
	* 业务条线
	*/
	@DataName(name = "业务条线", isRecordHistory = true)
	@ApiModelProperty(value = "业务条线", position = 2)
	@Length(max=30,message="业务条线长度不能大于30")
	@NotBlank(message = "业务条线不能为空")
	private String bizLines;	  
   
	
	/**
	* 业务板块
	*/
	@DataName(name = "业务板块", isRecordHistory = true)
	@ApiModelProperty(value = "业务板块", position = 3)
	@Length(max=30,message="业务板块长度不能大于30")
	@NotBlank(message = "业务板块不能为空")
	private String bizPlate;	  
   
	
	/**
	* 业务种类
	*/
	@DataName(name = "业务种类", isRecordHistory = true)
	@ApiModelProperty(value = "业务种类", position = 4)
	@Length(max=30,message="业务种类长度不能大于30")
	@NotBlank(message = "业务种类不能为空")
	private String bizCategory;	  
   
	
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
	@DataName(name = "负责人名", isRecordHistory = true)
	@ApiModelProperty(value = "负责人名", position = 7)
	@Length(max=50,message="负责人名长度不能大于50")
	private String respsbRealName;	  
 }
