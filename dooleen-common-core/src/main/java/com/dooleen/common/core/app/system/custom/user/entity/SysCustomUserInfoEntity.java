package com.dooleen.common.core.app.system.custom.user.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2021-02-21 16:40:24
 * @Description : 系统客户用户表实体
 * @Author : name
 * @Update : 2021-02-21 16:40:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_custom_user_info")
@ApiModel
@ToString(callSuper = true)
public class SysCustomUserInfoEntity  extends BaseEntity implements Serializable  {

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
	@DataName(name = "用户名", isRecordHistory = true)
	@ApiModelProperty(value = "用户名", position = 2)
	@Length(max=50,message="用户名长度不能大于50")
	@NotBlank(message = "用户名不能为空")
	private String userName;	  
   
	
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
	* 支付宝openID
	*/
	@DataName(name = "支付宝openID", isRecordHistory = true)
	@ApiModelProperty(value = "支付宝openID", position = 5)
	@Length(max=50,message="支付宝openID长度不能大于50")
	private String alipayOpenId;	  
   
	
	/**
	* APP支付宝openID
	*/
	@DataName(name = "APP支付宝openID", isRecordHistory = true)
	@ApiModelProperty(value = "APP支付宝openID", position = 6)
	@Length(max=50,message="APP支付宝openID长度不能大于50")
	private String appAlipayOpenId;	  
   
	
	/**
	* 其它openID
	*/
	@DataName(name = "其它openID", isRecordHistory = true)
	@ApiModelProperty(value = "其它openID", position = 7)
	@Length(max=50,message="其它openID长度不能大于50")
	private String otherOpenId;	  
   
	
	/**
	* APP其它openID
	*/
	@DataName(name = "APP其它openID", isRecordHistory = true)
	@ApiModelProperty(value = "APP其它openID", position = 8)
	@Length(max=50,message="APP其它openID长度不能大于50")
	private String appOtherOpenId;	  
   
	
	/**
	* 密码
	*/
	@DataName(name = "密码", isRecordHistory = true)
	@ApiModelProperty(value = "密码", position = 9)
	@Length(max=50,message="密码长度不能大于50")
	private String password;	  
   
	
	/**
	* 统一信用代码
	*/
	@DataName(name = "统一信用代码", isRecordHistory = true)
	@ApiModelProperty(value = "统一信用代码", position = 10)
	@Length(max=30,message="统一信用代码长度不能大于30")
	@NotBlank(message = "统一信用代码不能为空")
	private String unifiedCreditDcode;	  
   
	
	/**
	* 机构名称
	*/
	@DataName(name = "机构名称", isRecordHistory = true)
	@ApiModelProperty(value = "机构名称", position = 11)
	@Length(max=50,message="机构名称长度不能大于50")
	@NotBlank(message = "机构名称不能为空")
	private String orgName;

	/**
	 * 昵称
	 */
	@DataName(name = "昵称", isRecordHistory = true)
	@ApiModelProperty(value = "昵称", position = 5)
	@Length(max=50,message="昵称长度不能大于50")
	private String nickName;

