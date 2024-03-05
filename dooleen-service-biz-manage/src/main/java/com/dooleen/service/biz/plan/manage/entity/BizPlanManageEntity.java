package com.dooleen.service.biz.plan.manage.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;

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
 * @CreateDate : 2021-04-29 17:25:59
 * @Description : 计划管理维护实体
 * @Author : name
 * @Update : 2021-04-29 17:25:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_plan_manage")
@ApiModel
@ToString(callSuper = true)
public class BizPlanManageEntity  extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@DataName(name = "主键", isKeyId = true,  isRecordHistory = true)
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
	 * 项目编号
	 */
	@DataName(name = "项目编号", isRecordHistory = true)
	@ApiModelProperty(value = "项目编号", position = 2)
	@Length(max=30,message="项目编号长度不能大于30")
	@NotBlank(message = "项目编号不能为空")
	private String projectNo;


	/**
	 * 项目名称
	 */
	@DataName(name = "项目名称", isRecordHistory = true)
	@ApiModelProperty(value = "项目名称", position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	@NotBlank(message = "项目名称不能为空")
	private String projectName;


	/**
	 * 迭代编号
	 */
	@DataName(name = "迭代编号", isRecordHistory = true)
	@ApiModelProperty(value = "迭代编号", position = 4)
	@Length(max=30,message="迭代编号长度不能大于30")
	@NotBlank(message = "迭代编号不能为空")
	private String iterationNo;


	/**
	 * 迭代名称
	 */
	@DataName(name = "迭代名称", isRecordHistory = true)
	@ApiModelProperty(value = "迭代名称", position = 5)
	@Length(max=50,message="迭代名称长度不能大于50")
	@NotBlank(message = "迭代名称不能为空")
	private String iterationName;


	/**
	 * 里程碑ID
	 */
	@DataName(name = "里程碑ID", isRecordHistory = true)
	@ApiModelProperty(value = "里程碑ID", position = 6)
	@Length(max=20,message="里程碑ID长度不能大于20")
	@NotBlank(message = "里程碑ID不能为空")
	private String milestoneId;


	/**
	 * 里程碑名称
	 */
	@DataName(name = "里程碑名称", isRecordHistory = true)
	@ApiModelProperty(value = "里程碑名称", position = 7)
	@Length(max=50,message="里程碑名称长度不能大于50")
	@NotBlank(message = "里程碑名称不能为空")
	private String milestoneName;


	/**
	 * 计划层级
	 */
	@DataName(name = "计划层级", isRecordHistory = true)
	@ApiModelProperty(value = "计划层级", position = 8)
	@Length(max=100,message="计划层级长度不能大于100")
	private String planLevel;


	/**
	 * 父级计划ID
	 */
	@DataName(name = "父级计划ID", isRecordHistory = true)
	@ApiModelProperty(value = "父级计划ID", position = 9)
	@Length(max=20,message="父级计划ID长度不能大于20")
	private String parentPlanId;


	/**
	 * 父级计划名称
	 */
	@DataName(name = "父级计划名称", isRecordHistory = true)
	@ApiModelProperty(value = "父级计划名称", position = 10)
	@Length(max=50,message="父级计划名称长度不能大于50")
	private String parentPlanName;



	/**
	 * 前置计划ID
	 */
	@DataName(name = "前置计划ID", isRecordHistory = true)
	@ApiModelProperty(value = "前置计划ID", position = 6)
	@Length(max=20,message="前置计划ID长度不能大于20")
	private String prevPlanId;

	/**
	 * 前置计划名称
	 */
	@DataName(name = "前置计划名称", isRecordHistory = true)
	@ApiModelProperty(value = "前置计划名称", position = 6)
	@Length(max=50,message="前置计划名称长度不能大于50")
	private String prevPlanName;
	/**
	 * 计划类型
	 */
	@DataName(name = "计划类型", isRecordHistory = true)
	@ApiModelProperty(value = "计划类型", position = 11)
	@Length(max=30,message="计划类型长度不能大于30")
	@NotBlank(message = "计划类型不能为空")
	private String planType;


	/**
	 * 计划名称
	 */
	@DataName(name = "计划名称", isRecordHistory = true)
	@ApiModelProperty(value = "计划名称", position = 12)
	@Length(max=50,message="计划名称长度不能大于50")
	@NotBlank(message = "计划名称不能为空")
	private String planName;


	/**
	 * 计划描述
	 */
	@DataName(name = "计划描述", isRecordHistory = true)
	@ApiModelProperty(value = "计划描述", position = 13)
	@Length(max=2000,message="计划描述长度不能大于2000")
	private String planDesc;


	/**
	 * 责任人姓名
	 */
	@DataName(name = "责任人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "责任人姓名", position = 14)
	@Length(max=50,message="责任人姓名长度不能大于50")
	@NotBlank(message = "责任人姓名不能为空")
	private String respsbltPersonName;


	/**
	 * 责任人
	 */
	@DataName(name = "责任人", isRecordHistory = true)
	@ApiModelProperty(value = "责任人", position = 15)
	@Length(max=20,message="责任人长度不能大于20")
	@NotBlank(message = "责任人不能为空")
	private String respsbltPersonUserName;


	/**
	 * 干系人列表
	 */
	@DataName(name = "干系人列表", isRecordHistory = true)
	@ApiModelProperty(value = "干系人列表", position = 16)
	@Length(max=2000,message="干系人列表长度不能大于2000")
	private String stakeholderList;


	/**
	 * 计划开始日期
	 */
	@DataName(name = "计划开始日期", isRecordHistory = true)
	@ApiModelProperty(value = "计划开始日期", position = 17)
	@Length(max=20,message="计划开始日期长度不能大于20")
	@NotBlank(message = "计划开始日期不能为空")
	private String planBeginDate;


	/**
	 * 计划完成日期
	 */
	@DataName(name = "计划完成日期", isRecordHistory = true)
	@ApiModelProperty(value = "计划完成日期", position = 18)
	@Length(max=20,message="计划完成日期长度不能大于20")
	@NotBlank(message = "计划完成日期不能为空")
	private String planFinishDate;


	/**
	 * 实际开始日期
	 */
	@DataName(name = "实际开始日期", isRecordHistory = true)
	@ApiModelProperty(value = "实际开始日期", position = 19)
	@Length(max=20,message="实际开始日期长度不能大于20")
	private String actualBeginDate;


	/**
	 * 实际完成日期
	 */
	@DataName(name = "实际完成日期", isRecordHistory = true)
	@ApiModelProperty(value = "实际完成日期", position = 20)
	@Length(max=20,message="实际完成日期长度不能大于20")
	private String actualFinishDate;


	/**
	 * 计划投入用时
	 */
	@DataName(name = "计划投入用时", isRecordHistory = true)
	@ApiModelProperty(value = "计划投入用时", position = 21)
	@Length(max=100,message="计划投入用时长度不能大于100")
	private String planInvestmentTime;


	/**
	 * 实际投入用时
	 */
	@DataName(name = "实际投入用时", isRecordHistory = true)
	@ApiModelProperty(value = "实际投入用时", position = 22)
	@Length(max=100,message="实际投入用时长度不能大于100")
	private String actualInvestmentTime;


	/**
	 * 剩余用时
	 */
	@DataName(name = "剩余用时", isRecordHistory = true)
	@ApiModelProperty(value = "剩余用时", position = 23)
	@Length(max=100,message="剩余用时长度不能大于100")
	private String leftTime;


	/**
	 * 进度
	 */
	@DataName(name = "进度", isRecordHistory = true)
	@ApiModelProperty(value = "进度", position = 24)
	@DecimalMax(value="99999999999",message="进度不能大于99999999999")
	@DecimalMin(value="0",message="进度不能小于0")
	private int progress;


	/**
	 * 状态
	 */
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 25)
	@Length(max=30,message="状态长度不能大于30")
	private String status;


	/**
	 * 优先级
	 */
	@DataName(name = "优先级", isRecordHistory = true)
	@ApiModelProperty(value = "优先级", position = 26)
	@Length(max=30,message="优先级长度不能大于30")
	private String priorLevel;


	/**
	 * 关联计划ID
	 */
	@DataName(name = "关联计划ID", isRecordHistory = true)
	@ApiModelProperty(value = "关联计划ID", position = 27)
	@Length(max=20,message="关联计划ID长度不能大于20")
	private String relationPlanId;


	/**
	 * 颜色
	 */
	@DataName(name = "颜色", isRecordHistory = true)
	@ApiModelProperty(value = "颜色", position = 28)
	@Length(max=50,message="颜色长度不能大于50")
	private String color;

	/**
	 * 排序序号
	 */
	@DataName(name = "排序序号", isRecordHistory = true)
	@ApiModelProperty(value = "排序序号", position = 6)
	@Length(max=10,message="排序序号长度不能大于10")
	private String orderSeq;
}
