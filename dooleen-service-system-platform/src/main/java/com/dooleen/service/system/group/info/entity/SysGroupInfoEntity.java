package com.dooleen.service.system.group.info.entity;

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
 * @CreateDate : 2020-06-12 06:08:42
 * @Description : 系统用户组管理实体
 * @Author : name
 * @Update : 2020-06-12 06:08:42
 */
@Data
@TableName("sys_group_info")
@ApiModel
@ToString(callSuper = true)
public class SysGroupInfoEntity extends BaseEntity implements Serializable  {

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
	* 用户组分类
	*/
	@DataName(name = "用户组分类")
	@ApiModelProperty(value = "用户组分类", position = 2)
	@Length(max=30,message="用户组分类长度不能大于30")
	@NotBlank(message = "用户组分类不能为空")
	private String userGroupCategory;	  
   
	/**
	* 用户组名称
	*/
	@DataName(name = "用户组名称")
	@ApiModelProperty(value = "用户组名称", position = 3)
	@Length(max=50,message="用户组名称长度不能大于50")
	@NotBlank(message = "用户组名称不能为空")
	private String userGroupName;	  
   
	/**
	* 上级用户组ID
	*/
	@DataName(name = "上级用户组ID")
	@ApiModelProperty(value = "上级用户组ID", position = 4)
	@Length(max=20,message="上级用户组ID长度不能大于20")
	private String parentUserGroupId;	  
   
	/**
	* 上级用户组名称
	*/
	@DataName(name = "上级用户组名称")
	@ApiModelProperty(value = "上级用户组名称", position = 5)
	@Length(max=50,message="上级用户组名称长度不能大于50")
	private String parentUserGroupName;	  
   
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号")
	@ApiModelProperty(value = "排序序号", position = 6)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int orderSeq;	  
   
	/**
	* 备注
	*/
	@DataName(name = "备注")
	@ApiModelProperty(value = "备注", position = 7)
	@NotBlank(message = "备注不能为空")
	@Length(max=5000,message="备注长度不能大于5000")
	private String remark;	  
 }
