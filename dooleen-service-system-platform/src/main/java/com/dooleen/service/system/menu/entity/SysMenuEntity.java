package com.dooleen.service.system.menu.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2020-06-08 16:40:01
 * @Description : 系统菜单管理实体
 * @Author : name
 * @Update : 2020-06-08 16:40:01
 */
@Data
@TableName("sys_menu")
@ApiModel
@ToString(callSuper = true)
public class SysMenuEntity extends BaseEntity implements Serializable  {

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
	* 项目ID
	*/
	@DataName(name = "项目ID")
	@ApiModelProperty(value = "项目ID", position = 1)
	@TableField(exist = false)
	public String projectId;
	/**
	* 上级菜单ID
	*/
	@DataName(name = "上级菜单ID")
	@ApiModelProperty(value = "上级菜单ID", position = 2)
	@NotBlank(message = "上级菜单ID不能为空")
	private String parentMenuId;	  
   
	/**
	* 菜单分类
	*/
	@DataName(name = "菜单分类")
	@ApiModelProperty(value = "菜单分类", position = 3)
	private String menuCategory;	  
   
	/**
	* 菜单名称
	*/
	@DataName(name = "菜单名称")
	@ApiModelProperty(value = "菜单名称", position = 4)
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;	  
   
	/**
	* 菜单昵称
	*/
	@DataName(name = "菜单昵称")
	@ApiModelProperty(value = "菜单昵称", position = 5)
	@NotBlank(message = "菜单昵称不能为空")
	private String menuNickName;	  
   
	/**
	* 菜单编号
	*/
	@DataName(name = "菜单编号")
	@ApiModelProperty(value = "菜单编号", position = 6)
	@NotBlank(message = "菜单编号不能为空")
	private String menuNo;	  
   
	/**
	* 路由地址
	*/
	@DataName(name = "路由地址")
	@ApiModelProperty(value = "路由地址", position = 7)
	@NotBlank(message = "路由地址不能为空")
	private String routeAddress;	  
   
	/**
	* 菜单图标
	*/
	@DataName(name = "菜单图标")
	@ApiModelProperty(value = "菜单图标", position = 8)
	@NotBlank(message = "菜单图标不能为空")
	private String menuIcon;	  
   
	/**
	* 菜单序号
	*/
	@DataName(name = "菜单序号")
	@ApiModelProperty(value = "菜单序号", position = 9)
	private int menuSeq;


	/**
	 * APP图标
	 */
 	@DataName(name = "APP图标", isRecordHistory = true)
	@ApiModelProperty(value = "APP图标", position = 6)
	private String appIcon;

	/**
	 * APP路由地址
	 */
 	@DataName(name = "APP路由地址", isRecordHistory = true)
	@ApiModelProperty(value = "APP路由地址", position = 6)
	private String appRouteAddress;
 	
	/**
	* 是否有子节点
	*/
	@DataName(name = "是否有子节点")
	@ApiModelProperty(value = "是否有子节点", position = 10)
	private String hasChildren;	  
   
	/**
	* 上级菜单名称
	*/
	@DataName(name = "上级菜单名称")
	@ApiModelProperty(value = "上级菜单名称", position = 11)
	private String parentMenuName;	  
   
	/**
	* 保持活动标志
	*/
	@DataName(name = "保持活动标志")
	@ApiModelProperty(value = "保持活动标志", position = 12)
	private String keepAliveFlag;	  
	/**
	* 菜单等级
	*/
	@DataName(name = "菜单等级")
	@ApiModelProperty(value = "菜单等级", position = 13)
	private String menuLevel;	 
	
	/**
	* 项目控制标志
	*/
	@DataName(name = "项目控制标志")
	@ApiModelProperty(value = "项目控制标志", position = 6)
	@Length(max=1,message="项目控制标志长度不能大于1")
	private String projectControlFlag;
	
	/**
	* 菜单备注
	*/
	@DataName(name = "备注")
	@ApiModelProperty(value = "备注", position = 14)
	private String remark;	  
 }
