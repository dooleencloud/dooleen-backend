package com.dooleen.service.system.error.log.entity;

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
 * @CreateDate : 2021-05-07 21:34:34
 * @Description : 错误日志管理实体
 * @Author : name
 * @Update : 2021-05-07 21:34:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_error_log")
@ApiModel
@ToString(callSuper = true)
public class SysErrorLogEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true,  isRecordHistory = true)
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	public String id;
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 1)
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	
	/**
	* 错误类型
	*/
	@Excel(name ="错误类型")
	@DataName(name = "错误类型", isRecordHistory = true)
	@ApiModelProperty(value = "错误类型", position = 2)
	@Length(max=30,message="错误类型长度不能大于30")
	@NotBlank(message = "错误类型不能为空")
	private String errorType;	  
   
	
	/**
	* 错误地址
	*/
	@Excel(name ="错误地址")
	@DataName(name = "错误地址", isRecordHistory = true)
	@ApiModelProperty(value = "错误地址", position = 3)
	@Length(max=100,message="错误地址长度不能大于100")
	@NotBlank(message = "错误地址不能为空")
	private String errorAddress;	  
   
	
	/**
	* 错误摘要
	*/
	@Excel(name ="错误摘要")
	@DataName(name = "错误摘要", isRecordHistory = true)
	@ApiModelProperty(value = "错误摘要", position = 4)
	@Length(max=500,message="错误摘要长度不能大于500")
	private String errorSummary;	  
   
	
	/**
	* 错误内容
	*/
	@Excel(name ="错误内容")
	@DataName(name = "错误内容", isRecordHistory = true)
	@ApiModelProperty(value = "错误内容", position = 5)
	private String errorContent;
   
	
	/**
	* 错误时间
	*/
	@Excel(name ="错误时间")
	@DataName(name = "错误时间", isRecordHistory = true)
	@ApiModelProperty(value = "错误时间", position = 6)
	@Length(max=100,message="错误时间长度不能大于100")
	private String errorDatetime;	  
 }
