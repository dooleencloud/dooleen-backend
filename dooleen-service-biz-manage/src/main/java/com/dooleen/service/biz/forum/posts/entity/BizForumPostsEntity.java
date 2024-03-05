package com.dooleen.service.biz.forum.posts.entity;

import java.math.BigDecimal;
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
 * @CreateDate : 2021-07-21 10:20:09
 * @Description : 贴子管理实体
 * @Author : name
 * @Update : 2021-07-21 10:20:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_forum_posts")
@ApiModel
@ToString(callSuper = true)
public class BizForumPostsEntity  extends BaseEntity implements Serializable  {
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
	* 贴子类型
	*/
	@Excel(name ="贴子类型")
	@DataName(name = "贴子类型", isRecordHistory = true)
	@ApiModelProperty(value = "贴子类型", position = 2)
	@Length(max=30,message="贴子类型长度不能大于30")
	@NotBlank(message = "贴子类型不能为空")
	private String postsType;



	/**
	 * 贴子分类
	 */
	@Excel(name ="贴子分类")
	@DataName(name = "贴子分类", isRecordHistory = true)
	@ApiModelProperty(value = "贴子分类", position = 6)
	@Length(max=30,message="贴子分类长度不能大于30")
	private String postsCategory;

	/**
	* 标题
	*/
	@Excel(name ="标题")
	@DataName(name = "标题", isRecordHistory = true)
	@ApiModelProperty(value = "标题", position = 3)
	@Length(max=200,message="标题长度不能大于200")
	@NotBlank(message = "标题不能为空")
	private String title;	  
   
	
	/**
	* 内容
	*/
	@Excel(name ="内容")
	@DataName(name = "内容", isRecordHistory = true)
	@ApiModelProperty(value = "内容", position = 4)
	@NotBlank(message = "内容不能为空")
	private String content;	  
   
	
	/**
	* 图片列表
	*/
	@Excel(name ="图片列表")
	@DataName(name = "图片列表", isRecordHistory = true)
	@ApiModelProperty(value = "图片列表", position = 5)
	@Length(max=2000,message="图片列表长度不能大于2000")
	private String imgList;	  
   
	
	/**
	* 发布人
	*/
	@Excel(name ="发布人")
	@DataName(name = "发布人", isRecordHistory = true)
	@ApiModelProperty(value = "发布人", position = 6)
	@Length(max=50,message="发布人长度不能大于50")
	@NotBlank(message = "发布人不能为空")
	private String deployUserName;	  
   
	
	/**
	* 发布人名
	*/
	@Excel(name ="发布人名")
	@DataName(name = "发布人名", isRecordHistory = true)
	@ApiModelProperty(value = "发布人名", position = 7)
	@Length(max=50,message="发布人名长度不能大于50")
	private String deployRealName;	  
   
	
	/**
	* 发布人头像地址
	*/
	@Excel(name ="发布人头像地址")
	@DataName(name = "发布人头像地址", isRecordHistory = true)
	@ApiModelProperty(value = "发布人头像地址", position = 8)
	@Length(max=100,message="发布人头像地址长度不能大于100")
	private String deployUserHeadImgAddress;	  
   
	
	/**
	* 发布人所属机构
	*/
	@Excel(name ="发布人所属机构")
	@DataName(name = "发布人所属机构", isRecordHistory = true)
	@ApiModelProperty(value = "发布人所属机构", position = 9)
	@Length(max=10,message="发布人所属机构长度不能大于10")
	private String deployUserBelongOrg;	  
   
	
	/**
	* 发布时间
	*/
	@Excel(name ="发布时间")
	@DataName(name = "发布时间", isRecordHistory = true)
	@ApiModelProperty(value = "发布时间", position = 10)
	@Length(max=100,message="发布时间长度不能大于100")
	private String deployDatetime;	  
   
	
	/**
	* 提醒人列表
	*/
	@Excel(name ="提醒人列表")
	@DataName(name = "提醒人列表", isRecordHistory = true)
	@ApiModelProperty(value = "提醒人列表", position = 11)
	@Length(max=2000,message="提醒人列表长度不能大于2000")
	private String reminderList;	  
   
	
	/**
	* 引用话题列表
	*/
	@Excel(name ="引用话题列表")
	@DataName(name = "引用话题列表", isRecordHistory = true)
	@ApiModelProperty(value = "引用话题列表", position = 12)
	@Length(max=2000,message="引用话题列表长度不能大于2000")
	private String quoteTopicList;	  
   
	
	/**
	* 评论次数
	*/
	@Excel(name ="评论次数")
	@DataName(name = "评论次数", isRecordHistory = true)
	@ApiModelProperty(value = "评论次数", position = 13)
	private int commentTimes;	  
   
	
	/**
	* 点赞次数
	*/
	@Excel(name ="点赞次数")
	@DataName(name = "点赞次数", isRecordHistory = true)
	@ApiModelProperty(value = "点赞次数", position = 14)
	private int admireTimes;	  
   
	
	/**
	* 转发次数
	*/
	@Excel(name ="转发次数")
	@DataName(name = "转发次数", isRecordHistory = true)
	@ApiModelProperty(value = "转发次数", position = 15)
	private int forwardTimes;	  
   
	
	/**
	* 查看次数
	*/
	@Excel(name ="查看次数")
	@DataName(name = "查看次数", isRecordHistory = true)
	@ApiModelProperty(value = "查看次数", position = 16)
	private int viewTimes;	  
   
	
	/**
	* 回复分值
	*/
	@Excel(name ="回复分值")
	@DataName(name = "回复分值", isRecordHistory = true)
	@ApiModelProperty(value = "回复分值", position = 17)
	private BigDecimal replyScore;	  
   
	
	/**
	* 点赞分值
	*/
	@Excel(name ="点赞分值")
	@DataName(name = "点赞分值", isRecordHistory = true)
	@ApiModelProperty(value = "点赞分值", position = 18)
	private BigDecimal admireScore;

	/**
	 * 状态
	 */
	@Excel(name ="状态")
	@DataName(name = "状态", isRecordHistory = true)
	@ApiModelProperty(value = "状态", position = 6)
	private String status;

	/**
	 * 关注状态,临时字段 不存数据库表
	 */
	@Excel(name ="关注状态")
	@DataName(name = "关注状态", isRecordHistory = true)
	@ApiModelProperty(value = "关注状态", position = 6)
	@TableField(exist = false)
	private String followStatus;

	/**
	 * 点赞状态,临时字段 不存数据库表
	 */
	@Excel(name ="点赞状态")
	@DataName(name = "点赞状态", isRecordHistory = true)
	@ApiModelProperty(value = "点赞状态", position = 6)
	@TableField(exist = false)
	private String admireStatus;

	/**
	 * 收藏状态,临时字段 不存数据库表
	 */
	@Excel(name ="收藏状态")
	@DataName(name = "收藏状态", isRecordHistory = true)
	@ApiModelProperty(value = "收藏状态", position = 6)
	@TableField(exist = false)
	private String collectionStatus;

	@DataName(name = "圈子ID", isRecordHistory = true)
	@ApiModelProperty(value = "圈子ID", position = 6)
	@TableField(exist = false)
	private String beFollowUserName;

	//临时变量
	@DataName(name = "点赞加减标识", isRecordHistory = true)
	@ApiModelProperty(value = "点赞加减标识", position = 6)
	@TableField(exist = false)
	private String addAdmireFlag;

	//临时变量
	@DataName(name = "评论加减标识", isRecordHistory = true)
	@ApiModelProperty(value = "评论加减标识", position = 6)
	@TableField(exist = false)
	private String addCommentTimesFlag;
 }
