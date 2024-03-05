package com.dooleen.service.system.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

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
@TableName("sys_role")
@ApiModel
@ToString(callSuper = true)
public class SysRoleEntity extends BaseEntity implements Serializable  {

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
	@ApiModelProperty(value = "项目ID", position = 6)
	@Length(max=20,message="项目ID长度不能大于20")
	private String projectId;   
	/**
	* 角色名称
	*/
	@DataName(name = "角色名称")
	@ApiModelProperty(value = "角色名称", position = 2)
	@Length(max=50,message="角色名称长度不能大于50")
	@NotBlank(message = "角色名称不能为空")
	private String roleName;	  
   
	/**
	* 角色昵称
	*/
	@DataName(name = "角色昵称")
	@ApiModelProperty(value = "角色昵称", position = 3)
	@Length(max=50,message="角色昵称长度不能大于50")
	@NotBlank(message = "角色昵称不能为空")
	private String roleNickName;	  
   
	/**
	* 角色分组名
	*/
	@DataName(name = "角色分组名")
	@ApiModelProperty(value = "角色分组名", position = 4)
	@Length(max=30,message="角色分组名长度不能大于30")
	@NotBlank(message = "角色分组名不能为空")
	private String roleGroupName;	  
	/**
	* 角色类型
	*/
	@DataName(name = "角色类型")
	@ApiModelProperty(value = "角色类型", position = 4)
	private String roleType;	
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
