package com.dooleen.service.system.custom.org.info.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
 * @CreateDate : 2021-02-27 17:35:07
 * @Description : 客户机构登记管理实体
 * @Author : name
 * @Update : 2021-02-27 17:35:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_custom_org_info")
@ApiModel
@ToString(callSuper = true)
public class SysCustomOrgInfoEntity  extends BaseEntity implements Serializable  {

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
	* 统一信用代码
	*/
	@DataName(name = "统一信用代码", isRecordHistory = true)
	@ApiModelProperty(value = "统一信用代码", position = 2)
	@Length(max=30,message="统一信用代码长度不能大于30")
	@NotBlank(message = "统一信用代码不能为空")
	private String unifiedCreditDcode;	  
   
	
	/**
	* 机构名称
	*/
	@DataName(name = "机构名称", isRecordHistory = true)
	@ApiModelProperty(value = "机构名称", position = 3)
	@Length(max=50,message="机构名称长度不能大于50")
	@NotBlank(message = "机构名称不能为空")
	private String orgName;	  
   
	
	/**
	* 营业执照链接
	*/
	@DataName(name = "营业执照链接", isRecordHistory = true)
	@ApiModelProperty(value = "营业执照链接", position = 4)
	@Length(max=1000,message="营业执照链接长度不能大于1000")
	@NotBlank(message = "营业执照链接不能为空")
	private String businessLicenseUrl;	  
   
	
	/**
	* 法人姓名
	*/
	@DataName(name = "法人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "法人姓名", position = 5)
	@Length(max=50,message="法人姓名长度不能大于50")
	@NotBlank(message = "法人姓名不能为空")
	private String legalPersonName;	  
   
	
	/**
	* 法人身份证号
	*/
	@DataName(name = "法人身份证号", isRecordHistory = true)
	@ApiModelProperty(value = "法人身份证号", position = 6)
	@Length(max=30,message="法人身份证号长度不能大于30")
	@NotBlank(message = "法人身份证号不能为空")
	private String legalPersonIdCardNo;	  
   
	
	/**
	* 法人身份证链接
	*/
	@DataName(name = "法人身份证链接", isRecordHistory = true)
	@ApiModelProperty(value = "法人身份证链接", position = 7)
	@Length(max=1000,message="法人身份证链接长度不能大于1000")
	@NotBlank(message = "法人身份证链接不能为空")
	private String legalPersonIdCardUrl;	  
   
	
	/**
	* 注册地址
	*/
	@DataName(name = "注册地址", isRecordHistory = true)
	@ApiModelProperty(value = "注册地址", position = 8)
	@Length(max=100,message="注册地址长度不能大于100")
	@NotBlank(message = "注册地址不能为空")
	private String registAddress;	  
   
	
	/**
	* 联系人姓名
	*/
	@DataName(name = "联系人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "联系人姓名", position = 9)
	@Length(max=50,message="联系人姓名长度不能大于50")
	@NotBlank(message = "联系人姓名不能为空")
	private String contactPersonName;	  
   
	
	/**
	* 联系人身份证号
	*/
	@DataName(name = "联系人身份证号", isRecordHistory = true)
	@ApiModelProperty(value = "联系人身份证号", position = 10)
	@Length(max=30,message="联系人身份证号长度不能大于30")
	@NotBlank(message = "联系人身份证号不能为空")
	private String contactPersonIdCardNo;	  
   
	
	/**
	* 联系人身份证链接
	*/
	@DataName(name = "联系人身份证链接", isRecordHistory = true)
	@ApiModelProperty(value = "联系人身份证链接", position = 11)
	@Length(max=1000,message="联系人身份证链接长度不能大于1000")
	@NotBlank(message = "联系人身份证链接不能为空")
	private String contactPersonIdCardUrl;	  
   
	
	/**
	* 所属区域代码
	*/
	@DataName(name = "所属区域代码", isRecordHistory = true)
	@ApiModelProperty(value = "所属区域代码", position = 12)
	@Length(max=30,message="所属区域代码长度不能大于30")
	@NotBlank(message = "所属区域代码不能为空")
	private String belongAreaDcode;	  
   
	
	/**
	* 所属区域名称
	*/
	@DataName(name = "所属区域名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属区域名称", position = 13)
	@Length(max=50,message="所属区域名称长度不能大于50")
	@NotBlank(message = "所属区域名称不能为空")
	private String belongAreaName;	  
   
	
	/**
	* 联系电话
	*/
	@DataName(name = "联系电话", isRecordHistory = true)
	@ApiModelProperty(value = "联系电话", position = 14)
	@Length(max=30,message="联系电话长度不能大于30")
	private String contactPhoneNo;	  
   
	
	/**
	* 手机号码
	*/
	@DataName(name = "手机号码", isRecordHistory = true)
	@ApiModelProperty(value = "手机号码", position = 15)
	@Length(max=30,message="手机号码长度不能大于30")
	private String mobileNo;	  
   
	
	/**
	* 邮箱
	*/
	@DataName(name = "邮箱", isRecordHistory = true)
	@ApiModelProperty(value = "邮箱", position = 16)
	@Length(max=50,message="邮箱长度不能大于50")
	private String email;	  
   
	
	/**
	* 传真号码
	*/
	@DataName(name = "传真号码", isRecordHistory = true)
	@ApiModelProperty(value = "传真号码", position = 17)
	@Length(max=30,message="传真号码长度不能大于30")
	private String faxNo;	  
   
	
	/**
	* 激活手机号
	*/
	@DataName(name = "激活手机号", isRecordHistory = true)
	@ApiModelProperty(value = "激活手机号", position = 18)
	@Length(max=30,message="激活手机号长度不能大于30")
	private String activeMobileNo;	  
   
	
	/**
	* 企业有效期
	*/
	@DataName(name = "企业有效期", isRecordHistory = true)
	@ApiModelProperty(value = "企业有效期", position = 19)
	@Length(max=20,message="企业有效期长度不能大于20")
	private String companyValidDate;



	/**
	 * 状态
	 */
	@DataName(name = "状态")
	@ApiModelProperty(value = "状态", position = 6)
	@Length(max=30,message="状态长度不能大于30")
	private String status;

	/**
	 * 复核人
	 */
	@DataName(name = "复核人")
	@ApiModelProperty(value = "复核人", position = 6)
	@Length(max=50,message="复核人长度不能大于50")
	private String checkUserName;

	/**
	 * 驳回原因
	 */
	@DataName(name = "驳回原因")
	@ApiModelProperty(value = "驳回原因", position = 6)
	@Length(max=100,message="驳回原因长度不能大于100")
	private String rejectReason;
 }
