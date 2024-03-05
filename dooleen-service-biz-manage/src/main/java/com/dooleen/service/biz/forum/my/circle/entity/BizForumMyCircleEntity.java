package com.dooleen.service.biz.forum.my.circle.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
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
 * @CreateDate : 2021-07-21 10:14:39
 * @Description : 我的圈子管理实体
 * @Author : name
 * @Update : 2021-07-21 10:14:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_forum_my_circle")
@ApiModel
@ToString(callSuper = true)
public class BizForumMyCircleEntity  extends BaseEntity implements Serializable  {
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
	* 用户名
	*/
	@Excel(name ="用户名")
	@DataName(name = "用户名", isRecordHistory = true)
	@ApiModelProperty(value = "用户名", position = 2)
	@Length(max=50,message="用户名长度不能大于50")
	@NotBlank(message = "用户名不能为空")
	private String userName;	  
   
	
	/**
	* 用户姓名
	*/
	@Excel(name ="用户姓名")
	@DataName(name = "用户姓名", isRecordHistory = true)
	@ApiModelProperty(value = "用户姓名", position = 3)
	@Length(max=50,message="用户姓名长度不能大于50")
	@NotBlank(message = "用户姓名不能为空")
	private String userRealName;	  
   
	
	/**
	* 用户头像地址
	*/
	@Excel(name ="用户头像地址")
	@DataName(name = "用户头像地址", isRecordHistory = true)
	@ApiModelProperty(value = "用户头像地址", position = 4)
	@Length(max=100,message="用户头像地址长度不能大于100")
	private String userHeadImgAddress;	  
   
	
	/**
	* 圈子ID
	*/
	@Excel(name ="圈子ID")
	@DataName(name = "圈子ID", isRecordHistory = true)
	@ApiModelProperty(value = "圈子ID", position = 5)
	@Length(max=20,message="圈子ID长度不能大于20")
	@NotBlank(message = "圈子ID不能为空")
	private String circleId;	  
   
	
	/**
	* 圈子名称
	*/
	@Excel(name ="圈子名称")
	@DataName(name = "圈子名称", isRecordHistory = true)
	@ApiModelProperty(value = "圈子名称", position = 6)
	@Length(max=50,message="圈子名称长度不能大于50")
	@NotBlank(message = "圈子名称不能为空")
	private String circleName;	  
   
	
	/**
	* 加入时间
	*/
	@Excel(name ="加入时间")
	@DataName(name = "加入时间", isRecordHistory = true)
	@ApiModelProperty(value = "加入时间", position = 7)
	@Length(max=100,message="加入时间长度不能大于100")
	private String joinDatetime;	  
 }
