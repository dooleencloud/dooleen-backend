package com.dooleen.common.core.app.general.staff.info.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

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
 * @CreateDate : 2020-06-18 18:12:48
 * @Description : 人员管理实体
 * @Author : name
 * @Update : 2020-06-18 18:12:48
 */
@Data
@TableName("general_staff_info")
@ApiModel
@ToString(callSuper = true)
public class GeneralStaffInfoEntity extends BaseEntity implements Serializable  {

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
	* 员工ID
	*/
	@DataName(name = "员工ID")
	@ApiModelProperty(value = "员工ID", position = 2)
	@NotBlank(message = "员工ID不能为空")
	private String staffId;	  
   
	/**
	* 员工类型
	*/
	@DataName(name = "员工类型")
	@ApiModelProperty(value = "员工类型", position = 3)
	@NotBlank(message = "员工类型不能为空")
	private String staffType;	  
   
	/**
	* 员工姓名
	*/
	@DataName(name = "员工姓名")
	@ApiModelProperty(value = "员工姓名", position = 4)
	@NotBlank(message = "员工姓名不能为空")
	private String staffName;	  
   
	/**
	* 头像链接
	*/
	@DataName(name = "头像链接")
	@ApiModelProperty(value = "头像链接", position = 5)
	private String headImgUrl;	  
   
	/**
	* 性别
	*/
	@DataName(name = "性别")
	@ApiModelProperty(value = "性别", position = 6)
	@NotBlank(message = "性别不能为空")
	private String sex;	  
   
	/**
	* 出生日期
	*/
	@DataName(name = "出生日期")
	@ApiModelProperty(value = "出生日期", position = 7)
	@NotBlank(message = "出生日期不能为空")
	private String birthDate;	  
   
	/**
	* 身份证号码
	*/
	@DataName(name = "身份证号码")
	@ApiModelProperty(value = "身份证号码", position = 8)
	@NotBlank(message = "身份证号码不能为空")
	private String idCardNo;	  
   
	/**
	* 民族
	*/
	@DataName(name = "民族")
	@ApiModelProperty(value = "民族", position = 9)
	@NotBlank(message = "民族不能为空")
	private String nation;	  
   
	/**
	* 籍贯
	*/
	@DataName(name = "籍贯")
	@ApiModelProperty(value = "籍贯", position = 10)
	@NotBlank(message = "籍贯不能为空")
	private String nativePlace;	  
   
	/**
	* 出生地址
	*/
	@DataName(name = "出生地址")
	@ApiModelProperty(value = "出生地址", position = 11)
	@NotBlank(message = "出生地址不能为空")
	private String birthAddress;	  
   
	/**
	* 政治面貌
	*/
	@DataName(name = "政治面貌")
	@ApiModelProperty(value = "政治面貌", position = 12)
	@NotBlank(message = "政治面貌不能为空")
	private String politicalPoliticalStatus;	  
   
	/**
	* 婚姻状况
	*/
	@DataName(name = "婚姻状况")
	@ApiModelProperty(value = "婚姻状况", position = 13)
	@NotBlank(message = "婚姻状况不能为空")
	private String marriageCond;	  
   
	/**
	* 健康状况
	*/
	@DataName(name = "健康状况")
	@ApiModelProperty(value = "健康状况", position = 14)
	private String healthyCond;	  
   
	/**
	* 毕业院校名称
	*/
	@DataName(name = "毕业院校名称")
	@ApiModelProperty(value = "毕业院校名称", position = 15)
	private String graduationCollegeName;	  
   
	/**
	* 学历名称
	*/
	@DataName(name = "学历名称")
	@ApiModelProperty(value = "学历名称", position = 16)
	private String eduDegreeName;	  
   
	/**
	* 手机号码
	*/
	@DataName(name = "手机号码")
	@ApiModelProperty(value = "手机号码", position = 17)
	@NotBlank(message = "手机号码不能为空")
	private String mobileNo;	  
   
	/**
	* 邮箱地址
	*/
	@DataName(name = "邮箱地址")
	@ApiModelProperty(value = "邮箱地址", position = 18)
	@NotBlank(message = "邮箱地址不能为空")
	private String emailAddress;	  
   
	/**
	* 座机号码
	*/
	@DataName(name = "座机号码")
	@ApiModelProperty(value = "座机号码", position = 19)
	private String telNo;	  
   
