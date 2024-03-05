package com.dooleen.common.core.app.send.log.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Learning平台
 * @Project No : Learning
 * @Version : 1.0.0
 * @CreateDate : 2020-11-07 22:12:08
 * @Description : 敏感词管理实体
 * @Author : name
 * @Update : 2020-11-07 22:12:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_send_log")
@ApiModel
@ToString(callSuper = true)
public class SysSendLogEntity  extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@DataName(name = "主键")
	@ApiModelProperty(value = "id" , position = 0)
	@TableId(type = IdType.INPUT)
	public String id;

	/**
	 * 租户ID
	 */
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", position = 6)
	@Length(max=10,message="租户ID长度不能大于10")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;

	/**
	 * 发送人ID
	 */
	@Excel(name ="发送人ID")
	@DataName(name = "发送人ID", isRecordHistory = true)
	@ApiModelProperty(value = "发送人ID", position = 6)
	@Length(max=20,message="发送人ID长度不能大于20")
	private String senderId;



	/**
	 * 发送人姓名
	 */
	@Excel(name ="发送人姓名")
	@DataName(name = "发送人姓名", isRecordHistory = true)
	@ApiModelProperty(value = "发送人姓名", position = 6)
	@Length(max=50,message="发送人姓名长度不能大于50")
	private String senderRealName;

	/**
	 * 发送人姓名
	 */
	@DataName(name = "头像地址", isRecordHistory = true)
	@ApiModelProperty(value = "头像地址", position = 6)
	@TableField(exist = false)
	private String headImgUrl;

	/**
	 * 业务场景名称,比输入，匹配该场景下需要发送哪些消息。
	 */
	@Excel(name ="业务场景名称")
	@DataName(name = "业务场景名称", isRecordHistory = true)
	@ApiModelProperty(value = "业务场景名称", position = 6)
	@Length(max=50,message="业务场景名称长度不能大于50")
	private String bizSceneName;
	
	/**
	 * 消息类型 在@标签里展示 还是在我的标签里展示
	 */
	@Excel(name ="消息类型")
	@DataName(name = "消息类型", isRecordHistory = true)
	@ApiModelProperty(value = "消息类型", position = 6)
	@Length(max=30,message="消息类型长度不能大于30")
	private String msgType;

	/**
	 * 消息提醒类型,用户前端判断是卡片显示 还是聊天显示。 0-聊天，1-卡片，2-短文，3-长文
	 */
	@DataName(name = "消息提醒类型", isRecordHistory = true)
	@TableField(exist = false)
	private String msgNoticeType = "1";

	/**
	 * 发送渠道
	 */
	@Excel(name ="发送渠道")
	@DataName(name = "发送渠道", isRecordHistory = true)
	@ApiModelProperty(value = "发送渠道", position = 6)
	@Length(max=50,message="发送渠道长度不能大于50")
	private String sendChannel;

	/**
	 * 发送地址
	 */
	@Excel(name ="发送地址")
	@DataName(name = "发送地址", isRecordHistory = true)
	@ApiModelProperty(value = "发送地址", position = 6)
	private String sendAddress;

	/**
	 * 发送地址(不存数据库)
	 */
	@DataName(name = "发送地址", isRecordHistory = true)
	@ApiModelProperty(value = "发送地址", position = 6)
	@TableField(exist = false)
	private String sendAddressList;

	/**
	 * 模板ID
	 */
	@Excel(name ="模板ID")
	@DataName(name = "模板ID", isRecordHistory = true)
	@ApiModelProperty(value = "模板ID", position = 6)
	@Length(max=20,message="模板ID长度不能大于20")
	private String templateId;



	/**
	 * 模板名称
	 */
	@Excel(name ="模板名称")
	@DataName(name = "模板名称", isRecordHistory = true)
	@ApiModelProperty(value = "模板名称", position = 6)
	@Length(max=50,message="模板名称长度不能大于50")
	private String templateName;
	/**
	 * 短信参数
	 */
	@Excel(name ="短信参数")
	@DataName(name = "短信参数", isRecordHistory = true)
	@ApiModelProperty(value = "短信参数", position = 6)
	@Length(max=2000,message="短信参数长度不能大于2000")
	private String smsParam;

	/**
	 * 资源ID
	 */
	@Excel(name ="资源ID")
	@DataName(name = "资源ID", isRecordHistory = true)
	@ApiModelProperty(value = "资源ID", position = 6)
	@Length(max=20,message="资源ID长度不能大于20")
	private String resourceId;

	/**
	 * 验证码
	 */
	@Excel(name ="验证码")
	@DataName(name = "验证码", isRecordHistory = true)
	@ApiModelProperty(value = "验证码", position = 6)
	@Length(max=20,message="验证码长度不能大于20")
	private String verifyCode;

	/**
	 * 短信签名
	 */
	@Excel(name ="短信签名")
	@DataName(name = "短信签名", isRecordHistory = true)
	@ApiModelProperty(value = "短信签名", position = 6)
	@Length(max=128,message="短信签名长度不能大于128")
	private String smsSign;

	/**
	 * 消息标题
	 */
	@Excel(name ="消息标题")
	@DataName(name = "消息标题", isRecordHistory = true)
	@ApiModelProperty(value = "消息标题", position = 6)
	@Length(max=200,message="消息标题长度不能大于200")
	private String msgTitle;

	/**
	 * 消息内容
	 */
	@Excel(name ="消息内容")
	@DataName(name = "消息内容", isRecordHistory = true)
	@ApiModelProperty(value = "消息内容", position = 6)
	private String msgContent;

	/**
	 * 消息内容JSON
	 * 目前公众号通知，钉钉消息必须写此格式，格式参照消息模板
	 */
	@Excel(name ="消息内容JSON")
	@DataName(name = "消息内容JSON", isRecordHistory = true)
	@ApiModelProperty(value = "消息内容JSON", position = 6)
	private String msgContentJson;

	/**
	 * 发送状态
	 */
	@Excel(name ="发送状态")
	@DataName(name = "发送状态", isRecordHistory = true)
	@ApiModelProperty(value = "发送状态", position = 6)
	@Length(max=30,message="发送状态长度不能大于30")
	private String sendStatus;

	/**
	 * 状态信息
	 */
	@Excel(name ="状态信息")
	@DataName(name = "状态信息", isRecordHistory = true)
	@ApiModelProperty(value = "状态信息", position = 6)
	@Length(max=2000,message="状态信息长度不能大于2000")
	private String statusInfo;
 }
