
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
 * @Description : 排序实体
 * @Maintainer:liqiuhong
 * @Update:
 */
@Data
@ApiModel(value="排序规则体")
public class SQLOrderEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//字段名
	@ApiModelProperty(value = "字段名", position = 1)
	private String prop;
	
	//值 ascending  descending
	@ApiModelProperty(value = "升降序", position = 2)
	private String order ;
	 
}
