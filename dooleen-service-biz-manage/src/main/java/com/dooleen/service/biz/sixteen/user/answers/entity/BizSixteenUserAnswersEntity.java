package com.dooleen.service.biz.sixteen.user.answers.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
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
 * @CreateDate : 2021-05-08 21:47:19
 * @Description : 用户答题结果管理实体
 * @Author : name
 * @Update : 2021-05-08 21:47:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sixteen_user_answers")
@ApiModel
@ToString(callSuper = true)
public class BizSixteenUserAnswersEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
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
	* 题目编号
	*/
	@Excel(name ="题目编号")
	@DataName(name = "题目编号", isRecordHistory = true)
	@ApiModelProperty(value = "题目编号", position = 2)
    @DecimalMax(value="9999999999",message="题目编号不能大于9999999999")
	@DecimalMin(value="0",message="题目编号不能小于0")
	@NotBlank(message = "题目编号不能为空")
	private int topicNum;	  
   
	
	/**
	* 用户名
	*/
	@Excel(name ="用户名")
	@DataName(name = "用户名", isRecordHistory = true)
	@ApiModelProperty(value = "用户名", position = 3)
	@Length(max=50,message="用户名长度不能大于50")
	@NotBlank(message = "用户名不能为空")
	private String userName;	  
   
	
	/**
	* 用户姓名
	*/
	@Excel(name ="用户姓名")
	@DataName(name = "用户姓名", isRecordHistory = true)
	@ApiModelProperty(value = "用户姓名", position = 4)
	@Length(max=50,message="用户姓名长度不能大于50")
	@NotBlank(message = "用户姓名不能为空")
	private String realName;	  
   
	
	/**
	* 性别
	*/
	@Excel(name ="性别")
	@DataName(name = "性别", isRecordHistory = true)
	@ApiModelProperty(value = "性别", position = 5)
	@Length(max=10,message="性别长度不能大于10")
	private String sex;	  
   
	
	/**
	* 年龄
	*/
	@Excel(name ="年龄")
	@DataName(name = "年龄", isRecordHistory = true)
	@ApiModelProperty(value = "年龄", position = 6)
    @DecimalMax(value="9999999999",message="年龄不能大于9999999999")
	@DecimalMin(value="0",message="年龄不能小于0")
	private int age;	  
   
	
	/**
	* 因子ID
	*/
	@Excel(name ="因子ID")
	@DataName(name = "因子ID", isRecordHistory = true)
	@ApiModelProperty(value = "因子ID", position = 7)
	@Length(max=10,message="因子ID长度不能大于10")
	private String factorId;	  
   
	
	/**
	* 题目内容
	*/
	@Excel(name ="题目内容")
	@DataName(name = "题目内容", isRecordHistory = true)
	@ApiModelProperty(value = "题目内容", position = 8)
	@Length(max=500,message="题目内容长度不能大于500")
	private String topicContent;	  
   
	
	/**
	* 选项
	*/
	@Excel(name ="选项")
	@DataName(name = "选项", isRecordHistory = true)
	@ApiModelProperty(value = "选项", position = 9)
	@Length(max=2000,message="选项长度不能大于2000")
	private String options;	  
   
	
	/**
	* 正确答案
	*/
	@Excel(name ="正确答案")
	@DataName(name = "正确答案", isRecordHistory = true)
	@ApiModelProperty(value = "正确答案", position = 10)
	@Length(max=10,message="正确答案长度不能大于10")
	private String rightAnswer;	  
   
	
	/**
	* 正确答案分值
	*/
	@Excel(name ="正确答案分值")
	@DataName(name = "正确答案分值", isRecordHistory = true)
	@ApiModelProperty(value = "正确答案分值", position = 11)
    @DecimalMax(value="9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",message="正确答案分值不能大于9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999")
	@DecimalMin(value="0",message="正确答案分值不能小于0")
	private int rightAnswerScore;	  
   
	
	/**
	* 备选答案
	*/
	@Excel(name ="备选答案")
	@DataName(name = "备选答案", isRecordHistory = true)
	@ApiModelProperty(value = "备选答案", position = 12)
	@Length(max=10,message="备选答案长度不能大于10")
	private String preAnswer;	  
   
	
	/**
	* 备选答案分值
	*/
	@Excel(name ="备选答案分值")
	@DataName(name = "备选答案分值", isRecordHistory = true)
	@ApiModelProperty(value = "备选答案分值", position = 13)
    @DecimalMax(value="9999999999",message="备选答案分值不能大于9999999999")
	@DecimalMin(value="0",message="备选答案分值不能小于0")
	@NotBlank(message = "备选答案分值不能为空")
	private int preAnswerScore;	  
   
	
	/**
	* 用户答案
	*/
	@Excel(name ="用户答案")
	@DataName(name = "用户答案", isRecordHistory = true)
	@ApiModelProperty(value = "用户答案", position = 14)
	@Length(max=10,message="用户答案长度不能大于10")
	@NotBlank(message = "用户答案不能为空")
	private String userAnswer;	  
   
	
	/**
	* 用户答案分值
	*/
	@Excel(name ="用户答案分值")
	@DataName(name = "用户答案分值", isRecordHistory = true)
	@ApiModelProperty(value = "用户答案分值", position = 15)
    @DecimalMax(value="9999999999",message="用户答案分值不能大于9999999999")
	@DecimalMin(value="0",message="用户答案分值不能小于0")
	@NotBlank(message = "用户答案分值不能为空")
	private int userAnswerScore;	  
   
	
	/**
	* 时间
	*/
	@Excel(name ="时间")
	@DataName(name = "时间", isRecordHistory = true)
	@ApiModelProperty(value = "时间", position = 16)
	@Length(max=20,message="时间长度不能大于20")
	@NotBlank(message = "时间不能为空")
	private String dateTime;
 }
