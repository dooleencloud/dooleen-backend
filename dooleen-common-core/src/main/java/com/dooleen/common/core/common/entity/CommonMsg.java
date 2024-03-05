package com.dooleen.common.core.common.entity;

import java.io.Serializable;

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
@ApiModel(value="公共报文(输入、输出通用)")
public class CommonMsg<S,T> implements Serializable { 
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "报文头", position = 1)
	private HeadEntity head;//报文头
	
	@ApiModelProperty(value = "报文主体", position = 2)
	private MutBean<S,T> body;//返回实体类List和其他参数
}
