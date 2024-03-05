package com.dooleen.service.app.gateway.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @CreateDate : 2020-06-10 16:26:18
 * @Description : 系统接口管理实体
 * @Author : name
 * @Update : 2020-06-10 16:26:18
 */
@Data
@ApiModel
@ToString(callSuper = true)
public class SysApiScopeEntity extends BaseEntity implements Serializable  {

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
	* 菜单ID
	*/
	@DataName(name = "菜单ID")
	@ApiModelProperty(value = "菜单ID", position = 2)
	private String menuId;	  
   
	/**
	* 菜单名称
	*/
	@DataName(name = "菜单名称")
	@ApiModelProperty(value = "菜单名称", position = 3)
	private String menuName;	  
   
	/**
	* 接口分类
	*/
	@DataName(name = "接口分类")
	@ApiModelProperty(value = "接口分类", position = 4)
	@NotBlank(message = "接口分类不能为空")
	private String interfaceCategory;	  
   
	/**
	* 接口名称
	*/
	@DataName(name = "接口名称")
	@ApiModelProperty(value = "接口名称", position = 5)
	@NotBlank(message = "接口名称不能为空")
	private String interfaceName;	  
   
	/**
	* 接口地址
	*/
	@DataName(name = "接口地址")
	@ApiModelProperty(value = "接口地址", position = 6)
	@NotBlank(message = "接口地址不能为空")
	private String interfaceAddress;	  
   
	/**
	* 接口编码
	*/
	@DataName(name = "接口编码")
	@ApiModelProperty(value = "接口编码", position = 7)
	@NotBlank(message = "接口编码不能为空")
	private String interfaceCode;	  
   
	/**
	* 备注
	*/
	@DataName(name = "备注")
	@ApiModelProperty(value = "备注", position = 8)
	private String remark;	  
 }
