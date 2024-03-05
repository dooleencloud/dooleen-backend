package com.dooleen.common.core.app.general.flow.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2020-07-04 04:16:29
 * @Description : 流程处理记录管理实体
 * @Author : name
 * @Update : 2020-07-04 04:16:29
 */
@Data
@TableName("general_flow_process_record")
@ApiModel
@ToString(callSuper = true)
public class GeneralFlowProcessRecordEntity<T> extends BaseEntity implements Serializable  {

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
	* 流程ID
	*/
	@DataName(name = "流程ID")
	@ApiModelProperty(value = "流程ID", position = 2)
	@Length(max=20,message="流程ID长度不能大于20")
	@NotBlank(message = "流程ID不能为空")
	private String flowId;	  
   
	
	/**
	* 流程名称
	*/
	@DataName(name = "流程名称")
	@ApiModelProperty(value = "流程名称", position = 3)
	@Length(max=50,message="流程名称长度不能大于50")
	@NotBlank(message = "流程名称不能为空")
	private String flowName;	  
   
	
	/**
	* 节点类型
	*/
	@DataName(name = "节点类型")
	@ApiModelProperty(value = "节点类型", position = 4)
	@Length(max=10,message="节点类型长度不能大于10")
	@NotBlank(message = "节点类型不能为空")
	private String nodeType;	  
   
	
	/**
	* 前节点ID列表
	*/
	@DataName(name = "前节点ID列表")
	@ApiModelProperty(value = "前节点ID列表", position = 5)
	@Length(max=20,message="前节点ID列表长度不能大于20")
	@NotBlank(message = "前节点ID列表不能为空")
	private String prevNodeIdList;	  
   
	
	/**
	* 节点ID
	*/
	@DataName(name = "节点ID")
	@ApiModelProperty(value = "节点ID", position = 6)
	@Length(max=20,message="节点ID长度不能大于20")
	@NotBlank(message = "节点ID不能为空")
	private String nodeId;	  
   
	
	/**
	* 节点名称
	*/
	@DataName(name = "节点名称")
	@ApiModelProperty(value = "节点名称", position = 7)
	@Length(max=50,message="节点名称长度不能大于50")
	@NotBlank(message = "节点名称不能为空")
	private String nodeName;



	/**
	 * 节点排序序号
	 */
	@DataName(name = "节点排序序号", isRecordHistory = true)
	@ApiModelProperty(value = "节点排序序号", required= true , position = 6)
	@Length(max=10,message="节点排序序号长度不能大于10")
	private int nodeOrderSeq;
	
	/**
	* 业务ID
	*/
	@DataName(name = "业务ID")
	@ApiModelProperty(value = "业务ID", position = 8)
	@Length(max=20,message="业务ID长度不能大于20")
	@NotBlank(message = "业务ID不能为空")
	private String bizId;	  
   
	
	/**
	* 业务类型
	*/
	@DataName(name = "业务类型")
	@ApiModelProperty(value = "业务类型", position = 9)
	@Length(max=10,message="业务类型长度不能大于10")
	@NotBlank(message = "业务类型不能为空")
	private String bizType;	  
   
	
	/**
	* 业务名称
	*/
	@DataName(name = "业务名称")
	@ApiModelProperty(value = "业务名称", position = 10)
	@Length(max=50,message="业务名称长度不能大于50")
	@NotBlank(message = "业务名称不能为空")
	private String bizName;	  


	/**
	* 业务编码
	*/
	@DataName(name = "业务编码")
	@ApiModelProperty(value = "业务编码", position =11)
	@Length(max=30,message="业务编码长度不能大于30")
	private String bizCode;   
	
	/**
	* 表单类型
	*/
	@DataName(name = "表单类型")
	@ApiModelProperty(value = "表单类型", position = 11)
	@Length(max=10,message="表单类型长度不能大于10")
	private String formType;	  
   