	/**
	* 现居地址
	*/
	@DataName(name = "现居地址")
	@ApiModelProperty(value = "现居地址", position = 20)
	@NotBlank(message = "现居地址不能为空")
	private String liveAddress;	  
   
	/**
	* 语言信息
	*/
	@DataName(name = "语言信息")
	@ApiModelProperty(value = "语言信息", position = 21)
	private String lanInfo;	  
   
	/**
	* 教育信息列表
	*/
	@DataName(name = "教育信息列表")
	@ApiModelProperty(value = "教育信息列表", position = 22)
	private String eduInfoList;	  
   
	/**
	* 教育信息列表
	*/
	@TableField(exist = false)
	@DataName(name = "教育信息列表")
	@ApiModelProperty(value = "教育信息列表", position = 22)
	private List<EducationEntity> educationEntity;
	
	/**
	* 专业技能列表
	*/
	@DataName(name = "专业技能列表")
	@ApiModelProperty(value = "专业技能列表", position = 23)
	private String majorSkillList;	  
   
	/**
	* 资质信息列表
	*/
	@DataName(name = "资质信息列表")
	@ApiModelProperty(value = "资质信息列表", position = 24)
	private String qualInfoList;	  
   
	/**
	* 获奖信息列表
	*/
	@DataName(name = "获奖信息列表")
	@ApiModelProperty(value = "获奖信息列表", position = 25)
	private String awardInfoList;	  
   
	/**
	* 社会关系列表
	*/
	@DataName(name = "社会关系列表")
	@ApiModelProperty(value = "社会关系列表", position = 26)
	private String societyRelationList;	  
   
	/**
	* 职务级别
	*/
	@DataName(name = "职务级别")
	@ApiModelProperty(value = "职务级别", position = 27)
	private String dutyGrade;	  
   
	/**
	* 职务名称
	*/
	@DataName(name = "职务名称")
	@ApiModelProperty(value = "职务名称", position = 28)
	private String dutyName;	  
   
	/**
	* 岗位名称
	*/
	@DataName(name = "岗位名称")
	@ApiModelProperty(value = "岗位名称", position = 29)
	private String stationName;	  
   
	/**
	* 职称名称
	*/
	@DataName(name = "职称名称")
	@ApiModelProperty(value = "职称名称", position = 30)
	private String techTitleName;	  
   
	/**
	* 机构号
	*/
	@DataName(name = "所属机构号")
	@ApiModelProperty(value = "所属机构号", position = 31)
	private String belongOrgNo;	  
   
	/**
	* 机构名称
	*/
	@DataName(name = "所属机构名称")
	@ApiModelProperty(value = "所属机构名称", position = 32)
	private String belongOrgName;	  
   
	/**
	* 上级机构号
	*/
	@DataName(name = "上级机构号")
	@ApiModelProperty(value = "上级机构号", position = 33)
	private String parentOrgNo;	  
   
	/**
	* 上级机构名称
	*/
	@DataName(name = "上级机构名称")
	@ApiModelProperty(value = "上级机构名称", position = 34)
	private String parentOrgName;	  
   
	/**
	* 员工状态
	*/
	@DataName(name = "员工状态")
	@ApiModelProperty(value = "员工状态", position = 35)
	private String staffStatus;	  
   
	/**
	* 入职日期
	*/
	@DataName(name = "入职日期")
	@ApiModelProperty(value = "入职日期", position = 36)
	private String entryDate;	  
   
	/**
	* 入职经办人
	*/
	@DataName(name = "入职经办人")
	@ApiModelProperty(value = "入职经办人", position = 37)
	private String entryOptName;	  
   
	/**
	* 离职日期
	*/
	@DataName(name = "离职日期")
	@ApiModelProperty(value = "离职日期", position = 38)
	private String leaveDate;	  
   
	/**
	* 离职经办人
	*/
	@DataName(name = "离职经办人")
	@ApiModelProperty(value = "离职经办人", position = 39)
	private String leaveOptName;	  
   
	/**
	* 离职原因
	*/
	@DataName(name = "离职原因")
	@ApiModelProperty(value = "离职原因", position = 40)
	private String leaveReason;	  
   
	/**
	* 合同到期日期
	*/
	@DataName(name = "合同到期日期")
	@ApiModelProperty(value = "合同到期日期", position = 41)
	private String agreementExpireDate;	  
   
