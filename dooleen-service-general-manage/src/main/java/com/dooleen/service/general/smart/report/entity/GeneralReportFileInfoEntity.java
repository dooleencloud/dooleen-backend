package com.dooleen.service.general.smart.report.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
 * @CreateDate : 2020-08-05 11:52:21
 * @Description : 报表定义文件信息管理实体
 * @Author : name
 * @Update : 2020-08-05 11:52:21
 */
@Data
@TableName("general_report_file_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralReportFileInfoEntity extends BaseEntity implements Serializable  {

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
	* 标题
	*/
	@DataName(name = "标题")
	@ApiModelProperty(value = "标题", position = 2)
	@Length(max=200,message="标题长度不能大于200")
	@NotBlank(message = "标题不能为空")
	private String title;	  
   
	
	/**
	* 名称
	*/
	@DataName(name = "名称")
	@ApiModelProperty(value = "名称", position = 3)
	@Length(max=50,message="名称长度不能大于50")
	@NotBlank(message = "名称不能为空")
	private String name;	  
   
	
	/**
	* 目录ID
	*/
	@DataName(name = "目录ID")
	@ApiModelProperty(value = "目录ID", position = 4)
	@Length(max=20,message="目录ID长度不能大于20")
	@NotBlank(message = "目录ID不能为空")
	private String catalogId;	  
   
	
	/**
	* 目录名称
	*/
	@DataName(name = "目录名称")
	@ApiModelProperty(value = "目录名称", position = 5)
	@Length(max=50,message="目录名称长度不能大于50")
	private String catalogName;	  
   
	
	/**
	* 内容
	*/
	@DataName(name = "内容")
	@ApiModelProperty(value = "内容", position = 6)
	private String content;	  
   
	
	/**
	* 分享标志
	*/
	@DataName(name = "分享标志")
	@ApiModelProperty(value = "分享标志", position = 7)
	@Length(max=1,message="分享标志长度不能大于1")
	private String shareFlag;	  
   
	
	/**
	* 分享角色组
	*/
	@DataName(name = "分享角色组")
	@ApiModelProperty(value = "分享角色组", position = 8)
	@Length(max=2000,message="分享角色组长度不能大于2000")
	private String shareRoleGroup;	  
   
	
	/**
	* 分享用户组
	*/
	@DataName(name = "分享用户组")
	@ApiModelProperty(value = "分享用户组", position = 9)
	@Length(max=2000,message="分享用户组长度不能大于2000")
	private String shareUserGroup;	  
 }
