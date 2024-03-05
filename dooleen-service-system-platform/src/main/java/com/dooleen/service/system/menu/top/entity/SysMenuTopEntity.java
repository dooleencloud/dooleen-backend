package com.dooleen.service.system.menu.top.entity;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2020-06-12 19:35:33
 * @Description : 系统顶部菜单管理实体
 * @Author : name
 * @Update : 2020-06-12 19:35:33
 */
@Data
@TableName("sys_menu_top")
@ApiModel
@ToString(callSuper = true)
public class SysMenuTopEntity extends BaseEntity implements Serializable  {

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
	* 菜单名称
	*/
	@DataName(name = "菜单名称")
	@ApiModelProperty(value = "菜单名称", position = 2)
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;	  
   
	/**
	* 菜单等级
	*/
	@DataName(name = "菜单等级")
	@ApiModelProperty(value = "菜单等级", position = 3)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int menuLevel;	  
   
	/**
	* 路由地址
	*/
	@DataName(name = "路由地址")
	@ApiModelProperty(value = "路由地址", position = 4)
	private String routeAddress;	  
   
	/**
	* 菜单图标
	*/
	@DataName(name = "菜单图标")
	@ApiModelProperty(value = "菜单图标", position = 5)
	@NotBlank(message = "菜单图标不能为空")
	private String menuIcon;	  
   
	/**
	* 保持活动标志
	*/
	@DataName(name = "保持活动标志")
	@ApiModelProperty(value = "保持活动标志", position = 6)
	@NotBlank(message = "保持活动标志不能为空")
	private String keepAliveFlag;	  
 }
