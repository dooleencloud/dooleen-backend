
package com.dooleen.common.core.common.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : mongodb报文体
 * @Author : apple 
 * @Update:
 */ 
@Data
public class ExtFormEntity implements Serializable{

	public static final long serialVersionUID = 1L;
    /**
    * id
    */
    @DataName(name = "主键")
    @ApiModelProperty(value = "id" , position = 0)
	@NotBlank(message = "ID不能为空")
	public String id; 
	//业务编号
	@DataName(name = "业务编码")
	@ApiModelProperty(value = "业务编码", position = 0)
	public String bizCode;
	
//	//表单名称
//	@DataName(name = "表单名称")
//	@ApiModelProperty(value = "表单名称", position = 0)
//	public String formName;
}
