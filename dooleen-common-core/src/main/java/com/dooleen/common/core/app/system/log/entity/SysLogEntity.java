package com.dooleen.common.core.app.system.log.entity;

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
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-25 11:07:25
 * @Description : 系统日志管理实体
 * @Author : name
 * @Update : 2020-07-25 11:07:25
 */
@Data
@TableName("sys_log")
@ApiModel
@ToString(callSuper = true)
public class SysLogEntity extends BaseEntity implements Serializable  {

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
	* 日志类型
	*/
	@DataName(name = "日志类型")
	@ApiModelProperty(value = "日志类型", position = 2)
	@Length(max=30,message="日志类型长度不能大于30")
	private String logType;	  
   
	
	/**
	* 日志标题
	*/
	@DataName(name = "日志标题")
	@ApiModelProperty(value = "日志标题", position = 3)
	@Length(max=200,message="日志标题长度不能大于200")
	private String logTitle;	  
   
	
	/**
	* 服务名称
	*/
	@DataName(name = "服务名称")
	@ApiModelProperty(value = "服务名称", position = 4)
	@Length(max=50,message="服务名称长度不能大于50")
	private String serviceName;	  
   
	
	/**
	* 操作地址
	*/
	@DataName(name = "操作地址")
	@ApiModelProperty(value = "操作地址", position = 5)
	@Length(max=100,message="操作地址长度不能大于100")
	private String handleAddress;	  
   
	
	/**
	* 用户代理
	*/
	@DataName(name = "用户代理")
	@ApiModelProperty(value = "用户代理", position = 6)
	@Length(max=1000,message="用户代理长度不能大于1000")
	private String userAgent;	  
   
	
	/**
	* 请求地址
	*/
	@DataName(name = "请求地址")
	@ApiModelProperty(value = "请求地址", position = 7)
	@Length(max=100,message="请求地址长度不能大于100")
	private String requestAddress;	  
   
	
	/**
	* 请求方式
	*/
	@DataName(name = "请求方式")
	@ApiModelProperty(value = "请求方式", position = 8)
	@Length(max=30,message="请求方式长度不能大于30")
	private String requestWay;	  
   
	
	/**
	* 请求参数
	*/
	@DataName(name = "请求参数")
	@ApiModelProperty(value = "请求参数", position = 9)
	private String requestParam;


	/**
	 * 请求报文头
	 */
	@DataName(name = "请求报文头")
	@ApiModelProperty(value = "请求报文头", position = 10)
	private String requestHeader;

	/**
	* 请求报文体
	*/
	@DataName(name = "请求报文体")
	@ApiModelProperty(value = "请求报文体", position = 10)
	private String requestBody;

	/**
	 * 请求报文体
	 */
	@DataName(name = "返回报文体")
	@ApiModelProperty(value = "返回报文体", position = 10)
	private String responseBody;

	/**
	* 执行时长
	*/
	@DataName(name = "执行时长")
	@ApiModelProperty(value = "执行时长", position = 11)
	@Length(max=30,message="执行时长长度不能大于30")
	private String execDuration;

	/**
	 * 返回码
	 */
	@DataName(name = "返回码")
	@ApiModelProperty(value = "返回码", position = 12)
	private String responseCode;

	/**
	* 异常内容
	*/
	@DataName(name = "异常内容")
	@ApiModelProperty(value = "异常内容", position = 12)
	private String exceptionContent;	  
 }
