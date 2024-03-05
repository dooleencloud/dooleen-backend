
package com.dooleen.common.core.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 公共报文头
 * @Author : apple
 * @Update:
 */
@Data
public class BaseEntity implements Serializable {

	public static final long serialVersionUID = 1L;
	// 数据签名
	@DataName(name="有效标志")
	@ApiModelProperty(value="有效标志",position=200)
	public String validFlag;

	// 数据签名
	@DataName(name = "数据签名")
	@ApiModelProperty(value = "数据签名", position = 200)
	@ToString.Exclude
	public String dataSign;
	// 创建人ID
	@DataName(name = "创建人用户名")
	@ApiModelProperty(value = "创建人用户名", position = 201)
	@ToString.Exclude
	public String createUserName;;

	// 创建人姓名
	@DataName(name = "创建人姓名")
	@ApiModelProperty(value = "创建人姓名", position = 202)
	@ToString.Exclude
	public String createRealName;

	// 创建时间
	@DataName(name = "创建时间")
	@ApiModelProperty(value = "创建时间", position = 203)
	@ToString.Exclude
	public String createDatetime;

	// 修改人ID
	@DataName(name = "修改人用户名")
	@ApiModelProperty(value = "修改人用户名", position = 204)
	@ToString.Exclude
	public String updateUserName;

	// 修改人姓名
	@DataName(name = "修改人姓名")
	@ApiModelProperty(value = "修改人姓名", position = 205)
	@ToString.Exclude
	public String updateRealName;

	// 修改时间
	@DataName(name = "修改时间")
	@ApiModelProperty(value = "修改时间", position = 206)
	@ToString.Exclude
	public String updateDatetime;

	// 备用1
	@DataName(name = "备用1")
	@ApiModelProperty(value = "备用1", position = 207)
	@ToString.Exclude
	public String reserveString;

	// 备用2
	@DataName(name = "备用2")
	@ApiModelProperty(value = "备用2", position = 208)
	@ToString.Exclude
	public String reserveString2;

	// 备用
	@DataName(name = "")
	@ApiModelProperty(value = "备用数字1", position = 210)
	@ToString.Exclude
	public BigDecimal reserveDecimal1;

	// 备用
	@DataName(name = "")
	@ApiModelProperty(value = "备用数字2", position = 211)
	@ToString.Exclude
	public BigDecimal reserveDecimal2;

}
