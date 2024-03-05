package com.dooleen.service.batch.question.sent.list.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:37:04
 * @Description : 问卷下发题目管理实体
 * @Author : name
 * @Update : 2021-02-22 11:37:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_question_send_subject")
@ApiModel
@ToString(callSuper = true)
public class BizQuestionSendSubjectEntity extends BaseEntity implements Serializable  {

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
	 * 显示序号
	 */
	@DataName(name = "显示序号")
	@ApiModelProperty(value = "显示序号", position = 6)
	@Length(max=10,message="显示序号长度不能大于10")
	@NotBlank(message = "显示序号不能为空")
	private String showSeq;
	
	/**
	* 问卷标题
	*/
	@DataName(name = "问卷标题", isRecordHistory = true)
	@ApiModelProperty(value = "问卷标题", position = 4)
	@Length(max=200,message="问卷标题长度不能大于200")
	@NotBlank(message = "问卷标题不能为空")
	private String questionnaireTitle;	  
   
	
	/**
	* 批次号
	*/
	@DataName(name = "批次号", isRecordHistory = true)
	@ApiModelProperty(value = "批次号", position = 5)
	@Length(max=30,message="批次号长度不能大于30")
	@NotBlank(message = "批次号不能为空")
	private String batchNo;	  
   
	
	/**
	* 填报人
	*/
	@DataName(name = "填报人", isRecordHistory = true)
	@ApiModelProperty(value = "填报人", position = 6)
	@Length(max=50,message="填报人长度不能大于50")
	@NotBlank(message = "填报人不能为空")
	private String fillInUserName;	  
   
	
	/**
	* 填报人姓名
	*/
	@DataName(name = "填报人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "填报人姓名", position = 7)
	@Length(max=50,message="填报人姓名长度不能大于50")
	@NotBlank(message = "填报人姓名不能为空")
	private String fillInRealName;	  
   
	
	/**
	* 题目配置ID
	*/
	@DataName(name = "题目配置ID", isRecordHistory = true)
	@ApiModelProperty(value = "题目配置ID", position = 8)
	@Length(max=20,message="题目配置ID长度不能大于20")
	@NotBlank(message = "题目配置ID不能为空")
	private String subjectConfigId;	  
   
	
	/**
	* 题目标题
	*/
	@DataName(name = "题目标题", isRecordHistory = true)
	@ApiModelProperty(value = "题目标题", position = 9)
	@Length(max=200,message="题目标题长度不能大于200")
	@NotBlank(message = "题目标题不能为空")
	private String subjectTitle;	  
   
	
	/**
	* 题目类型
	*/
	@DataName(name = "题目类型", isRecordHistory = true)
	@ApiModelProperty(value = "题目类型", position = 10)
	@Length(max=30,message="题目类型长度不能大于30")
	@NotBlank(message = "题目类型不能为空")
	private String subjectType;	  
   
	
	/**
	* 题目内容
	*/
	@DataName(name = "题目内容", isRecordHistory = true)
	@ApiModelProperty(value = "题目内容", position = 11)
	@Length(max=100,message="题目内容长度不能大于100")
	private String subjectContent;	  
   
	
	/**
	* 题目答案
	*/
	@DataName(name = "题目答案", isRecordHistory = true)
	@ApiModelProperty(value = "题目答案", position = 12)
	@Length(max=500,message="题目答案长度不能大于500")
	private String subjectAnswer;	  
   
	
	/**
	* 答题结果
	*/
	@DataName(name = "答题结果", isRecordHistory = true)
	@ApiModelProperty(value = "答题结果", position = 13)
	@Length(max=50,message="答题结果长度不能大于50")
	private String answerResult;	  
   
	
	/**
	* 是否必填
	*/
	@DataName(name = "是否必填", isRecordHistory = true)
	@ApiModelProperty(value = "是否必填", position = 14)
	@Length(max=1,message="是否必填长度不能大于1")
	private String isRequired;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 15)
	@Length(max=50,message="项目名称长度不能大于50")
	private String projectName;	  
 }
