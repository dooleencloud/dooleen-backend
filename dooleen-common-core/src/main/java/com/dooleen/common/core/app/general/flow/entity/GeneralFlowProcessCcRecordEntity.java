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
 * @CreateDate : 2020-07-30 09:54:05
 * @Description : 流程处理抄送记录表实体
 * @Author : name
 * @Update : 2020-07-30 09:54:05
 */
@Data
@TableName("general_flow_process_cc_record")
@ApiModel
@ToString(callSuper = true)
public class GeneralFlowProcessCcRecordEntity extends BaseEntity implements Serializable  {

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
	* 处理ID
	*/
	@DataName(name = "处理ID")
	@ApiModelProperty(value = "处理ID", position = 6)
	@Length(max=20,message="处理ID长度不能大于20")
	private String processId;

	/**
	* 业务编码
	*/
	@DataName(name = "业务编码")
	@ApiModelProperty(value = "业务编码", position = 6)
	@Length(max=30,message="业务编码长度不能大于30")
	private String bizCode;
	
	/**
	* 业务ID
	*/
	@DataName(name = "业务ID")
	@ApiModelProperty(value = "业务ID", position = 3)
	@Length(max=20,message="业务ID长度不能大于20")
	@NotBlank(message = "业务ID不能为空")
	private String bizId;	  
   


	/**
	* 业务类型
	*/
	@DataName(name = "业务类型")
	@ApiModelProperty(value = "业务类型", position = 6)
	@Length(max=10,message="业务类型长度不能大于10")
	private String bizType;
	
	/**
	* 业务名称
	*/
	@DataName(name = "业务名称")
	@ApiModelProperty(value = "业务名称", position = 4)
	@Length(max=50,message="业务名称长度不能大于50")
	@NotBlank(message = "业务名称不能为空")
	private String bizName;	  
   
	
	/**
	* 节点名称
	*/
	@DataName(name = "节点名称")
	@ApiModelProperty(value = "节点名称", position = 5)
	@Length(max=50,message="节点名称长度不能大于50")
	private String nodeName;	  
   
	
	/**
	* 处理真实姓名
	*/
	@DataName(name = "处理真实姓名")
	@ApiModelProperty(value = "处理真实姓名", position = 6)
	@Length(max=50,message="处理真实姓名长度不能大于50")
	private String processRealName;	  
   
	
	/**
	* 处理用户名
	*/
	@DataName(name = "处理用户名")
	@ApiModelProperty(value = "处理用户名", position = 7)
	@Length(max=50,message="处理用户名长度不能大于50")
	private String processUserName;

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
	@ApiModelProperty(value = "处理进度状态", position = 8)
	@Length(max=30,message="处理进度状态长度不能大于30")
	private String processProgressStatus;	  
   


	/**
	* 处理开始时间
	*/
	@DataName(name = "处理开始时间")
	@ApiModelProperty(value = "处理开始时间", position = 6)
	@Length(max=100,message="处理开始时间长度不能大于100")
	private String processBeginDatetime;
	
	/**
	* 处理完成时间
	*/
	@DataName(name = "处理完成时间")
	@ApiModelProperty(value = "处理完成时间", position = 9)
	@Length(max=100,message="处理完成时间长度不能大于100")
	private String processFinishDatetime;	  
   
	
	/**
	* 节点等待时长
	*/
	@DataName(name = "节点等待时长")
	@ApiModelProperty(value = "节点等待时长", position = 10)
	@Length(max=30,message="节点等待时长长度不能大于30")
	private String nodeWaitDuration;	
	


	/**
	* 表单区
	*/
	@DataName(name = "表单区")
	@ApiModelProperty(value = "表单区", position = 6)
	@Length(max=100,message="表单区长度不能大于100")
	private String formArea;

	/**
	* 数据区
	*/
	@DataName(name = "数据区")
	@ApiModelProperty(value = "数据区", position = 6)
	@Length(max=100,message="数据区长度不能大于100")
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
