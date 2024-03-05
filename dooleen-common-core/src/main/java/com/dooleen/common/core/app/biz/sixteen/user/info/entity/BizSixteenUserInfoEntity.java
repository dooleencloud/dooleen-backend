package com.dooleen.common.core.app.biz.sixteen.user.info.entity;;

import cn.afterturn.easypoi.excel.annotation.Excel;
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

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-08 22:00:24
 * @Description : 用户信息管理实体
 * @Author : name
 * @Update : 2021-05-08 22:00:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_sixteen_user_info")
@ApiModel
@ToString(callSuper = true)
public class BizSixteenUserInfoEntity extends BaseEntity implements Serializable  {
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
	* 用户名
	*/
	@Excel(name ="用户名")
	@DataName(name = "用户名", isRecordHistory = true)
	@ApiModelProperty(value = "用户名", position = 2)
	@Length(max=50,message="用户名长度不能大于50")
	@NotBlank(message = "用户名不能为空")
	private String userName;


	/**
	* 用户姓名
	*/
	@Excel(name ="用户姓名")
	@DataName(name = "用户姓名", isRecordHistory = true)
	@ApiModelProperty(value = "用户姓名", position = 3)
	@Length(max=50,message="用户姓名长度不能大于50")
	@NotBlank(message = "用户姓名不能为空")
	private String realName;

	/**
	 * 微信openID
	 */
	@DataName(name = "微信openID", isRecordHistory = true)
	@ApiModelProperty(value = "微信openID", position = 3)
	@Length(max=50,message="微信openID长度不能大于50")
	private String wxOpenId;


	/**
	 * APP微信openID
	 */
	@DataName(name = "APP微信openID", isRecordHistory = true)
	@ApiModelProperty(value = "APP微信openID", position = 4)
	@Length(max=50,message="APP微信openID长度不能大于50")
	private String appWxOpenId;
	/**
	 * 组织名称
	 */
	@Excel(name ="组织名称")
	@DataName(name = "组织名称", isRecordHistory = true)
	@ApiModelProperty(value = "组织名称", position = 2)
	@Length(max=50,message="用户名长度不能大于50")
	@NotBlank(message = "组织名称不能为空")
	private String orgName;

	/**
	* 邮箱
	*/
	@Excel(name ="邮箱")
	@DataName(name = "邮箱", isRecordHistory = true)
	@ApiModelProperty(value = "邮箱", position = 4)
	@Length(max=100,message="邮箱长度不能大于100")
	private String email;	  
   
	
	/**
	* 电话
	*/
	@Excel(name ="电话")
	@DataName(name = "电话", isRecordHistory = true)
	@ApiModelProperty(value = "电话", position = 5)
	@Length(max=20,message="电话长度不能大于20")
	private String phone;	  
   
	
	/**
	* 密码
	*/
	@Excel(name ="密码")
	@DataName(name = "密码", isRecordHistory = true)
	@ApiModelProperty(value = "密码", position = 6)
	@Length(max=100,message="密码长度不能大于100")
	private String password;	  
   
	
	/**
	* 性别
	*/
	@Excel(name ="性别")
	@DataName(name = "性别", isRecordHistory = true)
	@ApiModelProperty(value = "性别", position = 7)
	@Length(max=10,message="性别长度不能大于10")
	private String sex;	  
   
	
	/**
	* 年龄
	*/
	@Excel(name ="年龄")
	@DataName(name = "年龄", isRecordHistory = true)
	@ApiModelProperty(value = "年龄", position = 8)
    @DecimalMax(value="9999999999",message="年龄不能大于9999999999")
	@DecimalMin(value="0",message="年龄不能小于0")
	private int age;	  
   
	
	/**
	* 答题开始时间
	*/
	@Excel(name ="答题开始时间")
	@DataName(name = "答题开始时间", isRecordHistory = true)
	@ApiModelProperty(value = "答题开始时间", position = 9)
	@Length(max=20,message="答题开始时间长度不能大于20")
	private String answerBeginTime;	  
   
	
	/**
	* 答题结束时间
	*/
	@Excel(name ="答题结束时间")
	@DataName(name = "答题结束时间", isRecordHistory = true)
	@ApiModelProperty(value = "答题结束时间", position = 10)
	@Length(max=20,message="答题结束时间长度不能大于20")
	private String answerEndTime;



	/**
	 * 消耗时间
	 */
	@Excel(name ="消耗时间")
	@DataName(name = "消耗时间", isRecordHistory = true)
	@ApiModelProperty(value = "消耗时间", position = 6)
	@Length(max=10,message="消耗时间长度不能大于10")
	private String takeTime;

	/**
	 * 进度比例
	 */
	@Excel(name ="进度比例")
	@DataName(name = "进度比例", isRecordHistory = true)
	@ApiModelProperty(value = "进度比例", position = 6)
	@Length(max=10,message="进度比例长度不能大于10")
	private String progressRate;

	/**
	* 结束标志
	*/
	@Excel(name ="结束标志")
	@DataName(name = "结束标志", isRecordHistory = true)
	@ApiModelProperty(value = "结束标志", position = 11)
	@Length(max=10,message="结束标志长度不能大于10")
	private String endFlag;	  
   
	
	/**
	* 注册时间
	*/
	@Excel(name ="注册时间")
	@DataName(name = "注册时间", isRecordHistory = true)
	@ApiModelProperty(value = "注册时间", position = 12)
	@Length(max=20,message="注册时间长度不能大于20")
	private String regTime;	  
   
	
	/**
	* 分析状态
	*/
	@Excel(name ="分析状态")
	@DataName(name = "分析状态", isRecordHistory = true)
	@ApiModelProperty(value = "分析状态", position = 13)
	@Length(max=10,message="分析状态长度不能大于10")
	private String analysisStatus;	  
   
	
	/**
	* 分析描述
	*/
	@Excel(name ="分析描述")
	@DataName(name = "分析描述", isRecordHistory = true)
	@ApiModelProperty(value = "分析描述", position = 14)
	@Length(max=1000,message="分析描述长度不能大于1000")
	private String analysisDesc;	  
 }