	/**
	* 营业执照链接
	*/
	@DataName(name = "营业执照链接", isRecordHistory = true)
	@ApiModelProperty(value = "营业执照链接", position = 12)
	@Length(max=1000,message="营业执照链接长度不能大于1000")
	private String businessLicenseUrl;	  
   
	
	/**
	* 法人姓名
	*/
	@DataName(name = "法人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "法人姓名", position = 13)
	@Length(max=50,message="法人姓名长度不能大于50")
	@NotBlank(message = "法人姓名不能为空")
	private String legalPersonName;	  
   
	
	/**
	* 法人身份证号
	*/
	@DataName(name = "法人身份证号", isRecordHistory = true)
	@ApiModelProperty(value = "法人身份证号", position = 14)
	@Length(max=30,message="法人身份证号长度不能大于30")
	@NotBlank(message = "法人身份证号不能为空")
	private String legalPersonIdCardNo;	  
   
	
	/**
	* 法人身份证链接
	*/
	@DataName(name = "法人身份证链接", isRecordHistory = true)
	@ApiModelProperty(value = "法人身份证链接", position = 15)
	@Length(max=1000,message="法人身份证链接长度不能大于1000")
	private String legalPersonIdCardUrl;	  
   
	
	/**
	* 注册地址
	*/
	@DataName(name = "注册地址", isRecordHistory = true)
	@ApiModelProperty(value = "注册地址", position = 16)
	@Length(max=100,message="注册地址长度不能大于100")
	@NotBlank(message = "注册地址不能为空")
	private String registAddress;	  
   
	
	/**
	* 联系人姓名
	*/
	@DataName(name = "联系人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "联系人姓名", position = 17)
	@Length(max=50,message="联系人姓名长度不能大于50")
	private String contactPersonName;	  
   
	
	/**
	* 联系人身份证号
	*/
	@DataName(name = "联系人身份证号", isRecordHistory = true)
	@ApiModelProperty(value = "联系人身份证号", position = 18)
	@Length(max=30,message="联系人身份证号长度不能大于30")
	private String contactPersonIdCardNo;	  
   
	
	/**
	* 联系人身份证链接
	*/
	@DataName(name = "联系人身份证链接", isRecordHistory = true)
	@ApiModelProperty(value = "联系人身份证链接", position = 19)
	@Length(max=1000,message="联系人身份证链接长度不能大于1000")
	private String contactPersonIdCardUrl;	  
   
	
	/**
	* 所属区域代码
	*/
	@DataName(name = "所属区域代码", isRecordHistory = true)
	@ApiModelProperty(value = "所属区域代码", position = 20)
	@Length(max=30,message="所属区域代码长度不能大于30")
	private String belongAreaDcode;	  
   
	
	/**
	* 所属区域名称
	*/
	@DataName(name = "所属区域名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属区域名称", position = 21)
	@Length(max=50,message="所属区域名称长度不能大于50")
	private String belongAreaName;	  
   
	
	/**
	* 固定电话号码
	*/
	@DataName(name = "固定电话号码", isRecordHistory = true)
	@ApiModelProperty(value = "固定电话号码", position = 22)
	@Length(max=30,message="固定电话号码长度不能大于30")
	private String fixedPhoneNo;	  
   
	
	/**
	* 手机号码
	*/
	@DataName(name = "手机号码", isRecordHistory = true)
	@ApiModelProperty(value = "手机号码", position = 23)
	@Length(max=30,message="手机号码长度不能大于30")
	private String mobileNo;	  
   
	
	/**
	* 邮箱
	*/
	@DataName(name = "邮箱", isRecordHistory = true)
	@ApiModelProperty(value = "邮箱", position = 24)
	@Length(max=50,message="邮箱长度不能大于50")
	private String email;	  
   
	
	/**
	* 传真号码
	*/
	@DataName(name = "传真号码", isRecordHistory = true)
	@ApiModelProperty(value = "传真号码", position = 25)
	@Length(max=30,message="传真号码长度不能大于30")
	private String faxNo;	  
   
	
	/**
	* 激活手机号
	*/
	@DataName(name = "激活手机号", isRecordHistory = true)
	@ApiModelProperty(value = "激活手机号", position = 26)
	@Length(max=30,message="激活手机号长度不能大于30")
	private String activeMobileNo;	  
   
	
	/**
	* 企业有效期
	*/
	@DataName(name = "企业有效期", isRecordHistory = true)
	@ApiModelProperty(value = "企业有效期", position = 27)
	@Length(max=20,message="企业有效期长度不能大于20")
	private String companyValidDate;	  
   
	
	/**
	* 密码错误次数
	*/
	@DataName(name = "密码错误次数", isRecordHistory = true)
	@ApiModelProperty(value = "密码错误次数", position = 28)
    @DecimalMax(value="9999999999",message="密码错误次数不能大于9999999999")
	@DecimalMin(value="0",message="密码错误次数不能小于0")
	private int passwordErrorTimes;	  
   
	
	/**
	* 注册渠道
	*/
	@DataName(name = "注册渠道", isRecordHistory = true)
	@ApiModelProperty(value = "注册渠道", position = 29)
	@Length(max=50,message="注册渠道长度不能大于50")
	private String registChannel;	  
   
	
	/**
	* 头像链接
	*/
	@DataName(name = "头像链接", isRecordHistory = true)
	@ApiModelProperty(value = "头像链接", position = 30)
	@Length(max=100,message="头像链接长度不能大于100")
	private String headImgUrl;
	/**
	 * 状态
	 */
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 6)
	@Length(max=30,message="状态长度不能大于30")
	private String status;
 }
