package com.dooleen.common.core.common.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value="空值体-占位")
public class NullEntity  implements Serializable{ 
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "空值-占位", position = 1)
	public String id; 
}
