package com.dooleen.service.system.user.account.entity;

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
 * @CreateDate : 2021-08-05 21:54:04
 * @Description : 用户账户管理实体
 * @Author : name
 * @Update : 2021-08-05 21:54:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_account")
@ApiModel
@ToString(callSuper = true)
public class SysUserAccountEntity  extends BaseEntity implements Serializable  {
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
	* 账户类型
	*/
	@Excel(name ="账户类型")
	@DataName(name = "账户类型", isRecordHistory = true)
	@ApiModelProperty(value = "账户类型", position = 3)
	@Length(max=30,message="账户类型长度不能大于30")
	@NotBlank(message = "账户类型不能为空")
	private String acctType;	  
   
	
	/**
	* 账户余额
	*/
	@Excel(name ="账户余额")
	@DataName(name = "账户余额", isRecordHistory = true)
	@ApiModelProperty(value = "账户余额", position = 4)
	@Digits(integer=10,fraction=2, message = "账户余额请填9999999999以内数字")
	@NotBlank(message = "账户余额不能为空")
	private BigDecimal acctBal;	  
   
	
	/**
	* 冻结金额
	*/
	@Excel(name ="冻结金额")
	@DataName(name = "冻结金额", isRecordHistory = true)
	@ApiModelProperty(value = "冻结金额", position = 5)
	@Digits(integer=13,fraction=2, message = "冻结金额请填9999999999999以内数字")
	private BigDecimal freezeAmt;	  
   
	
	/**
	* 提现申请账户类型
	*/
	@Excel(name ="提现申请账户类型")
	@DataName(name = "提现申请账户类型", isRecordHistory = true)
	@ApiModelProperty(value = "提现申请账户类型", position = 6)
	@Length(max=30,message="提现申请账户类型长度不能大于30")
	private String cashApplyAcctType;	  
   
	
	/**
	* 提现申请人名
	*/
	@Excel(name ="提现申请人名")
	@DataName(name = "提现申请人名", isRecordHistory = true)
	@ApiModelProperty(value = "提现申请人名", position = 7)
	@Length(max=50,message="提现申请人名长度不能大于50")
	private String cashApplyRealName;	  
   
	
	/**
	* 提现申请账户号
	*/
	@Excel(name ="提现申请账户号")
	@DataName(name = "提现申请账户号", isRecordHistory = true)
	@ApiModelProperty(value = "提现申请账户号", position = 8)
	@Length(max=30,message="提现申请账户号长度不能大于30")
	private String cashApplyAcctNo;	  
   
	
	/**
	* 奖励积分数量
	*/
	@Excel(name ="奖励积分数量")
	@DataName(name = "奖励积分数量", isRecordHistory = true)
	@ApiModelProperty(value = "奖励积分数量", position = 9)
    @DecimalMax(value="9999999999",message="奖励积分数量不能大于9999999999")
	@DecimalMin(value="0",message="奖励积分数量不能小于0")
	private int rewardPointCount;	  
   
	
	/**
	* 邀请数量
	*/
	@Excel(name ="邀请数量")
	@DataName(name = "邀请数量", isRecordHistory = true)
	@ApiModelProperty(value = "邀请数量", position = 10)
    @DecimalMax(value="9999999999",message="邀请数量不能大于9999999999")
	@DecimalMin(value="0",message="邀请数量不能小于0")
	private int inviteCount;	  
   
	
	/**
	* 剩余邀请数量
	*/
	@Excel(name ="剩余邀请数量")
	@DataName(name = "剩余邀请数量", isRecordHistory = true)
	@ApiModelProperty(value = "剩余邀请数量", position = 11)
    @DecimalMax(value="9999999999",message="剩余邀请数量不能大于9999999999")
	@DecimalMin(value="0",message="剩余邀请数量不能小于0")
	private int leftInviteCount;	  
   
	
	/**
	* 状态
	*/
	@Excel(name ="状态")
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 12)
	@Length(max=30,message="状态长度不能大于30")
	private String status;	  
 }
