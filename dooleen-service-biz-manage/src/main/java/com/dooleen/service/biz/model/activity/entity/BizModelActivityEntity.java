package com.dooleen.service.biz.model.activity.entity;

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
 * @CreateDate : 2022-01-04 23:09:23
 * @Description : 模型活动管理实体
 * @Author : name
 * @Update : 2022-01-04 23:09:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_model_activity")
@ApiModel(value = "模型活动管理实体")
@ToString(callSuper = true)
public class BizModelActivityEntity  extends BaseEntity implements Serializable  {
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
	* 项目ID
	*/
	@DataName(name = "项目ID", isRecordHistory = true)
	@ApiModelProperty(value = "项目ID", required=false,  position = 2)
	@Length(max=20,message="项目ID长度不能大于20")
	private String projectId;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", required=false,  position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
   
	
	/**
	* 业务渠道
	*/
	@Excel(name ="业务渠道")
	@DataName(name = "业务渠道", isRecordHistory = true)
	@ApiModelProperty(value = "业务渠道", required=true, position = 4)
	@Length(max=50,message="业务渠道长度不能大于50")
	@NotBlank(message = "业务渠道不能为空")
	private String bizChannel;	  
   
	
	/**
	* 活动名称
	*/
	@Excel(name ="活动名称")
	@DataName(name = "活动名称", isRecordHistory = true)
	@ApiModelProperty(value = "活动名称", required=true, position = 5)
	@Length(max=50,message="活动名称长度不能大于50")
	@NotBlank(message = "活动名称不能为空")
	private String activityName;	  
   
	
	/**
	* 活动描述
	*/
	@Excel(name ="活动描述")
	@DataName(name = "活动描述", isRecordHistory = true)
	@ApiModelProperty(value = "活动描述", required=true, position = 6)
	@Length(max=2000,message="活动描述长度不能大于2000")
	@NotBlank(message = "活动描述不能为空")
	private String activityDesc;	  
 }
