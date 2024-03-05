package com.dooleen.service.biz.model.task.item.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2022-01-04 23:16:29
 * @Description : 模型任务项管理实体
 * @Author : name
 * @Update : 2022-01-04 23:16:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_model_task_item")
@ApiModel(value = "模型任务项管理实体")
@ToString(callSuper = true)
public class BizModelTaskItemEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true, isRecordHistory = true)
    @ApiModelProperty(value = "id", required=true, position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", required=true, position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	
	/**
	* 所属系统ID
	*/
	@DataName(name = "所属系统ID", isRecordHistory = true)
	@ApiModelProperty(value = "所属系统ID", required=false,  position = 2)
	@Length(max=20,message="所属系统ID长度不能大于20")
	private String belongSystemId;	  
   
	
	/**
	* 所属系统名
	*/
	@DataName(name = "所属系统名", isRecordHistory = true)
	@ApiModelProperty(value = "所属系统名", required=false,  position = 3)
	@Length(max=50,message="所属系统名长度不能大于50")
	private String belongSystemName;	  
   
	
	/**
	* 排序序号
	*/
	@DataName(name = "排序序号", isRecordHistory = true)
	@ApiModelProperty(value = "排序序号", required=false,  position = 4)
    @DecimalMax(value="9999999999",message="排序序号不能大于9999999999")
	@DecimalMin(value="0",message="排序序号不能小于0")
	private int orderSeq;	  
   
	
	/**
	* 操作名称
	*/
	@Excel(name ="操作名称")
	@DataName(name = "操作名称", isRecordHistory = true)
	@ApiModelProperty(value = "操作名称", required=true, position = 5)
	@Length(max=50,message="操作名称长度不能大于50")
	@NotBlank(message = "操作名称不能为空")
	private String handleName;	  
   
	
	/**
	* 业务实体名称
	*/
	@Excel(name ="业务实体名称")
	@DataName(name = "业务实体名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务实体名称", required=true, position = 6)
	@Length(max=50,message="业务实体名称长度不能大于50")
	@NotBlank(message = "业务实体名称不能为空")
	private String bizEntityName;	  
   
	
	/**
	* 判断条件
	*/
	@Excel(name ="判断条件")
	@DataName(name = "判断条件", isRecordHistory = true)
	@ApiModelProperty(value = "判断条件", required=false,  position = 7)
	@Length(max=500,message="判断条件长度不能大于500")
	private String judgeCondition;	  
   
	
	/**
	* 变更属性值
	*/
	@Excel(name ="变更属性值")
	@DataName(name = "变更属性值", isRecordHistory = true)
	@ApiModelProperty(value = "变更属性值", required=false,  position = 8)
	@Length(max=2000,message="变更属性值长度不能大于2000")
	private String changeAttributeValue;	  
   
	
	/**
	* 公共规则组
	*/
	@Excel(name ="公共规则组")
	@DataName(name = "公共规则组", isRecordHistory = true)
	@ApiModelProperty(value = "公共规则组", required=false,  position = 9)
	@Length(max=2000,message="公共规则组长度不能大于2000")
	private String publicRuleGroup;	  
   
	
	/**
	* 特有规则组
	*/
	@Excel(name ="特有规则组")
	@DataName(name = "特有规则组", isRecordHistory = true)
	@ApiModelProperty(value = "特有规则组", required=false,  position = 10)
	@Length(max=2000,message="特有规则组长度不能大于2000")
	private String specificRuleGroup;	  
   
	
	/**
	* 核算规则组
	*/
	@Excel(name ="核算规则组")
	@DataName(name = "核算规则组", isRecordHistory = true)
	@ApiModelProperty(value = "核算规则组", required=false,  position = 11)
	@Length(max=2000,message="核算规则组长度不能大于2000")
	private String accountingRuleGroup;	  
   
	
	/**
	* 需求条目名称
	*/
	@Excel(name ="需求条目名称")
	@DataName(name = "需求条目名称", isRecordHistory = true)
	@ApiModelProperty(value = "需求条目名称", required=false,  position = 12)
	@Length(max=50,message="需求条目名称长度不能大于50")
	private String demandItemName;	  
   
	
	/**
	* 任务执行前提条件
	*/
	@Excel(name ="任务执行前提条件")
	@DataName(name = "任务执行前提条件", isRecordHistory = true)
	@ApiModelProperty(value = "任务执行前提条件", required=false,  position = 13)
	@Length(max=500,message="任务执行前提条件长度不能大于500")
	private String taskExecPremiseCondition;	  
   
	
	/**
	* 任务执行角色名称
	*/
	@Excel(name ="任务执行角色名称")
	@DataName(name = "任务执行角色名称", isRecordHistory = true)
	@ApiModelProperty(value = "任务执行角色名称", required=false,  position = 14)
	@Length(max=50,message="任务执行角色名称长度不能大于50")
	private String taskExecRoleName;	  
   
	
	/**
	* 任务输入组
	*/
	@Excel(name ="任务输入组")
	@DataName(name = "任务输入组", isRecordHistory = true)
	@ApiModelProperty(value = "任务输入组", required=false,  position = 15)
	@Length(max=2000,message="任务输入组长度不能大于2000")
	private String taskInputGroup;	  
   
	
	/**
	* 任务输出组
	*/
	@Excel(name ="任务输出组")
	@DataName(name = "任务输出组", isRecordHistory = true)
	@ApiModelProperty(value = "任务输出组", required=false,  position = 16)
	@Length(max=2000,message="任务输出组长度不能大于2000")
	private String taskOutputGroup;	  
   
	
	/**
	* 所属需求章节名称
	*/
	@Excel(name ="所属需求章节名称")
	@DataName(name = "所属需求章节名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属需求章节名称", required=false,  position = 17)
	@Length(max=50,message="所属需求章节名称长度不能大于50")
	private String belongDemandChapterName;	  
   
	
	/**
	* 所属领域中心名称
	*/
	@Excel(name ="所属领域中心名称")
	@DataName(name = "所属领域中心名称", isRecordHistory = true)
	@ApiModelProperty(value = "所属领域中心名称", required=false,  position = 18)
	@Length(max=50,message="所属领域中心名称长度不能大于50")
	private String belongDomainCenterName;	  
   
	
	/**
	* 所属产品组件
	*/
	@Excel(name ="所属产品组件")
	@DataName(name = "所属产品组件", isRecordHistory = true)
	@ApiModelProperty(value = "所属产品组件", required=false,  position = 19)
	@Length(max=100,message="所属产品组件长度不能大于100")
	private String belongProductComponent;	  
 }
