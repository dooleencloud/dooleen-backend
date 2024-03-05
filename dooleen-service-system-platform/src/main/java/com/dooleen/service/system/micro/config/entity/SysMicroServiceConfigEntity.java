package com.dooleen.service.system.micro.config.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
 * @CreateDate : 2021-11-24 20:51:35
 * @Description : 服务配置管理实体
 * @Author : name
 * @Update : 2021-11-24 20:51:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_micro_service_config")
@ApiModel(value = "服务配置管理实体")
@ToString(callSuper = true)
public class SysMicroServiceConfigEntity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true, isRecordHistory = true)
    @ApiModelProperty(value = "id", required=true, position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", required=true, position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;



	/**
	 * 服务类型
	 */
	@Excel(name ="服务类型")
	@DataName(name = "服务类型", isRecordHistory = true)
	@ApiModelProperty(value = "服务类型", required= true , position = 6)
	@Length(max=10,message="服务类型长度不能大于10")
	private String serviceType;

	/**
	 * 所属应用ID
	 */
	@Excel(name ="所属应用ID")
	@DataName(name = "所属应用ID", isRecordHistory = true)
	@ApiModelProperty(value = "所属应用ID", required= true , position = 6)
	@Length(max=20,message="所属应用ID长度不能大于20")
	@NotBlank(message = "所属应用ID不能为空")
	private String belongAppId;
	
	/**
	* 服务编码
	*/
	@Excel(name ="服务编码")
	@DataName(name = "服务编码", isRecordHistory = true)
	@ApiModelProperty(value = "服务编码", required=true, position = 2)
	@Length(max=50,message="服务编码长度不能大于50")
	@NotBlank(message = "服务编码不能为空")
	private String serviceCode;	  
   
	
	/**
	* 所属应用名
	*/
	@Excel(name ="所属应用名")
	@DataName(name = "所属应用名", isRecordHistory = true)
	@ApiModelProperty(value = "所属应用名", required=true, position = 3)
	@Length(max=50,message="所属应用名长度不能大于50")
	private String belongAppName;
   
	
	/**
	* 服务名称
	*/
	@Excel(name ="服务名称")
	@DataName(name = "服务名称", isRecordHistory = true)
	@ApiModelProperty(value = "服务名称", required=true, position = 4)
	@Length(max=50,message="服务名称长度不能大于50")
	@NotBlank(message = "服务名称不能为空")
	private String serviceName;	  
   
	
	/**
	* 服务总线数据
	*/
	@Excel(name ="服务总线数据")
	@DataName(name = "服务总线数据", isRecordHistory = true)
	@ApiModelProperty(value = "服务总线数据", required=false,  position = 5)
	@Length(max=50000,message="服务总线数据长度不能大于50000")
	private String serviceBusData;	  
   
	
	/**
	* 写日志标识
	*/
	@Excel(name ="写日志标识")
	@DataName(name = "写日志标识", isRecordHistory = true)
	@ApiModelProperty(value = "写日志标识", required=true, position = 6)
	@Length(max=1,message="写日志标识长度不能大于1")
	@NotBlank(message = "写日志标识不能为空")
	private String writeLogFlag;	  
   
	
	/**
	* 分录拆分方式
	*/
	@Excel(name ="分录拆分方式")
	@DataName(name = "分录拆分方式", isRecordHistory = true)
	@ApiModelProperty(value = "分录拆分方式", required=false,  position = 7)
	@Length(max=30,message="分录拆分方式长度不能大于30")
	private String entrySplitWay;	  
   
	
	/**
	* 入账方式
	*/
	@Excel(name ="入账方式")
	@DataName(name = "入账方式", isRecordHistory = true)
	@ApiModelProperty(value = "入账方式", required=false,  position = 8)
	@Length(max=30,message="入账方式长度不能大于30")
	private String postingWay;	  
   
	
	/**
	* 转主机报文标识
	*/
	@Excel(name ="转主机报文标识")
	@DataName(name = "转主机报文标识", isRecordHistory = true)
	@ApiModelProperty(value = "转主机报文标识", required=false,  position = 9)
	@Length(max=1,message="转主机报文标识长度不能大于1")
	private String toMainframeMsgFlag;	  
   
	
	/**
	* 交易类型
	*/
	@Excel(name ="交易类型")
	@DataName(name = "交易类型", isRecordHistory = true)
	@ApiModelProperty(value = "交易类型", required=false,  position = 10)
	@Length(max=30,message="交易类型长度不能大于30")
	private String transType;	  
   
	
	/**
	* 输入报文体
	*/
	@Excel(name ="输入报文体")
	@DataName(name = "输入报文体", isRecordHistory = true)
	@ApiModelProperty(value = "输入报文体", required=false,  position = 11)
	@Length(max=5000,message="输入报文体长度不能大于5000")
	private String inputMsgBody;	  
   
	
	/**
	* 输出报文体
	*/
	@Excel(name ="输出报文体")
	@DataName(name = "输出报文体", isRecordHistory = true)
	@ApiModelProperty(value = "输出报文体", required=false,  position = 12)
	@Length(max=5000,message="输出报文体长度不能大于5000")
	private String outputMsgBody;	  
 }
