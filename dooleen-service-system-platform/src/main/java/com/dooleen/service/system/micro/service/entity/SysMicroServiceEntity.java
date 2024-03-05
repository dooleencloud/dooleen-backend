package com.dooleen.service.system.micro.service.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * @CreateDate : 2021-11-24 20:25:17
 * @Description : 微服务管理实体
 * @Author : name
 * @Update : 2021-11-24 20:25:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_micro_service")
@ApiModel(value = "微服务管理实体")
@ToString(callSuper = true)
public class SysMicroServiceEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true, isRecordHistory = true)
    @ApiModelProperty(value = "id", required=true, position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", required=true, position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;


	/**
	 * 所属应用ID
	 */
	@Excel(name ="所属应用ID")
	@DataName(name = "所属应用ID", isRecordHistory = true)
	@ApiModelProperty(value = "所属应用ID", required= true , position = 6)
	@Length(max=20,message="所属应用ID长度不能大于20")
	@NotBlank(message = "所属应用ID不能为空")
	private String belongAppId;

	
	/**
	* 微服务编码
	*/
	@Excel(name ="微服务编码")
	@DataName(name = "微服务编码", isRecordHistory = true)
	@ApiModelProperty(value = "微服务编码", required=true, position = 2)
	@Length(max=50,message="微服务编码长度不能大于50")
	@NotBlank(message = "微服务编码不能为空")
	private String microServiceCode;	  
   
	
	/**
	* 微服务名称
	*/
	@Excel(name ="微服务名称")
	@DataName(name = "微服务名称", isRecordHistory = true)
	@ApiModelProperty(value = "微服务名称", required=true, position = 3)
	@Length(max=50,message="微服务名称长度不能大于50")
	@NotBlank(message = "微服务名称不能为空")
	private String microServiceName;	  
   
	
	/**
	* 微服务描述
	*/
	@Excel(name ="微服务描述")
	@DataName(name = "微服务描述", isRecordHistory = true)
	@ApiModelProperty(value = "微服务描述", required=true, position = 4)
	@Length(max=2000,message="微服务描述长度不能大于2000")
	@NotBlank(message = "微服务描述不能为空")
	private String microServiceDesc;	  
   
	
	/**
	* 工程包名
	*/
	@Excel(name ="工程包名")
	@DataName(name = "工程包名", isRecordHistory = true)
	@ApiModelProperty(value = "工程包名", required=true, position = 5)
	@Length(max=50,message="工程包名长度不能大于50")
	@NotBlank(message = "工程包名不能为空")
	private String projectPackageName;	  
   
	
	/**
	* 工程路径
	*/
	@Excel(name ="工程路径")
	@DataName(name = "工程路径", isRecordHistory = true)
	@ApiModelProperty(value = "工程路径", required=true, position = 6)
	@Length(max=500,message="工程路径长度不能大于500")
	@NotBlank(message = "工程路径不能为空")
	private String projectPath;	  
   
	
	/**
	* 工程版本号
	*/
	@Excel(name ="工程版本号")
	@DataName(name = "工程版本号", isRecordHistory = true)
	@ApiModelProperty(value = "工程版本号", required=true, position = 7)
	@Length(max=30,message="工程版本号长度不能大于30")
	@NotBlank(message = "工程版本号不能为空")
	private String projectVersionNo;	  
 }
