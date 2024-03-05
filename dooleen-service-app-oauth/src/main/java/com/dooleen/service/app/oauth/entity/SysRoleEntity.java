package com.dooleen.service.app.oauth.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-07 21:34:01
 * @Description : 系统角色管理实体
 * @Author : name
 * @Update : 2020-06-07 21:34:01
 */
@Data
@ApiModel
public class SysRoleEntity  extends BaseEntity{
	public SysRoleEntity() {
	}
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
	* 角色名称
	*/
	@DataName(name = "角色名称")
	@ApiModelProperty(value = "角色名称", position = 2)
	@NotBlank(message = "角色名称不能为空")
	private String roleName;	  
   
	/**
	* 角色昵称
	*/
	@DataName(name = "角色昵称")
	@ApiModelProperty(value = "角色昵称", position = 3)
	@NotBlank(message = "角色昵称不能为空")
	private String roleNickName;	  
   
	/**
	* 角色类型
	*/
	@DataName(name = "角色分组名")
	@ApiModelProperty(value = "角色分组名", position = 4)
	@NotBlank(message = "角色分组名不能为空")
	private String roleGroupName;	  
   
	/**
	* 角色序号
	*/
	@DataName(name = "角色序号")
	@ApiModelProperty(value = "角色序号", position = 5)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int roleSeq;	  
   
	/**
	* 状态
	*/
	@DataName(name = "状态")
	@ApiModelProperty(value = "状态", position = 6)
	private String status;	  
 }
