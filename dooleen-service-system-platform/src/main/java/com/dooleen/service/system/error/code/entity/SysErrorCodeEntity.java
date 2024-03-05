package com.dooleen.service.system.error.code.entity;

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
 * @CreateDate : 2021-10-19 10:56:50
 * @Description : 错误码管理实体
 * @Author : name
 * @Update : 2021-10-19 10:56:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_error_code")
@ApiModel(value = "错误码管理实体")
@ToString(callSuper = true)
public class SysErrorCodeEntity  extends BaseEntity implements Serializable  {
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
	* 错误级别
	*/
	@Excel(name ="错误级别")
	@DataName(name = "错误级别", isRecordHistory = true)
	@ApiModelProperty(value = "错误级别", required=true, position = 2)
	@Length(max=30,message="错误级别长度不能大于30")
	@NotBlank(message = "错误级别不能为空")
	private String errorGrade;

	/**
	 * 错误代码
	 */
	@Excel(name ="错误代码")
	@DataName(name = "错误代码", isRecordHistory = true)
	@ApiModelProperty(value = "错误代码", required= true , position = 6)
	@Length(max=100,message="错误代码长度不能大于100")
	@NotBlank(message = "错误代码不能为空")
	private String errorDcode;
	
	/**
	* 错误编码
	*/
	@Excel(name ="错误编码")
	@DataName(name = "错误编码", isRecordHistory = true)
	@ApiModelProperty(value = "错误编码", required=true, position = 3)
	@Length(max=30,message="错误编码长度不能大于30")
	@NotBlank(message = "错误编码不能为空")
	private String errorCode;	  
   
	
	/**
	* 错误信息
	*/
	@Excel(name ="错误信息")
	@DataName(name = "错误信息", isRecordHistory = true)
	@ApiModelProperty(value = "错误信息", required=true, position = 4)
	@Length(max=2000,message="错误信息长度不能大于2000")
	@NotBlank(message = "错误信息不能为空")
	private String errorInfo;

 }
