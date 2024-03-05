package com.dooleen.service.app.oauth.entity;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 系统用户管理实体
 * @Author : name
 * @Update : 2020-06-06 19:37:42
 */
@Data
@ApiModel
public class SysUserInfoEntity extends BaseEntity {
	/**
	 * 
	 */
	public SysUserInfoEntity() {
	}

	/**
	 * @param user
	 */
	public SysUserInfoEntity(SysUserInfoEntity user) {
		this.id = user.getId();
		this.password = user.getPassword();
		this.tenantId = user.getTenantId();
		this.userName = user.getUserName();
		this.realName = user.getRealName();
		this.nickName = user.getNickName();
		this.wxOpenId = user.getWxOpenId();
		this.appWxOpenId = user.getAppWxOpenId();
		this.companyName = user.getCompanyName();
		this.headImgUrl = user.getHeadImgUrl();
		this.sex = user.getSex();
		this.passwordStatus = user.getPasswordStatus();
		this.status = user.getStatus();
		this.email = user.getEmail();
		this.belongOrgNo = user.getBelongOrgNo();
		this.belongOrgName = user.getBelongOrgName();
		this.loginName = user.getLoginName();
		this.effectDate =  user.getEffectDate();
		this.invalidDate = user.getInvalidDate();
	}

	/**
	 * id
	 */
	@DataName(name = "主键")
	@ApiModelProperty(value = "id", position = 0)
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
	@DataName(name = "用户名")
	@ApiModelProperty(value = "用户名", position = 2)
	@NotBlank(message = "用户名不能为空")
	private String userName;


	/**
	 * 微信openID
	 */
	@DataName(name = "微信openID", isRecordHistory = true)
	@ApiModelProperty(value = "微信openID", position = 6)
	@Length(max=50,message="微信openID长度不能大于50")
	private String wxOpenId;

	/**
	 * 小程序openID
	 */
	@DataName(name = "小程序openID", isRecordHistory = true)
	@ApiModelProperty(value = "小程序openID", position = 6)
	@Length(max=50,message="小程序openID长度不能大于50")
	private String miniProgramOpenId;

	/**
	 * APP微信openID
	 */
	@DataName(name = "APP微信openID", isRecordHistory = true)
	@ApiModelProperty(value = "APP微信openID", position = 6)
	@Length(max=50,message="APP微信openID长度不能大于50")
	private String appWxOpenId;
	/**
	 * 钉钉APPID
	 */
	@DataName(name = "钉钉APPID", isRecordHistory = true)
	@ApiModelProperty(value = "钉钉APPID", position = 6)
	@Length(max=20,message="钉钉APPID长度不能大于20")
	private String dingtalkAppId;

	/**
	 * 钉钉用户ID
	 */
	@DataName(name = "钉钉用户ID", isRecordHistory = true)
	@ApiModelProperty(value = "钉钉用户ID", position = 6)
	@Length(max=20,message="钉钉用户ID长度不能大于20")
	private String dingtalkUserId;

	/**
	 * 支付宝openID
	 */
	@DataName(name = "支付宝openID", isRecordHistory = true)
	@ApiModelProperty(value = "支付宝openID", position = 6)
	@Length(max=50,message="支付宝openID长度不能大于50")
	private String alipayOpenId;

	/**
	 * APP支付宝openID
	 */
	@Excel(name ="APP支付宝openID")
	@DataName(name = "APP支付宝openID", isRecordHistory = true)
	@ApiModelProperty(value = "APP支付宝openID", position = 6)
	@Length(max=50,message="APP支付宝openID长度不能大于50")
	private String appAlipayOpenId;

	/**
	 * 客户端ID
	 */
	@DataName(name = "客户端ID", isRecordHistory = true)
	@ApiModelProperty(value = "客户端ID", position = 6)
	@Length(max=20,message="客户端ID长度不能大于20")
	private String clientId;

	/**
	* 登录用户名
	*/
	@DataName(name = "登录用户名")
	@ApiModelProperty(value = "登录用户名", position = 2)
	@TableField(exist = false)
	private String loginName;	
	
	/**
	 * 密码
	 */
	@DataName(name = "密码")
	@ApiModelProperty(value = "密码", position = 3)
	private String password;

	/**
	 * 真实姓名
	 */
	@DataName(name = "真实姓名")
	@ApiModelProperty(value = "真实姓名", position = 4)
	@NotBlank(message = "真实姓名不能为空")
	private String realName;

