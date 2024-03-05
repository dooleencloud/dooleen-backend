package com.dooleen.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 生成ID、编号
 * @Author : apple
 * @Update:
 */
@Slf4j
@Component
public class FormatUserName {
	/**
	 * 全系统通过下拉框选择的用户传到后台为 姓名(工号)，将格式化为数组[用户名，工号]
	 * 
	 * @param userInfo  姓名(工号)
	 * @return String[]<realName,userName>
	 */
	public static String[] getUser(String userInfo) {
		String user = userInfo.replace(")", "");
		String[] userList = user.split("\\(");
		return userList;
	}
	public static String getAllName(String realName,String userName) {
		return realName.trim()+"("+userName.trim()+")";
	}
	
	public static String[] getRole(String roleInfo) {
		String role = roleInfo.replace(")", "");
		String[] roleList = role.split("\\(");
		return roleList;
	}
}
