package com.dooleen.common.core.common.entity;

import java.io.Serializable;
import java.util.List;

import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;

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
@ApiModel(value="报文主体")
public class MutBean<S,T> implements Serializable{ 

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "单报文体", position = 1)
	private S singleBody; //单报文体
	
	@ApiModelProperty(value = "多条报文体", position = 2)
	private List<T> listBody;//多条报文体 

	@ApiModelProperty(value = "扩展报文体", position = 3)
	private T extBody;//扩展报文体

	@ApiModelProperty(value = "流程报文体", position = 4)
	private FlowProcessDataEntity flowArea; //流程扩展区域
	
	@ApiModelProperty(value = "与查询条件报文", position = 5)
	private List<SQLConditionEntity> sqlCondition;//查询条件
	
	@ApiModelProperty(value = "或查询条件报文", position = 6)
	private List<SQLConditionEntity> sqlOrCondition;//或查询条件
	
	@ApiModelProperty(value = "排序规则	", position = 7)
	private List<SQLOrderEntity> orderRule;//查询条件
	
	@ApiModelProperty(value = "当前页数", position = 8)
	private int currentPage;//当前页数
	
	@ApiModelProperty(value = "每页条数", position = 9)
	private int pageSize = 10;//每页条数
	
	@ApiModelProperty(value = "数据总条数", position =10)
	private long total;//数据总条数
}
