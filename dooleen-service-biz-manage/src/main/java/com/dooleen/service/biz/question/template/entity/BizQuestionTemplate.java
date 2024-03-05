package com.dooleen.service.biz.question.template.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:31:07
 * @Description : 问卷模板管理实体
 * @Author : name
 * @Update : 2021-02-22 11:31:07
 */
@Data
public class BizQuestionTemplate {
	@Excel(name ="名字")
	private String name;
	@Excel(name ="性别")
	private String sex;
	@Excel(name ="基础状态" )
	private String baseStatus;
	@Excel(name ="状态")
	private  String status;
}
