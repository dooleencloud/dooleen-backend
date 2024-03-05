package com.dooleen.common.core.app.system.msg.notice.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2021-06-05 13:38:58
 * @Description : 消息通知管理实体
 * @Author : name
 * @Update : 2021-06-05 13:38:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_msg_notice")
@ApiModel
@ToString(callSuper = true)
public class SysMsgNoticeEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
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
	* 消息类型
	*/
	@Excel(name ="消息类型")
	@DataName(name = "消息类型", isRecordHistory = true)
	@ApiModelProperty(value = "消息类型", position = 2)
	@Length(max=30,message="消息类型长度不能大于30")
	@NotBlank(message = "消息类型不能为空")
	private String msgType;



	/**
	 * 业务场景名称
	 */
	@Excel(name ="业务场景名称")
	@DataName(name = "业务场景名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务场景名称", position = 6)
	@Length(max=50,message="业务场景名称长度不能大于50")
	private String bizSceneName;

	/**
	* 接收消息人
	*/
	@Excel(name ="接收消息人")
	@DataName(name = "接收消息人", isRecordHistory = true)
	@ApiModelProperty(value = "接收消息人", position = 3)
	@Length(max=50,message="接收消息人长度不能大于50")
	@NotBlank(message = "接收消息人不能为空")
	private String acceptMsgUserName;	  
   
	
	/**
	* 接收人名
	*/
	@Excel(name ="接收人名")
	@DataName(name = "接收人名", isRecordHistory = true)
	@ApiModelProperty(value = "接收人名", position = 4)
	@Length(max=50,message="接收人名长度不能大于50")
	private String acceptRealName;	  
   
	
	/**
	* 发送消息人
	*/
	@Excel(name ="发送消息人")
	@DataName(name = "发送消息人", isRecordHistory = true)
	@ApiModelProperty(value = "发送消息人", position = 5)
	@Length(max=50,message="发送消息人长度不能大于50")
	@NotBlank(message = "发送消息人不能为空")
	private String sendMsgUserName;	  
   
	
	/**
	* 发送人名
	*/
	@Excel(name ="发送人名")
	@DataName(name = "发送人名", isRecordHistory = true)
	@ApiModelProperty(value = "发送人名", position = 6)
	@Length(max=50,message="发送人名长度不能大于50")
	private String sendRealName;	  
   
	
	/**
	* 发送人头像链接
	*/
	@Excel(name ="发送人头像链接")
	@DataName(name = "发送人头像链接", isRecordHistory = true)
	@ApiModelProperty(value = "发送人头像链接", position = 7)
	@Length(max=100,message="发送人头像链接长度不能大于100")
	private String senderHeadImgUrl;	  
   
	
	/**
	* 消息标题
	*/
	@Excel(name ="消息标题")
	@DataName(name = "消息标题", isRecordHistory = true)
	@ApiModelProperty(value = "消息标题", position = 8)
	@Length(max=5000,message="消息标题长度不能大于200")
	@NotBlank(message = "消息标题不能为空")
	private String msgTitle;	  
   
	
	/**
	* 消息内容
	*/
	@Excel(name ="消息内容")
	@DataName(name = "消息内容", isRecordHistory = true)
	@ApiModelProperty(value = "消息内容", position = 9)
	@Length(max=5000,message="消息内容长度不能大于100")
	private String msgContent;

	/**
	 * 消息内容
	 */
	@Excel(name ="消息内容JSON")
	@DataName(name = "消息内容JSON", isRecordHistory = true)
	@ApiModelProperty(value = "消息内容JSON", position = 9)
	private String msgContentJson;

	/**
	* 消息资源ID
	*/
	@Excel(name ="消息资源ID")
	@DataName(name = "消息资源ID", isRecordHistory = true)
	@ApiModelProperty(value = "消息资源ID", position = 10)
	@Length(max=20,message="消息资源ID长度不能大于20")
	private String msgResourceId;	  
   
	
	/**
	* 会话标识
	*/
	@Excel(name ="会话标识")
	@DataName(name = "会话标识", isRecordHistory = true)
	@ApiModelProperty(value = "会话标识", position = 11)
	@Length(max=1,message="会话标识长度不能大于1")
	private String sessionFlag;	  
   
	
	/**
	* 读取状态
	*/
	@Excel(name ="读取状态")
	@DataName(name = "读取状态", isRecordHistory = true)
	@ApiModelProperty(value = "读取状态", position = 12)
	@Length(max=30,message="读取状态长度不能大于30")
	private String readStatus;

	/**
	 * 未读条数
	 */
	@DataName(name = "未读条数", isRecordHistory = true)
	@ApiModelProperty(value = "未读条数", position = 12)
	@TableField(exist = false)
	private int notReadCnt;

	/**
	 * 是否显示
	 */
	@DataName(name = "是否显示", isRecordHistory = true)
	@ApiModelProperty(value = "是否显示", position = 12)
	@TableField(exist = false)
	private boolean show = false;

	/**
	 * 是否置顶
	 */
	@DataName(name = "是否置顶", isRecordHistory = true)
	@ApiModelProperty(value = "是否置顶", position = 12)
	@TableField(exist = false)
	private boolean top;

	/**
	* 读取日期
	*/
	@Excel(name ="读取日期")
	@DataName(name = "读取日期", isRecordHistory = true)
	@ApiModelProperty(value = "读取日期", position = 13)
	@Length(max=20,message="读取日期长度不能大于20")
	private String readDate;	  
 }
