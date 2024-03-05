package com.dooleen.service.biz.forum.comment.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2021-07-21 10:17:42
 * @Description : 评论管理实体
 * @Author : name
 * @Update : 2021-07-21 10:17:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_forum_comment")
@ApiModel
@ToString(callSuper = true)
public class BizForumCommentEntity  extends BaseEntity implements Serializable  {
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
	* 贴子ID
	*/
	@Excel(name ="贴子ID")
	@DataName(name = "贴子ID", isRecordHistory = true)
	@ApiModelProperty(value = "贴子ID", position = 2)
	@Length(max=20,message="贴子ID长度不能大于20")
	@NotBlank(message = "贴子ID不能为空")
	private String postsId;	  
   
	
	/**
	* 评论人
	*/
	@Excel(name ="评论人")
	@DataName(name = "评论人", isRecordHistory = true)
	@ApiModelProperty(value = "评论人", position = 3)
	@Length(max=50,message="评论人长度不能大于50")
	@NotBlank(message = "评论人不能为空")
	private String commentUserName;	  
   
	
	/**
	* 评论人名
	*/
	@Excel(name ="评论人名")
	@DataName(name = "评论人名", isRecordHistory = true)
	@ApiModelProperty(value = "评论人名", position = 4)
	@Length(max=50,message="评论人名长度不能大于50")
	@NotBlank(message = "评论人名不能为空")
	private String commentRealName;	  
   
	
	/**
	* 评论头像地址
	*/
	@Excel(name ="评论头像地址")
	@DataName(name = "评论头像地址", isRecordHistory = true)
	@ApiModelProperty(value = "评论头像地址", position = 5)
	@Length(max=100,message="评论头像地址长度不能大于100")
	private String commentHeadImgAddress;	  
   
	
	/**
	* 评论类型
	*/
	@Excel(name ="评论类型")
	@DataName(name = "评论类型", isRecordHistory = true)
	@ApiModelProperty(value = "评论类型", position = 6)
	@Length(max=30,message="评论类型长度不能大于30")
	private String commentType;	  
   
	
	/**
	* 评论内容
	*/
	@Excel(name ="评论内容")
	@DataName(name = "评论内容", isRecordHistory = true)
	@ApiModelProperty(value = "评论内容", position = 7)
	@Length(max=100,message="评论内容长度不能大于100")
	@NotBlank(message = "评论内容不能为空")
	private String commentContent;	  
   
	
	/**
	* 评论图片地址
	*/
	@Excel(name ="评论图片地址")
	@DataName(name = "评论图片地址", isRecordHistory = true)
	@ApiModelProperty(value = "评论图片地址", position = 8)
	private String commentImgAddress;
   
	
	/**
	* 回复评论ID
	*/
	@Excel(name ="回复评论ID")
	@DataName(name = "回复评论ID", isRecordHistory = true)
	@ApiModelProperty(value = "回复评论ID", position = 9)
	@Length(max=20,message="回复评论ID长度不能大于20")
	private String replyCommentId;	  
   
	
	/**
	* 回复人
	*/
	@Excel(name ="回复人")
	@DataName(name = "回复人", isRecordHistory = true)
	@ApiModelProperty(value = "回复人", position = 10)
	@Length(max=50,message="回复人长度不能大于50")
	private String replyUserName;	  
   
	
	/**
	* 回复人名
	*/
	@Excel(name ="回复人名")
	@DataName(name = "回复人名", isRecordHistory = true)
	@ApiModelProperty(value = "回复人名", position = 11)
	@Length(max=50,message="回复人名长度不能大于50")
	private String replyRealName;	  
   
	
	/**
	* 评论日期
	*/
	@Excel(name ="评论日期")
	@DataName(name = "评论日期", isRecordHistory = true)
	@ApiModelProperty(value = "评论日期", position = 12)
	@Length(max=20,message="评论日期长度不能大于20")
	private String commentDate;	  
   
	
	/**
	* 点赞次数
	*/
	@Excel(name ="点赞次数")
	@DataName(name = "点赞次数", isRecordHistory = true)
	@ApiModelProperty(value = "点赞次数", position = 13)
    @DecimalMax(value="9999999999",message="点赞次数不能大于9999999999")
	@DecimalMin(value="0",message="点赞次数不能小于0")
	private int admireTimes;	  
   
	
	/**
	* 回复次数
	*/
	@Excel(name ="回复次数")
	@DataName(name = "回复次数", isRecordHistory = true)
	@ApiModelProperty(value = "回复次数", position = 14)
    @DecimalMax(value="9999999999",message="回复次数不能大于9999999999")
	@DecimalMin(value="0",message="回复次数不能小于0")
	private int replyTimes;	  
 }