	/**
	* 表单ID
	*/
	@DataName(name = "表单ID")
	@ApiModelProperty(value = "表单ID", position = 12)
	@Length(max=20,message="表单ID长度不能大于20")
	private String formId;
	
	/**
	* 节点状态
	*/
	@DataName(name = "节点状态")
	@ApiModelProperty(value = "节点状态", position = 12)
	@Length(max=30,message="节点状态长度不能大于30")
	private String nodeStatus;	  
   
	
	/**
	* 转派委托前ID
	*/
	@DataName(name = "转派委托前ID")
	@ApiModelProperty(value = "转派委托前ID", position = 13)
	@Length(max=20,message="转派委托前ID长度不能大于20")
	private String transferDelegatePrevId;	  
   
	
	/**
	* 节点阶段状态
	*/
	@DataName(name = "节点阶段状态")
	@ApiModelProperty(value = "节点阶段状态", position = 14)
	@Length(max=30,message="节点阶段状态长度不能大于30")
	private String nodeStageStatus;	  
   
	
	/**
	* 是否转派标志
	*/
	@DataName(name = "是否转派标志")
	@ApiModelProperty(value = "是否转派标志", position = 15)
	@Length(max=1,message="是否转派标志长度不能大于1")
	private String isTransferFlag;	  
   
	
	/**
	* 转派用户名
	*/
	@DataName(name = "转派用户名")
	@ApiModelProperty(value = "转派用户名", position = 16)
	@Length(max=50,message="转派用户名长度不能大于50")
	private String transferUserName;	  
   
	
	/**
	* 是否委托标志
	*/
	@DataName(name = "是否委托标志")
	@ApiModelProperty(value = "是否委托标志", position = 17)
	@Length(max=1,message="是否委托标志长度不能大于1")
	private String isDelegateFlag;	  
   
	
	/**
	* 委托用户名
	*/
	@DataName(name = "委托用户名")
	@ApiModelProperty(value = "委托用户名", position = 18)
	@Length(max=50,message="委托用户名长度不能大于50")
	private String delegateUserName;	  

	
	/**
	* 处理状态
	*/
	@DataName(name = "处理状态")
	@ApiModelProperty(value = "处理状态", position = 20)
	@Length(max=30,message="处理状态长度不能大于30")
	private String processStatus;	  
   
	
	/**
	* 处理结果
	*/
	@DataName(name = "处理结果")
	@ApiModelProperty(value = "处理结果", position = 21)
	@Length(max=50,message="处理结果长度不能大于50")
	private String processResult;	  
   
	
	/**
	* 处理部门编号
	*/
	@DataName(name = "处理部门编号")
	@ApiModelProperty(value = "处理部门编号", position = 22)
	@Length(max=30,message="处理部门编号长度不能大于30")
	private String processDeptNo;	  
   
	
	/**
	* 处理部门名称
	*/
	@DataName(name = "处理部门名称")
	@ApiModelProperty(value = "处理部门名称", position = 23)
	@Length(max=50,message="处理部门名称长度不能大于50")
	private String processDeptName;	  
   
	
	/**
	* 处理人用户名
	*/
	@DataName(name = "处理人用户名")
	@ApiModelProperty(value = "处理人用户名", position = 24)
	@Length(max=50,message="处理人用户名长度不能大于50")
	private String processUserName;	  
   
	
	/**
	* 处理人姓名
	*/
	@DataName(name = "处理人姓名")
	@ApiModelProperty(value = "处理人姓名", position = 25)
	@Length(max=50,message="处理人姓名长度不能大于50")
	private String processRealName;	  
   
	
	/**
	* 处理意见
	*/
	@DataName(name = "处理意见")
	@ApiModelProperty(value = "处理意见", position = 26)
	@Length(max=2000,message="处理意见长度不能大于2000")
	private String processOpinion;	  
   
	
	/**
	* 处理开始时间
	*/
	@DataName(name = "处理开始时间")
	@ApiModelProperty(value = "处理开始时间", position = 27)
	@Length(max=100,message="处理开始时间长度不能大于100")
	private String processBeginDatetime;	  
   
	
	/**
	* 处理完成时间
	*/
	@DataName(name = "处理完成时间")
	@ApiModelProperty(value = "处理完成时间", position = 28)
	@Length(max=100,message="处理完成时间长度不能大于100")
	private String processFinishDatetime;


