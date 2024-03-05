package com.dooleen.service.general.product.service.entity;

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
 * @CreateDate : 2020-08-26 11:17:40
 * @Description : 产品及内部服务管理实体
 * @Author : name
 * @Update : 2020-08-26 11:17:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_product_service")
@ApiModel
@ToString(callSuper = true)
public class GeneralProductServiceEntity  extends BaseEntity implements Serializable  {

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
	* 类别
	*/
	@DataName(name = "类别", isRecordHistory = true)
	@ApiModelProperty(value = "类别", position = 2)
	@Length(max=30,message="类别长度不能大于30")
	@NotBlank(message = "类别不能为空")
	private String category;	  
   
	
	/**
	* 代码
	*/
	@DataName(name = "代码", isRecordHistory = true)
	@ApiModelProperty(value = "代码", position = 3)
	@Length(max=30,message="代码长度不能大于30")
	@NotBlank(message = "代码不能为空")
	private String dcode;	  
   
	
	/**
	* 名称
	*/
	@DataName(name = "名称", isRecordHistory = true)
	@ApiModelProperty(value = "名称", position = 4)
	@Length(max=50,message="名称长度不能大于50")
	@NotBlank(message = "名称不能为空")
	private String name;	  
   
	
	/**
	* 业务条线
	*/
	@DataName(name = "业务条线", isRecordHistory = true)
	@ApiModelProperty(value = "业务条线", position = 5)
	@Length(max=30,message="业务条线长度不能大于30")
	@NotBlank(message = "业务条线不能为空")
	private String bizLines;	  
   
	
	/**
	* 业务板块
	*/
	@DataName(name = "业务板块", isRecordHistory = true)
	@ApiModelProperty(value = "业务板块", position = 6)
	@Length(max=30,message="业务板块长度不能大于30")
	@NotBlank(message = "业务板块不能为空")
	private String bizPlate;	  
   
	
	/**
	* 业务种类
	*/
	@DataName(name = "业务种类", isRecordHistory = true)
	@ApiModelProperty(value = "业务种类", position = 7)
	@Length(max=30,message="业务种类长度不能大于30")
	@NotBlank(message = "业务种类不能为空")
	private String bizCategory;	  
   
	
	/**
	* 客户类型
	*/
	@DataName(name = "客户类型", isRecordHistory = true)
	@ApiModelProperty(value = "客户类型", position = 8)
	@Length(max=30,message="客户类型长度不能大于30")
	@NotBlank(message = "客户类型不能为空")
	private String custType;	  
   
	
	/**
	* 所属部门
	*/
	@DataName(name = "所属部门", isRecordHistory = true)
	@ApiModelProperty(value = "所属部门", position = 9)
	@Length(max=30,message="所属部门长度不能大于30")
	private String belongDeptName;	  
   
	
	/**
	* 负责人
	*/
	@DataName(name = "负责人", isRecordHistory = true)
	@ApiModelProperty(value = "负责人", position = 10)
	@Length(max=50,message="负责人长度不能大于50")
	private String respsbUserName;	  
   
	
	/**
	* 负责人名
	*/
	@DataName(name = "负责人名", isRecordHistory = true)
	@ApiModelProperty(value = "负责人名", position = 11)
	@Length(max=50,message="负责人名长度不能大于50")
	private String respsbRealName;	  
   
	
	/**
	* 生效日期
	*/
	@DataName(name = "生效日期", isRecordHistory = true)
	@ApiModelProperty(value = "生效日期", position = 12)
	@Length(max=20,message="生效日期长度不能大于20")
	private String effectDate;	  
   
	
	/**
	* 失效日期
	*/
	@DataName(name = "失效日期", isRecordHistory = true)
	@ApiModelProperty(value = "失效日期", position = 13)
	@Length(max=20,message="失效日期长度不能大于20")
	private String invalidDate;	  
 }
