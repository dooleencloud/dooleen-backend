package com.dooleen.common.core.app.general.staff.info.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-18 18:12:48
 * @Description : 教育信息实体
 * @Author : name
 * @Update : 2020-06-18 18:12:48
 */
@Data
public class EducationEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * 教育阶段
	 */
	private String eduStage;
	/**
	 * 毕业学校
	 */
	private String graduationSchool;
	/**
	 * 毕业日期
	 */
	private String graduationDate;
	/**
	 * 学历
	 */
	private String eduDegreeName;
}
