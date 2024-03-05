package com.dooleen.service.system.tenant.info.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-18 09:44:46
 * @Description : 租户管理实体
 * @Author : name
 * @Update : 2020-06-18 09:44:46
 */
@Data
@TableName("sys_tenant_info")
@ApiModel
@ToString(callSuper = true)
public class SysTenantInfoEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@DataName(name = "主键")
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
	 * 租户用户ID
	 */
	@DataName(name = "用户租户ID")
	@ApiModelProperty(value = "用户租户ID", position = 2)
	@NotBlank(message = "用户租户ID不能为空")
	private String userTenantId;

	/**
	 * 租户名称
	 */
	@DataName(name = "租户名称")
	@ApiModelProperty(value = "租户名称", position = 3)
	@NotBlank(message = "租户名称不能为空")
	private String tenantName;

	/**
	 * 联系人名
	 */
	@DataName(name = "联系人名")
	@ApiModelProperty(value = "联系人名", position = 4)
	@NotBlank(message = "联系人名不能为空")
	private String contactName;

	/**
	 * 联系电话号码
	 */
	@DataName(name = "联系电话号码")
	@ApiModelProperty(value = "联系电话号码", position = 5)
	@NotBlank(message = "联系电话号码不能为空")
	private String contactPhoneNo;

	/**
	 * 联系地址
	 */
	@DataName(name = "联系地址")
	@ApiModelProperty(value = "联系地址", position = 6)
	private String contactAddress;

	/**
	 * 绑定域名
	 */
	@DataName(name = "绑定域名")
	@ApiModelProperty(value = "绑定域名", position = 7)
	private String bindDomainName;

	/**
	 * 客户端ID
	 */
	@DataName(name = "客户端ID")
	@ApiModelProperty(value = "客户端ID", position = 8)
	private String clientId;

	/**
	 * 客户秘钥
	 */
	@DataName(name = "客户秘钥")
	@ApiModelProperty(value = "客户秘钥", position = 9)
	private String custSecretKey;

	/**
	 * 系统版本号
	 */
	@DataName(name = "系统版本号")
	@ApiModelProperty(value = "系统版本号", position = 10)
	private String systemVersionNo;

	/**
	 * 菜单版本号
	 */
	@DataName(name = "菜单版本号")
	@ApiModelProperty(value = "菜单版本号", position = 11)
	private String menuVersionNo;

	/**
	 * 顶部菜单版本号
	 */
	@DataName(name = "顶部菜单版本号")
	@ApiModelProperty(value = "顶部菜单版本号", position = 12)
	private String topMenuVersionNo;

	/**
	 * 重置编号
	 */
	@DataName(name = "重置编号")
	@ApiModelProperty(value = "重置编号", position = 13)
	private String resetNo;

	/**
	 * 消息编号
	 */
	@DataName(name = "消息编号")
	@ApiModelProperty(value = "消息编号", position = 14)
	private String msgNo;

	/**
	 * 通知消息
	 */
	@DataName(name = "通知消息")
	@ApiModelProperty(value = "通知消息", position = 15)
	private String noticeMsg;

	/**
	 * 默认系统颜色
	 */
	@DataName(name = "默认系统颜色")
	@ApiModelProperty(value = "默认系统颜色", position = 16)
	private String defaultSystemColor;

	/**
	 * 登录图片地址
	 */
	@DataName(name = "登录图片地址")
	@ApiModelProperty(value = "登录图片地址", position = 16)
	private String bgImgUrl;



	/**
	 * 登录框图片地址
	 */
	@DataName(name = "登录框图片地址")
	@ApiModelProperty(value = "登录框图片地址", position = 16)
	private String loginBgUrl;

	/**
	 * 公司logo
	 */
	@DataName(name = "公司logo")
	@ApiModelProperty(value = "公司logo", position = 16)
	private String logoUrl;

	/**
	 * 公司大logo
	 */
	@DataName(name = "公司大logo")
	@ApiModelProperty(value = "公司大logo", position = 16)
	private String bigLogoUrl;
	/**
	 * 默认密码
	 */
	@DataName(name = "默认密码")
	@ApiModelProperty(value = "默认密码", position = 17)
	private String defaultPassword;

	/**
	 * 购买方式
	 */
	@DataName(name = "购买方式")
	@ApiModelProperty(value = "购买方式", position = 18)
	private String buyWay;

	/**
	 * 账户余额
	 */
	@DataName(name = "账户余额")
	@ApiModelProperty(value = "账户余额", position = 19)
	private BigDecimal acctBal;

	/**
	 * 生效日期
	 */
	@DataName(name = "生效日期")
	@ApiModelProperty(value = "生效日期", position = 20)
	private String effectDate;

	/**
	 * 失效日期
	 */
	@DataName(name = "失效日期")
	@ApiModelProperty(value = "失效日期", position = 21)
	private String invalidDate;


	/**
	 * 许可证内容
	 */
	@DataName(name = "许可证内容")
	@ApiModelProperty(value = "许可证内容", position = 6)
	private String licenseContent;
}