	/**
	 * 流程结束标志
	 */
	@DataName(name = "流程结束标志", isRecordHistory = true)
	@ApiModelProperty(value = "流程结束标志", required= true , position = 6)
	@Length(max=1,message="流程结束标志长度不能大于1")
	private String flowEndFlag;

	/**
	* 前节点处理ID
	*/
	@DataName(name = "前节点处理ID")
	@ApiModelProperty(value = "前节点处理ID", position = 6)
	@Length(max=20,message="前节点处理ID长度不能大于20")
	@NotBlank(message = "前节点处理ID不能为空")
	private String prevNodeProcessId;
	
	/**
	* 前节点意见
	*/
	@DataName(name = "前节点意见")
	@ApiModelProperty(value = "前节点意见", position = 29)
	@Length(max=2000,message="前节点意见长度不能大于2000")
	@NotBlank(message = "前节点意见不能为空")
	private String prevNodeOpinion;

	/**
	* 前节点名称
	*/
	@DataName(name = "前节点名称")
	@ApiModelProperty(value = "前节点名称", position = 30)
	@Length(max=50,message="前节点名称长度不能大于50")
	@NotBlank(message = "前节点名称不能为空")
	private String prevNodeNameList;


	/**
	* 前节点用户名
	*/
	@DataName(name = "前节点用户名")
	@ApiModelProperty(value = "前节点用户名", position = 31)
	@Length(max=50,message="前节点用户名长度不能大于50")
	@NotBlank(message = "前节点用户名不能为空")
	private String prevNodeUserName;

	/**
	 * 处理阶段状态
	 */
	@DataName(name = "处理阶段状态", isRecordHistory = true)
	@ApiModelProperty(value = "处理阶段状态", required= true , position = 6)
	@Length(max=50,message="处理阶段状态长度不能大于50")
	private String processStageStatus;

	/**
	* 处理进度状态
	*/
	@DataName(name = "处理进度状态")
	@ApiModelProperty(value = "处理进度状态", position = 32)
	@Length(max=30,message="处理进度状态长度不能大于30")
	private String processProgressStatus;

	/**
	* 主流程ID
	*/
	@DataName(name = "主流程ID")
	@ApiModelProperty(value = "主流程ID", position = 33)
	@Length(max=20,message="主流程ID长度不能大于20")
	private String mainFlowId;

	/**
	* 主节点ID
	*/
	@DataName(name = "主节点ID")
	@ApiModelProperty(value = "主节点ID", position = 34)
	@Length(max=20,message="主节点ID长度不能大于20")
	private String mainNodeId;
	
	/**
	* 回退标志
	*/
	@DataName(name = "回退标志")
	@ApiModelProperty(value = "回退标志", position = 6)
	@Length(max=1,message="回退标志长度不能大于1")
	private String rollbackFlag;

	/**
	* 超时标志
	*/
	@DataName(name = "超时标志")
	@ApiModelProperty(value = "超时标志", position = 6)
	@Length(max=1,message="超时标志长度不能大于1")
	private String overtimeFlag;


	/**
	* 是否超时通知标志
	*/
	@DataName(name = "是否超时通知标志")
	@ApiModelProperty(value = "是否超时通知标志", position = 6)
	@Length(max=1,message="是否超时通知标志长度不能大于1")
	private String isOvertimeNoticeFlag;
	
	/**
	* 节点等待时长
	*/
	@DataName(name = "节点等待时长")
	@ApiModelProperty(value = "节点等待时长", position = 6)
	@Length(max=30,message="节点等待时长长度不能大于30")
	private String nodeWaitDuration;
	