	/**
	* 合同链接
	*/
	@DataName(name = "合同链接")
	@ApiModelProperty(value = "合同链接", position = 42)
	private String agreementUrl;	  
   
	/**
	* 账号列表
	*/
	@DataName(name = "账号列表")
	@ApiModelProperty(value = "账号列表", position = 43)
	private String acctList;	  
   
	/**
	* 办公地址
	*/
	@DataName(name = "办公地址")
	@ApiModelProperty(value = "办公地址", position = 44)
	private String officeAddress;	  
   
	/**
	* 通用标签
	*/
	@DataName(name = "通用标签")
	@ApiModelProperty(value = "通用标签", position = 45)
	private String generalLabel;	  
   
	/**
	* 公司名称
	*/
	@DataName(name = "公司名称")
	@ApiModelProperty(value = "公司名称", position = 46)
	private String companyName;	  
   
	/**
	* 参加工作日期
	*/
	@DataName(name = "参加工作日期")
	@ApiModelProperty(value = "参加工作日期", position = 47)
	private String joinJobDate;	  
   
	/**
	* 金融工作年限
	*/
	@DataName(name = "金融工作年限")
	@ApiModelProperty(value = "金融工作年限", position = 48)
	private String financeJobYears;	  
   
	/**
	* 核心项目个数
	*/
	@DataName(name = "核心项目个数")
	@ApiModelProperty(value = "核心项目个数", position = 49)
	private int coreProjectCount;	  
   
	/**
	* 工作经历列表
	*/
	@DataName(name = "工作经历列表")
	@ApiModelProperty(value = "工作经历列表", position = 50)
	private String jobExpeList;	  
   
	/**
	* 项目经历列表
	*/
	@DataName(name = "项目经历列表")
	@ApiModelProperty(value = "项目经历列表", position = 51)
	private String projectExpeList;	  
   
	/**
	* 行内服务标志
	*/
	@DataName(name = "行内服务标志")
	@ApiModelProperty(value = "行内服务标志", position = 52)
	private String ownBankServiceFlag;	  
   
	/**
	* 行内项目列表
	*/
	@DataName(name = "行内项目列表")
	@ApiModelProperty(value = "行内项目列表", position = 53)
	private String ownBankProjectList;	  
   
	/**
	* 服务类型
	*/
	@DataName(name = "服务类型")
	@ApiModelProperty(value = "服务类型", position = 54)
	private String serviceType;	  
   
	/**
	* 驻场类型
	*/
	@DataName(name = "驻场类型")
	@ApiModelProperty(value = "驻场类型", position = 55)
	private String settleType;	  
   
	/**
	* 所属项目名称
	*/
	@DataName(name = "所属项目名称")
	@ApiModelProperty(value = "所属项目名称", position = 56)
	private String belongProjectName;	  
   
	/**
	* 级别名称
	*/
	@DataName(name = "级别名称")
	@ApiModelProperty(value = "级别名称", position = 57)
	private String levelName;	  
   
	/**
	* 档次名称
	*/
	@DataName(name = "档次名称")
	@ApiModelProperty(value = "档次名称", position = 58)
	private String gradeName;	  
   
	/**
	* 入场日期
	*/
	@DataName(name = "入场日期")
	@ApiModelProperty(value = "入场日期", position = 59)
	private String entranceDate;	  
   
	/**
	* 入场经办人
	*/
	@DataName(name = "入场经办人")
	@ApiModelProperty(value = "入场经办人", position = 60)
	private String entranceOptName;	  
   
	/**
	* 离场日期
	*/
	@DataName(name = "离场日期")
	@ApiModelProperty(value = "离场日期", position = 61)
	private String departureDate;	  
   
	/**
	* 离场经办人
	*/
	@DataName(name = "离场经办人")
	@ApiModelProperty(value = "离场经办人", position = 62)
	private String departureOptName;	  
   
	/**
	* 离场原因
	*/
	@DataName(name = "离场原因")
	@ApiModelProperty(value = "离场原因", position = 63)
	private String departureReason;	  
	
	/**
	* 用户ID, 不在数据库表中
	*/
	@DataName(name = "用户ID")
	@ApiModelProperty(value = "用户ID", position = 5)
	@TableField(exist = false)
	private String userId;	  
	
	/**
	* 状态, 不在数据库表中
	*/
	@DataName(name = "状态")
	@ApiModelProperty(value = "状态", position = 5)
	@TableField(exist = false)
	private String status;	  
 }
