package com.dooleen.service.system.user.setting.entity;

import java.io.Serializable;

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
 * @CreateDate : 2020-06-17 15:20:53
 * @Description : 系统用户设置管理实体
 * @Author : name
 * @Update : 2020-06-17 15:20:53
 */
@Data
@TableName("sys_user_setting")
@ApiModel
@ToString(callSuper = true)
public class SysUserSettingEntity extends BaseEntity implements Serializable  {

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
	* 用户名
	*/
	@DataName(name = "用户名")
	@ApiModelProperty(value = "用户名", position = 2)
	@NotBlank(message = "用户名不能为空")
	private String userName;	  
   
	/**
	* 真实姓名
	*/
	@DataName(name = "真实姓名")
	@ApiModelProperty(value = "真实姓名", position = 3)
	private String realName;	  
   
	/**
	* 系统版本号
	*/
	@DataName(name = "系统版本号")
	@ApiModelProperty(value = "系统版本号", position = 4)
	private String systemVersionNo;	  
   
	/**
	* 顶部菜单版本号
	*/
	@DataName(name = "顶部菜单版本号")
	@ApiModelProperty(value = "顶部菜单版本号", position = 5)
	private String topMenuVersionNo;	  
   
	/**
	* 菜单版本号
	*/
	@DataName(name = "菜单版本号")
	@ApiModelProperty(value = "菜单版本号", position = 6)
	private String menuVersionNo;	  
   
	/**
	* 重置编号
	*/
	@DataName(name = "重置编号")
	@ApiModelProperty(value = "重置编号", position = 7)
	private String resetNo;	  
   
	/**
	* 通知消息
	*/
	@DataName(name = "通知消息")
	@ApiModelProperty(value = "通知消息", position = 8)
	private String noticeMsg;	  
	
	/**
	* 消息编号
	*/
	@DataName(name = "消息编号")
	@ApiModelProperty(value = "消息编号", position = 8)
	private String msgNo;	  
	/**
	* 阅读标志
	*/
	@DataName(name = "阅读标志")
	@ApiModelProperty(value = "阅读标志", position = 8)
	private String readFlag;	
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
	@ApiModelProperty(value = "客户秘钥", position = 8)
	private String custSecretKey;	
	


	/**
	* 自定义菜单内容
	*/
	@DataName(name = "自定义菜单内容")
	@ApiModelProperty(value = "自定义菜单内容", position = 6)
	private String selfDefMenuContent;

	/**
	* 自定义导航内容
	*/
	@DataName(name = "自定义导航内容")
	@ApiModelProperty(value = "自定义导航内容", position = 6)
	private String selfDefNaviContent;



	/**
	 * 自定义布局内容
	 */
	@DataName(name = "自定义布局内容", isRecordHistory = true)
	@ApiModelProperty(value = "自定义布局内容", position = 6)
	private String selfDefLayoutContent;

	/**
	* 个人设置内容
	*/
	@DataName(name = "个人设置内容")
	@ApiModelProperty(value = "个人设置内容", position = 9)
	private String personalSettingContent;	  
   
	/**
	* 个人设置内容1
	*/
	@DataName(name = "个人设置内容1")
	@ApiModelProperty(value = "个人设置内容1", position = 10)
	private String personalSettingOneContent;	  
   
	/**
	* 个人设置内容2
	*/
	@DataName(name = "个人设置内容2")
	@ApiModelProperty(value = "个人设置内容2", position = 11)
	private String personalSettingTwoContent;	  
 }
