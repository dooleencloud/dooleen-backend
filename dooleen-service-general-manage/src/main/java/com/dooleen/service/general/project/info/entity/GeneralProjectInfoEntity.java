package com.dooleen.service.general.project.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.aop.annos.DataName;
import com.dooleen.common.core.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 17:11:02
 * @Description : 项目信息管理实体
 * @Author : name
 * @Update : 2020-06-28 17:11:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_project_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralProjectInfoEntity extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(isRecordHistory = true, name = "主键", isKeyId = true)
    @ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(isRecordHistory = true, name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
   
	
	/**
	* 项目编号
	*/
	@DataName(isRecordHistory = true, name = "项目编号")
	@ApiModelProperty(value = "项目编号", position = 2)
	@Length(max=30,message="项目编号长度不能大于30")
	@NotBlank(message = "项目编号不能为空")
	private String projectNo;	  
   
	
	/**
	* 项目名称
	*/
	@DataName(isRecordHistory = true, name = "项目名称")
	@ApiModelProperty(value = "项目名称", position = 3)
	@Length(max=50,message="项目名称长度不能大于50")
	@NotBlank(message = "项目名称不能为空")
	private String projectName;	  
   
	
	/**
	* 项目分类
	*/
	@DataName(isRecordHistory = true, name = "项目分类")
	@ApiModelProperty(value = "项目分类", position = 4)
	@Length(max=30,message="项目分类长度不能大于30")
	@NotBlank(message = "项目分类不能为空")
	private String projectCategory;	  
   
	
	/**
	* 项目简称
	*/
	@DataName(isRecordHistory = true, name = "项目简称")
	@ApiModelProperty(value = "项目简称", position = 5)
	@Length(max=30,message="项目简称长度不能大于30")
	private String projectShortName;	  
   
	
	/**
	* 需求编号
	*/
	@DataName(isRecordHistory = true, name = "需求编号")
	@ApiModelProperty(value = "需求编号", position = 6)
	@Length(max=30,message="需求编号长度不能大于30")
	private String demandNo;	  
   
	
	/**
	* 需求名称
	*/
	@DataName(isRecordHistory = true, name = "需求名称")
	@ApiModelProperty(value = "需求名称", position = 7)
	@Length(max=50,message="需求名称长度不能大于50")
	private String demandName;


    /**
     * 实施主办部门
     */
    @DataName(name = "实施主办部门")
    @ApiModelProperty(value = "实施主办部门", position = 8)
    @Length(max=30,message="实施主办部门长度不能大于30")
    private String makeMainDeptName;

    /**
     * 实施协办部门
     */
    @DataName(name = "实施协办部门")
    @ApiModelProperty(value = "实施协办部门", position = 9)
    @Length(max=30,message="实施协办部门长度不能大于30")
    private String makeAssistDeptName;

    /**
     * 业务主办部门
     */
    @DataName(name = "业务主办部门")
    @ApiModelProperty(value = "业务主办部门", position = 10)
    @Length(max=30,message="业务主办部门长度不能大于30")
    private String bizMainDeptName;

    /**
     * 业务协办部门
     */
    @DataName(name = "业务协办部门")
    @ApiModelProperty(value = "业务协办部门", position = 11)
    @Length(max=30,message="业务协办部门长度不能大于30")
    private String bizAssistDeptName;
   
	
	/**
	* 建设方式
	*/
	@DataName(isRecordHistory = true, name = "建设方式")
	@ApiModelProperty(value = "建设方式", position = 12)
	@Length(max=30,message="建设方式长度不能大于30")
	private String buildWay;	  
   
	
	/**
	* 实施方式
	*/
	@DataName(isRecordHistory = true, name = "实施方式")
	@ApiModelProperty(value = "实施方式", position = 13)
	@Length(max=30,message="实施方式长度不能大于30")
	private String makeWay;	  
   
	
	/**
	* 项目经理
	*/
	@DataName(isRecordHistory = true, name = "项目经理")
	@ApiModelProperty(value = "项目经理", position = 14)
	@Length(max=30,message="项目经理长度不能大于30")
	@NotBlank(message = "项目经理不能为空")
	private String projectManagerName;	  

	/**
	* 项目经理姓名
	*/
	@DataName(isRecordHistory = true, name = "项目经理姓名")
	@ApiModelProperty(value = "项目经理姓名", position = 15)
	@Length(max=30,message="项目经理姓名长度不能大于30")
	@NotBlank(message = "项目经理姓名不能为空")
	private String projectManagerRealName;
	
	/**
	* 项目级别
	*/
	@DataName(isRecordHistory = true, name = "项目级别")
	@ApiModelProperty(value = "项目级别", position = 16)
	@Length(max=30,message="项目级别长度不能大于30")
	private String projectGrade;	  
   
	
	/**
	* 优先等级
	*/
	@DataName(isRecordHistory = true, name = "优先等级")
	@ApiModelProperty(value = "优先等级", position = 17)
	@Length(max=10,message="优先等级长度不能大于10")
	private String priorLevel;	  
   
	/**
	* 预计工作量
	*/
	@DataName(isRecordHistory = true, name = "预计工作量")
	@ApiModelProperty(value = "预计工作量", position = 18)
	@Length(max=20,message="预计工作量长度不能大于20")
	private String expectWorkload;	  
   
	/**
	* 预算类目
	*/
	@DataName(isRecordHistory = true, name = "预算类目")
	@ApiModelProperty(value = "预算类目", position = 19)
	@Length(max=30,message="预算类目长度不能大于30")
	private String budgetClass;	  
   
	/**
	* 预算金额
	*/
	@DataName(isRecordHistory = true, name = "预算金额")
	@ApiModelProperty(value = "预算金额", position = 20)
	@Digits(integer=13,fraction=2, message = "预算金额请填9999999999999以内数字")
	private BigDecimal budgetAmt;	  
   
	
	/**
	* 项目状态
	*/
	@DataName(isRecordHistory = true, name = "项目状态")
	@ApiModelProperty(value = "项目状态", position = 21)
	@Length(max=2,message="项目状态长度不能大于2")
	private String projectStatus;	  
   
	
	/**
	* 计划开始日期
	*/
	@DataName(isRecordHistory = true, name = "计划开始日期")
	@ApiModelProperty(value = "计划开始日期", position = 22)
	@Length(max=20,message="计划开始日期长度不能大于20")
	private String planBeginDate;	  
   
	
	/**
	* 计划完成日期
	*/
	@DataName(isRecordHistory = true, name = "计划完成日期")
	@ApiModelProperty(value = "计划完成日期", position = 23)
	@Length(max=20,message="计划完成日期长度不能大于20")
	private String planFinishDate;	  
   
	
	/**
	* 实际开始日期
	*/
	@DataName(isRecordHistory = true, name = "实际开始日期")
	@ApiModelProperty(value = "实际开始日期", position = 24)
	@Length(max=20,message="实际开始日期长度不能大于20")
	private String actualBeginDate;	  
   
	
	/**
	* 实际完成日期
	*/
	@DataName(isRecordHistory = true, name = "实际完成日期")
	@ApiModelProperty(value = "实际完成日期", position = 25)
	@Length(max=20,message="实际完成日期长度不能大于20")
	private String actualFinishDate;	  
   
	
	/**
	* 立项文档地址
	*/
	@DataName(isRecordHistory = true, name = "立项文档地址")
	@ApiModelProperty(value = "立项文档地址", position = 26)
	@Length(max=100,message="立项文档地址长度不能大于100")
	private String apprProjectFileAddress;	  
   
	
	/**
	* 项目描述
	*/
	@DataName(isRecordHistory = true, name = "项目描述")
	@ApiModelProperty(value = "项目描述", position = 27)
	@Length(max=2000,message="项目描述长度不能大于2000")
	private String projectDesc;	  
   
	
	/**
	* 备注
	*/
	@DataName(isRecordHistory = true, name = "备注")
	@ApiModelProperty(value = "备注", position = 28)
	@Length(max=5000,message="备注长度不能大于5000")
	private String remark;

	/**
	 * 项目规模
	 */
	@DataName(name = "项目规模")
	@ApiModelProperty(value = "项目规模", position = 29)
	@Length(max=30,message="项目规模长度不能大于30")
	private String projectScale;

	/**
	 * 涉及重要系统标志
	 */
	@DataName(name = "涉及重要系统标志")
	@ApiModelProperty(value = "涉及重要系统标志", position = 30)
	@Length(max=1,message="涉及重要系统标志长度不能大于1")
	private String involveImportantSystemFlag;

    /**
     * 主子项目标志
     */
    @DataName(name = "主子项目标志")
    @ApiModelProperty(value = "主子项目标志", position = 31)
    @Length(max=30,message="主子项目标志长度不能大于30")
    private String mainSubProjectFlag;


    /**
     * 主项目编号
     */
    @DataName(name = "主项目编号")
    @ApiModelProperty(value = "主项目编号", position = 32)
    @Length(max=30,message="主项目编号长度不能大于30")
    private String mainProjectNo;

    /**
     * 主项目名称
     */
    @DataName(name = "主项目名称")
    @ApiModelProperty(value = "主项目名称", position = 33)
    @Length(max=50,message="主项目名称长度不能大于50")
    private String mainProjectName;
 }
