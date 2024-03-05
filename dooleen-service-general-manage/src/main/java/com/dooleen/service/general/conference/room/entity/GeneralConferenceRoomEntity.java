package com.dooleen.service.general.conference.room.entity;

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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-25 16:35:56
 * @Description : 会议室管理实体
 * @Author : name
 * @Update : 2020-08-25 16:35:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("general_conference_room")
@ApiModel
@ToString(callSuper = true)
public class GeneralConferenceRoomEntity  extends BaseEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true)
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
	 * 办公区名称
	 */
	@DataName(name = "办公区名称", isRecordHistory = true)
	@ApiModelProperty(value = "办公区名称", position = 6)
	@Length(max=50,message="办公区名称长度不能大于50")
	@NotBlank(message = "办公区名称不能为空")
	private String officeAreaName;

	/**
	 * 楼层号
	 */
	@DataName(name = "楼层号", isRecordHistory = true)
	@ApiModelProperty(value = "楼层号", position = 6)
	@Length(max=30,message="楼层号长度不能大于30")
	@NotBlank(message = "楼层号不能为空")
	private String floorNo;

	/**
	 * 会议室名称
	 */
	@DataName(name = "会议室名称", isRecordHistory = true)
	@ApiModelProperty(value = "会议室名称", position = 6)
	@Length(max=50,message="会议室名称长度不能大于50")
	@NotBlank(message = "会议室名称不能为空")
	private String conferenceRoomName;

	/**
	 * 会议室容量
	 */
	@DataName(name = "会议室容量", isRecordHistory = true)
	@ApiModelProperty(value = "会议室容量", position = 6)
	private int conferenceRoomCapacity;


	/**
	 * 会议室管理员
	 */
	@DataName(name = "会议室管理员", isRecordHistory = true)
	@ApiModelProperty(value = "会议室管理员", position = 6)
	@Length(max=50,message="会议室管理员长度不能大于50")
	private String conferenceRoomManageUserName;

	/**
	 * 会议室管理员姓名
	 */
	@DataName(name = "会议室管理员姓名", isRecordHistory = true)
	@ApiModelProperty(value = "会议室管理员姓名", position = 6)
	@Length(max=50,message="会议室管理员姓名长度不能大于50")
	private String conferenceRoomManageRealName;

	/**
	 * 设备列表
	 */
	@DataName(name = "设备列表", isRecordHistory = true)
	@ApiModelProperty(value = "设备列表", position = 6)
	@Length(max=2000,message="设备列表长度不能大于2000")
	private String deviceList;

	/**
	 * 状态
	 */
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 6)
	@Length(max=30,message="状态长度不能大于30")
	@NotBlank(message = "状态不能为空")
	private String status;

	/**
	 * 备注
	 */
	@DataName(name = "备注", isRecordHistory = true)
	@ApiModelProperty(value = "备注", position = 6)
	@Length(max=5000,message="备注长度不能大于5000")
	private String remark;
 }
