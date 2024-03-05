package com.dooleen.service.biz.forum.follow.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @CreateDate : 2021-07-21 10:19:30
 * @Description : 用户关注管理实体
 * @Author : name
 * @Update : 2021-07-21 10:19:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_forum_follow")
@ApiModel
@ToString(callSuper = true)
public class BizForumFollowEntity  extends BaseEntity implements Serializable  {
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
	* 被关注用户名
	*/
	@Excel(name ="被关注用户名")
	@DataName(name = "被关注用户名", isRecordHistory = true)
	@ApiModelProperty(value = "被关注用户名", position = 5)
	@Length(max=50,message="被关注用户名长度不能大于50")
	@NotBlank(message = "被关注用户名不能为空")
	private String beFollowUserName;	  
   
	
	/**
	* 被关注用户姓名
	*/
	@Excel(name ="被关注用户姓名")
	@DataName(name = "被关注用户姓名", isRecordHistory = true)
	@ApiModelProperty(value = "被关注用户姓名", position = 6)
	@Length(max=50,message="被关注用户姓名长度不能大于50")
	@NotBlank(message = "被关注用户姓名不能为空")
	private String beFollowUserRealName;	  
   
	
	/**
	* 被关注用户头像地址
	*/
	@Excel(name ="被关注用户头像地址")
	@DataName(name = "被关注用户头像地址", isRecordHistory = true)
	@ApiModelProperty(value = "被关注用户头像地址", position = 7)
	@Length(max=100,message="被关注用户头像地址长度不能大于100")
	private String beFollowUserHeadImgAddress;	  
   
	
	/**
	* 关注时间
	*/
	@Excel(name ="关注时间")
	@DataName(name = "关注时间", isRecordHistory = true)
	@ApiModelProperty(value = "关注时间", position = 8)
	@Length(max=100,message="关注时间长度不能大于100")
	private String followDatetime;	  
   
	
	/**
	* 关注状态
	*/
	@Excel(name ="关注状态")
	@DataName(name = "关注状态", isRecordHistory = true)
	@ApiModelProperty(value = "关注状态", position = 9)
	@Length(max=30,message="关注状态长度不能大于30")
	private String followStatus;

	/**
	 * 关注数量
	 */
	@DataName(name = "关注数量", isRecordHistory = true)
	@ApiModelProperty(value = "关注数量", position = 9)
	@Length(max=30,message="关注数量长度不能大于30")
	@TableField(exist = false)
	private String followTotal;


	/**
	 * 粉丝数量
	 */
	@DataName(name = "粉丝数量", isRecordHistory = true)
	@ApiModelProperty(value = "粉丝数量", position = 9)
	@Length(max=30,message="粉丝数量长度不能大于30")
	@TableField(exist = false)
	private String funsTotal;
}
