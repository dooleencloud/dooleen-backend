
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
 * @Description : 查询参数
 * @Maintainer:liqiuhong
 * @Update:
 */
@Data
@ApiModel(value="条件报文体")
public class SQLConditionEntity  implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "字段名", position = 1)
	private String column;//字段名
	@ApiModelProperty(value = "条件类型（like,eq,gt,le）", position = 2)
	private String type;//like、 eq、 gt、 le
	@ApiModelProperty(value = "值", position = 3)
	private String value ;//值
	 
}
