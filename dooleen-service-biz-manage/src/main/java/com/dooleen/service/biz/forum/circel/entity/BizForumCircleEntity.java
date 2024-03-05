package com.dooleen.service.biz.forum.circel.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.dooleen.service.biz.forum.posts.entity.BizForumPostsEntity;
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
 * @CreateDate : 2021-07-21 10:19:44
 * @Description : 圈子管理实体
 * @Author : name
 * @Update : 2021-07-21 10:19:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_forum_circle")
@ApiModel
@ToString(callSuper = true)
public class BizForumCircleEntity  extends BaseEntity implements Serializable  {
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
	* 圈子名称
	*/
	@Excel(name ="圈子名称")
	@DataName(name = "圈子名称", isRecordHistory = true)
	@ApiModelProperty(value = "圈子名称", position = 2)
	@Length(max=50,message="圈子名称长度不能大于50")
	@NotBlank(message = "圈子名称不能为空")
	private String circleName;

	/**
	 * 圈子类型
	 */
	@Excel(name ="圈子类型")
	@DataName(name = "圈子类型", isRecordHistory = true)
	@ApiModelProperty(value = "圈子类型", position = 2)
	@Length(max=50,message="圈子类型长度不能大于50")
	@NotBlank(message = "圈子类型不能为空")
	private String circleType;

	/**
	 * 图片地址
	 */
	@Excel(name ="图片地址")
	@DataName(name = "图片地址", isRecordHistory = true)
	@ApiModelProperty(value = "图片地址", position = 2)
	private String imgAddress;


	/**
	* 圈子描述
	*/
	@Excel(name ="圈子描述")
	@DataName(name = "圈子描述", isRecordHistory = true)
	@ApiModelProperty(value = "圈子描述", position = 3)
	@Length(max=2000,message="圈子描述长度不能大于2000")
	@NotBlank(message = "圈子描述不能为空")
	private String circleDesc;

	/**
	 * 审批标志
	 */
	@Excel(name ="审批标志")
	@DataName(name = "审批标志", isRecordHistory = true)
	@ApiModelProperty(value = "审批标志", position = 2)
	private String approveFlag;

	/**
	* 圈子成员数量
	*/
	@Excel(name ="圈子成员数量")
	@DataName(name = "圈子成员数量", isRecordHistory = true)
	@ApiModelProperty(value = "圈子成员数量", position = 4)
    @DecimalMax(value="9999999999",message="圈子成员数量不能大于9999999999")
	@DecimalMin(value="0",message="圈子成员数量不能小于0")
	private int circleMemberCount;

	/**
	 * 关注状态,临时字段 不存数据库表 审批
	 */
	@Excel(name ="关注状态")
	@DataName(name = "关注状态", isRecordHistory = true)
	@ApiModelProperty(value = "关注状态", position = 6)
	@TableField(exist = false)
	private String followStatus;

	/**
	 * 是否查询动态标识
	 */
	@Excel(name ="动态标识")
	@DataName(name = "动态标识", isRecordHistory = true)
	@ApiModelProperty(value = "动态标识", position = 6)
	@TableField(exist = false)
	private String queryPosts;

	@DataName(name = "动态列表", isRecordHistory = true)
	@ApiModelProperty(value = "动态列表", position = 6)
	@TableField(exist = false)
	private List<BizForumPostsEntity> bizForumPostsEntityList = new ArrayList<>();

	@DataName(name = "成员加减标识", isRecordHistory = true)
	@ApiModelProperty(value = "成员加减标识", position = 6)
	@TableField(exist = false)
	private String addMemberFlag;
}