	/**
	 * 昵称
	 */
	@DataName(name = "昵称")
	@ApiModelProperty(value = "昵称", position = 5)
	private String nickName;

	/**
	 * 所属机构号
	 */
	@DataName(name = "所属机构号")
	@ApiModelProperty(value = "所属机构号", position = 6)
	@NotBlank(message = "所属机构号不能为空")
	private String belongOrgNo;

	/**
	 * 所属机构名称
	 */
	@DataName(name = "所属机构名称")
	@ApiModelProperty(value = "所属机构名称", position = 7)
	@NotBlank(message = "所属机构名称不能为空")
	private String belongOrgName;

	/**
	 * 所属机构组
	 */
	@DataName(name = "所属机构组")
	@ApiModelProperty(value = "所属机构组", position = 8)
	private String belongOrgGroup;

	/**
	 * 所属部门ID
	 */
	@DataName(name = "所属部门ID")
	@ApiModelProperty(value = "所属部门ID", position = 9)
	private String belongDeptId;

	/**
	 * 所属部门名称
	 */
	@DataName(name = "所属部门名称")
	@ApiModelProperty(value = "所属部门名称", position = 10)
	private String belongDeptName;

	/**
	 * 公司名称
	 */
	@DataName(name = "公司名称")
	@ApiModelProperty(value = "公司名称", position = 11)
	private String companyName;

	/**
	 * 职位名称
	 */
	@DataName(name = "职位名称")
	@ApiModelProperty(value = "职位名称", position = 12)
	private String postName;

	/**
	 * 拥有角色组
	 */
	@DataName(name = "拥有角色组")
	@ApiModelProperty(value = "拥有角色组", position = 13)
	private String ownRoleGroup;

	/**
	 * 性别
	 */
	@DataName(name = "性别")
	@ApiModelProperty(value = "性别", position = 14)
	private String sex;

	/**
	 * 年龄
	 */
	@DataName(name = "年龄")
	@ApiModelProperty(value = "年龄", position = 15)
	@DecimalMax(value = "9999999999", message = "数字不能大于10")
	@DecimalMin(value = "0", message = "数字不能小于0")
	private int age;

	/**
	 * 电话号码
	 */
	@DataName(name = "电话号码")
	@ApiModelProperty(value = "电话号码", position = 16)
	private String phoneNo;

	/**
	 * 手机号码
	 */
	@DataName(name = "手机号码")
	@ApiModelProperty(value = "手机号码", position = 17)
	@NotBlank(message = "手机号码不能为空")
	private String mobileNo;

	/**
	 * 邮箱
	 */
	@DataName(name = "邮箱")
	@ApiModelProperty(value = "邮箱", position = 18)
	private String email;

	/**
	 * 头像链接
	 */
	@DataName(name = "头像链接")
	@ApiModelProperty(value = "头像链接", position = 19)
	private String headImgUrl;
	/**
	 * 用户类型
	 */
	@DataName(name = "用户类型")
	@ApiModelProperty(value = "用户类型", position = 21)
	private String userType;
	
	/**
	* 密码状态
	*/
	@DataName(name = "密码状态")
	@ApiModelProperty(value = "密码状态", position = 6)
	private String passwordStatus;
	
	/**
	 * 状态
	 */
	@DataName(name = "状态")
	@ApiModelProperty(value = "状态", position = 20)
	private String status;

	/**
	 * 技能名称
	 */
	@DataName(name = "技能名称", isRecordHistory = true)
	@ApiModelProperty(value = "技能名称", position = 6)
	@Length(max=50,message="技能名称长度不能大于50")
	private String skillName;

	/**
	 * 级别
	 */
	@DataName(name = "级别", isRecordHistory = true)
	@ApiModelProperty(value = "级别", position = 6)
	@Length(max=30,message="级别长度不能大于30")
	private String grade;

	/**
	 * 个人签名
	 */
	@DataName(name = "个人签名", isRecordHistory = true)
	@ApiModelProperty(value = "个人签名", position = 6)
	@Length(max=128,message="个人签名长度不能大于128")
	private String personalSign;

	@DataName(name = "生效日期")
	@ApiModelProperty(value = "生效日期", position = 2)
	@TableField(exist = false)
	private String effectDate;

	/**
	 * 失效日期
	 */
	@DataName(name = "失效日期")
	@ApiModelProperty(value = "失效日期", position = 2)
	@TableField(exist = false)
	private String invalidDate;
}
