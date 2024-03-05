package com.dooleen.service.biz.model.activity.task.group.entity;

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
 * @CreateDate : 2022-01-04 23:19:41
 * @Description : 模型活动任务组关系管理实体
 * @Author : name
 * @Update : 2022-01-04 23:19:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_model_activity_task_group")
@ApiModel(value = "模型活动任务组关系管理实体")
@ToString(callSuper = true)
public class BizModelActivityTaskGroupEntity  extends BaseEntity implements Serializable  {
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
	* 活动ID
	*/
	@Excel(name ="活动ID")
	@DataName(name = "活动ID", isRecordHistory = true)
	@ApiModelProperty(value = "活动ID", required=true, position = 2)
	@Length(max=20,message="活动ID长度不能大于20")
	@NotBlank(message = "活动ID不能为空")
	private String activityId;	  
   
	
	/**
	* 任务组ID
	*/
	@Excel(name ="任务组ID")
	@DataName(name = "任务组ID", isRecordHistory = true)
	@ApiModelProperty(value = "任务组ID", required=true, position = 3)
	@Length(max=20,message="任务组ID长度不能大于20")
	@NotBlank(message = "任务组ID不能为空")
	private String taskGroupId;	  
   
	
	/**
	* 排序序号
	*/
	@Excel(name ="排序序号")
	@DataName(name = "排序序号", isRecordHistory = true)
	@ApiModelProperty(value = "排序序号", required=true, position = 4)
    @DecimalMax(value="9999999999",message="排序序号不能大于9999999999")
	@DecimalMin(value="0",message="排序序号不能小于0")
	@NotBlank(message = "排序序号不能为空")
	private int orderSeq;	  
 }