	/**
	* 时间单位
	*/
	@DataName(name = "时间单位")
	@ApiModelProperty(value = "时间单位", position = 6)
	@Length(max=30,message="时间单位长度不能大于30")
	private String timeUnit;
	/**
	* 超时通知方式
	*/
	@DataName(name = "超时通知方式")
	@ApiModelProperty(value = "超时通知方式", position = 6)
	@Length(max=30,message="超时通知方式长度不能大于30")
	private String overtimeNoticeWay;

	/**
	* 超时通知列表
	*/
	@DataName(name = "超时通知列表")
	@ApiModelProperty(value = "超时通知列表", position = 6)
	@Length(max=2000,message="超时通知列表长度不能大于2000")
	private String overtimeNoticeList;
	
	/**
	* 表单区(动态表单使用)
	*/
	@DataName(name = "表单区")
	@ApiModelProperty(value = "表单区", position = 35)
	@Length(max=0,message="表单区长度不能大于0")
	private String formArea;
	
	/**
	* 数据区
	*/
	@DataName(name = "数据区")
	@ApiModelProperty(value = "数据区", position = 36)
	@Length(max=5000,message="数据区长度不能大于5000")
	private String dataArea;

	/**
	 * 业务数据
	 */
	@DataName(name = "业务数据", isRecordHistory = true)
	@ApiModelProperty(value = "业务数据", required= false , position = 6)
	@Length(max=10000,message="业务数据长度不能大于10000")
	private String bizData;

	/**
	 * 工作台数据
	 */
	@DataName(name = "工作台数据", isRecordHistory = true)
	@ApiModelProperty(value = "工作台数据", required= false , position = 6)
	@Length(max=10000,message="工作台数据长度不能大于10000")
	private String workbenchData;

	/**
	* 发起用户名
	*/
	@DataName(name = "发起用户名")
	@ApiModelProperty(value = "发起用户名", position = 6)
	@Length(max=50,message="发起用户名长度不能大于50")
	public String launchUserName;

	/**
	 * 发起用户姓名
	 */
	@DataName(name = "发起用户姓名")
	@ApiModelProperty(value = "发起用户姓名", position = 6)
	@Length(max=50,message="发起用户姓名长度不能大于50")
	public String launchRealName;

	/**
	 * 发起时间
	 */
	@DataName(name = "发起时间", isRecordHistory = true)
	@ApiModelProperty(value = "发起时间", required= true , position = 6)
	@Length(max=100,message="发起时间长度不能大于100")
	private String launchDatetime;

	/**
	* 抄送标志
	*/
	@DataName(name = "抄送标志")
	@ApiModelProperty(value = "抄送标志", position = 6)
	@Length(max=1,message="抄送标志长度不能大于1")
	private String ccFlag;

	/**
	* 抄送用户列表
	*/
	@DataName(name = "抄送用户列表")
	@ApiModelProperty(value = "抄送用户列表", position = 6)
	@Length(max=2000,message="抄送用户列表长度不能大于2000")
	private String ccUserList;

	/**
	* 附件列表
	*/
	@DataName(name = "附件列表")
	@ApiModelProperty(value = "附件列表", position = 6)
	@Length(max=2000,message="附件列表长度不能大于2000")
	private String attList;

	

	/**
	* 附件名称
	*/
	@DataName(name = "附件名称")
	@ApiModelProperty(value = "附件名称", position = 6)
	@Length(max=50,message="附件名称长度不能大于50")
	private String attName;

	/**
	 * 业务数据JSON
	 */
	@DataName(name = "业务数据")
	@TableField(exist = false)
	private T bizDataEntity;

	/**
	 * 所属机构
	 */
	@DataName(name = "所属机构")
	@TableField(exist = false)
	public String belongDeptName;

	/**
	 * 发起人头像
	 */
	@DataName(name = "发起人头像")
	@TableField(exist = false)
	public String headImgUrl;
 }
