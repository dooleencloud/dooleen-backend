package com.dooleen.service.system.org.info.entity;

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
 * @CreateDate : 2020-06-04 00:05:24
 * @Description : 组织机构管理实体
 * @Author : name
 * @Update : 2020-06-04 00:05:24
 */
@Data
@TableName("sys_org_info")
@ApiModel
@ToString(callSuper = true)
public class SysOrgInfoEntity extends BaseEntity implements Serializable  {

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
	* 机构号
	*/
	@DataName(name = "机构号")
	@ApiModelProperty(value = "机构号", position = 2)
	@Length(max=30,message="机构号长度不能大于30")
	@NotBlank(message = "机构号不能为空")
	private String orgNo;	  
   
	/**
	* 机构类型
	*/
	@DataName(name = "机构类型")
	@ApiModelProperty(value = "机构类型", position = 3)
	@NotBlank(message = "机构类型不能为空")
	private String orgType;

	/**
	 * 机构名称
	 */
	@DataName(name = "机构名称")
	@ApiModelProperty(value = "机构名称", position = 4)
	@Length(max=50,message="机构名称长度不能大于50")
	@NotBlank(message = "机构名称不能为空")
	private String orgName;

	/**
	* 机构全名
	*/
	@DataName(name = "机构全名")
	@ApiModelProperty(value = "机构全名", position = 5)
	@Length(max=50,message="机构全名长度不能大于50")
	@NotBlank(message = "机构全名不能为空")
	private String orgFullName;	  
   
	/**
	* 上级机构ID
	*/
	@DataName(name = "上级机构号")
	@ApiModelProperty(value = "上级机构号", position = 6)
	@NotBlank(message = "上级机构号不能为空")
	private String parentOrgNo;	 
	
	/**
	* 所属机构组
	*/
	@DataName(name = "所属机构组")
	@ApiModelProperty(value = "所属机构组", position = 8)
	private String belongOrgGroup ;	  
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号")
	@ApiModelProperty(value = "排序序号", position = 7)
	@DecimalMax(value="9999999999",message="数字不能大于10")
	@DecimalMin(value="0",message="数字不能小于0")
	private int orderSeq;	
	
	/**
	* 是否有子节点
	*/
	@DataName(name = "是否有子节点")
	@ApiModelProperty(value = "是否有子节点", position = 8)
	private String hasChildren;	 
	
	/**
	* 负责人ID
	*/
	@DataName(name = "负责人ID")
	@ApiModelProperty(value = "负责人ID", position = 9)
	public String respsbUserId;
	
	/**
	* 负责人名
	*/
	@DataName(name = "负责人名")
	@ApiModelProperty(value = "负责人名", position = 10)
	public String respsbUserName;
	/**
	* 备注
	*/
	@DataName(name = "备注")
	@ApiModelProperty(value = "备注", position = 11)
	@Length(max=5000,message="备注长度不能大于5000")
	private String remark;	  
 }