package com.dooleen.service.system.user.trans.detail.entity;

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
 * @CreateDate : 2021-08-05 22:19:54
 * @Description : 账户交易明细管理实体
 * @Author : name
 * @Update : 2021-08-05 22:19:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_trans_detail")
@ApiModel
@ToString(callSuper = true)
public class SysUserTransDetailEntity  extends BaseEntity implements Serializable  {
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
	* 账户ID
	*/
	@Excel(name ="账户ID")
	@DataName(name = "账户ID", isRecordHistory = true)
	@ApiModelProperty(value = "账户ID", position = 3)
	@Length(max=20,message="账户ID长度不能大于20")
	@NotBlank(message = "账户ID不能为空")
	private String acctId;	  
   
	
	/**
	* 交易流水号
	*/
	@Excel(name ="交易流水号")
	@DataName(name = "交易流水号", isRecordHistory = true)
	@ApiModelProperty(value = "交易流水号", position = 4)
	@Length(max=30,message="交易流水号长度不能大于30")
	@NotBlank(message = "交易流水号不能为空")
	private String transSerialNo;	  
   
	
	/**
	* 交易方向标识
	*/
	@Excel(name ="交易方向标识")
	@DataName(name = "交易方向标识", isRecordHistory = true)
	@ApiModelProperty(value = "交易方向标识", position = 5)
	@Length(max=1,message="交易方向标识长度不能大于1")
	@NotBlank(message = "交易方向标识不能为空")
	private String transDirectionFlag;	  
   
	
	/**
	* 交易金额
	*/
	@Excel(name ="交易金额")
	@DataName(name = "交易金额", isRecordHistory = true)
	@ApiModelProperty(value = "交易金额", position = 6)
	@Digits(integer=13,fraction=2, message = "交易金额请填9999999999999以内数字")
	@NotBlank(message = "交易金额不能为空")
	private BigDecimal transAmt;	  
   
	
	/**
	* 余额
	*/
	@Excel(name ="余额")
	@DataName(name = "余额", isRecordHistory = true)
	@ApiModelProperty(value = "余额", position = 7)
	@Digits(integer=10,fraction=2, message = "余额请填9999999999以内数字")
	@NotBlank(message = "余额不能为空")
	private BigDecimal bal;	  
   
	
	/**
	* 交易原因
	*/
	@Excel(name ="交易原因")
	@DataName(name = "交易原因", isRecordHistory = true)
	@ApiModelProperty(value = "交易原因", position = 8)
	@Length(max=100,message="交易原因长度不能大于100")
	private String transReason;	  
   
	
	/**
	* 资源ID
	*/
	@Excel(name ="资源ID")
	@DataName(name = "资源ID", isRecordHistory = true)
	@ApiModelProperty(value = "资源ID", position = 9)
	@Length(max=20,message="资源ID长度不能大于20")
	private String resourceId;	  
   
	
	/**
	* 资源标题
	*/
	@Excel(name ="资源标题")
	@DataName(name = "资源标题", isRecordHistory = true)
	@ApiModelProperty(value = "资源标题", position = 10)
	@Length(max=200,message="资源标题长度不能大于200")
	private String resourceTitle;	  
   
	
	/**
	* 对方账户ID
	*/
	@Excel(name ="对方账户ID")
	@DataName(name = "对方账户ID", isRecordHistory = true)
	@ApiModelProperty(value = "对方账户ID", position = 11)
	@Length(max=20,message="对方账户ID长度不能大于20")
	private String peerAcctId;	  
   
	
	/**
	* 对方账户用户名
	*/
	@Excel(name ="对方账户用户名")
	@DataName(name = "对方账户用户名", isRecordHistory = true)
	@ApiModelProperty(value = "对方账户用户名", position = 12)
	@Length(max=50,message="对方账户用户名长度不能大于50")
	private String peerAcctUserName;	  
   
	
	/**
	* 对方交易流水号
	*/
	@Excel(name ="对方交易流水号")
	@DataName(name = "对方交易流水号", isRecordHistory = true)
	@ApiModelProperty(value = "对方交易流水号", position = 13)
	@Length(max=30,message="对方交易流水号长度不能大于30")
	private String peerTransSerialNo;	  
   
	
	/**
	* 交易比例
	*/
	@Excel(name ="交易比例")
	@DataName(name = "交易比例", isRecordHistory = true)
	@ApiModelProperty(value = "交易比例", position = 14)
	@Length(max=5,message="交易比例长度不能大于5")
	private String transRate;	  
   
	
	/**
	* 交易状态
	*/
	@Excel(name ="交易状态")
	@DataName(name = "交易状态", isRecordHistory = true)
	@ApiModelProperty(value = "交易状态", position = 15)
	@Length(max=30,message="交易状态长度不能大于30")
	private String transStatus;	  
   
	
	/**
	* 交易方式
	*/
	@Excel(name ="交易方式")
	@DataName(name = "交易方式", isRecordHistory = true)
	@ApiModelProperty(value = "交易方式", position = 16)
	@Length(max=30,message="交易方式长度不能大于30")
	private String transWay;	  
   
	
	/**
	* 充值卡号
	*/
	@Excel(name ="充值卡号")
	@DataName(name = "充值卡号", isRecordHistory = true)
	@ApiModelProperty(value = "充值卡号", position = 17)
	@Length(max=30,message="充值卡号长度不能大于30")
	private String rechargeCardNo;	  
   
	
	/**
	* 充值密码
	*/
	@Excel(name ="充值密码")
	@DataName(name = "充值密码", isRecordHistory = true)
	@ApiModelProperty(value = "充值密码", position = 18)
	@Length(max=50,message="充值密码长度不能大于50")
	private String rechargePassword;	  
 }
