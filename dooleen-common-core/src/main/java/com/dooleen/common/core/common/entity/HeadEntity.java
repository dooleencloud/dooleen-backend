package com.dooleen.common.core.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : Controller类
 * @Maintainer:liqiuhong
 * @Update:
 */
@Data
@ApiModel(value="公共报文头(输入、输出通用)")
public class HeadEntity  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "租户号", position = 1)
	private String tenantId;//租户号
	
	@ApiModelProperty(value = "租户名称", position = 2)
	private String tenantName;//租户名称
	
	@ApiModelProperty(value = "项目Id", position = 3)
	private String projectId;//项目编号
	
	@ApiModelProperty(value = "项目编号", position = 4)
	private String projectNo;//项目编号

	@ApiModelProperty(value = "项目名称", position = 5)
	private String projectName;//项目名称
	
	@ApiModelProperty(value = "用户ID", position = 6)
	private String userId;//用户ID
	
	@ApiModelProperty(value = "真实姓名", position = 7)
	private String realName;//真实姓名

	@ApiModelProperty(value = "用户姓名", position = 8)
	private String userName;//用户姓名

	@ApiModelProperty(value = "头像地址", position = 9)
	private String headImgUrl;//头像地址

	@ApiModelProperty(value = "微信openId ", position = 10)
	private String openId;//微信openId 
	
	@ApiModelProperty(value = "用户访问IP", position = 11)
	private String ipAddr;//用户访问IP
	
	@ApiModelProperty(value = "交易流水号", position = 12)
	private String transSeqNo;//交易流水号
	
	@ApiModelProperty(value = "微服务链路信息", position = 13)
	private Map<String, String> chainPath;//微服务链路信息
	
	@ApiModelProperty(value = "渠道号", position = 14)
	private String chainnelId;//渠道号
	
	@ApiModelProperty(value = "交易码", position = 15)
	private String transCode;//交易码
	
	@ApiModelProperty(value = "交易时间", position = 16)
	private String transTime ;//交易时间
	
	@ApiModelProperty(value = "用户ID", position = 17)
	private String respTime ;//交易时间
	
	@ApiModelProperty(value = "返回码", position = 18)
	private String respCode;//错误码(输出的时候必传)
	
	@ApiModelProperty(value = "错误信息", position = 19)
	private Map<String, String> respMsg =  new HashMap<String, String>();//错误信息(输出的时候必传)
	
	@ApiModelProperty(value = "当前访问token", position = 20)
	private String token;//当前访问Token 
	
	@ApiModelProperty(value = "当前访问刷新token", position = 21)
	private String refreshToken;//当前访问刷新的token
	
	@ApiModelProperty(value = "授权码", position = 22)
	private String authCode;//授权码

	@ApiModelProperty(value = "缓存标志-true，false", position = 23)
	private String cacheAble = "true";//缓存标志

	@ApiModelProperty(value = "MD5签名", position = 24)
	private String sign;//MD5签名
}
