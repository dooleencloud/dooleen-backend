package com.dooleen.service.biz.question.send.result.entity;

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
 * @CreateDate : 2021-03-26 10:40:07
 * @Description : 问卷下发答题结果实体
 * @Author : name
 * @Update : 2021-03-26 10:40:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_question_send_result")
@ApiModel
@ToString(callSuper = true)
public class BizQuestionSendResultEntity  extends BaseEntity implements Serializable  {

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
	* 项目编号
	*/
	@DataName(name = "项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "项目编号", position = 2)
	@Length(max=30,message="项目编号长度不能大于30")
	@NotBlank(message = "项目编号不能为空")
	private String projectNo;	  
   
	
	/**
	* 问卷ID
	*/
	@DataName(name = "问卷ID", isRecordHistory = true)
	@ApiModelProperty(value = "问卷ID", position = 3)
	@Length(max=20,message="问卷ID长度不能大于20")
	@NotBlank(message = "问卷ID不能为空")
	private String questionnaireId;	  
   
	
	/**
	* 批次号
	*/
	@DataName(name = "批次号", isRecordHistory = true)
	@ApiModelProperty(value = "批次号", position = 4)
	@Length(max=30,message="批次号长度不能大于30")
	@NotBlank(message = "批次号不能为空")
	private String batchNo;	  
   
	
	/**
	* 问卷标题
	*/
	@DataName(name = "问卷标题", isRecordHistory = true)
	@ApiModelProperty(value = "问卷标题", position = 5)
	@Length(max=200,message="问卷标题长度不能大于200")
	private String questionnaireTitle;	  
   
	
	/**
	* 题目类型
	*/
	@DataName(name = "题目类型", isRecordHistory = true)
	@ApiModelProperty(value = "题目类型", position = 6)
	@Length(max=30,message="题目类型长度不能大于30")
	private String subjectType;	  
   
	
	/**
	* 题目答案
	*/
	@DataName(name = "题目答案", isRecordHistory = true)
	@ApiModelProperty(value = "题目答案", position = 7)
	@Length(max=500,message="题目答案长度不能大于500")
	private String subjectAnswer;	  
 }
