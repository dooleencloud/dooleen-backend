package com.dooleen.service.app.oauth.service.impl;
import java.util.HashMap;
import java.util.Map;

import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.app.oauth.entity.LicenseEntity;
import org.springframework.stereotype.Component;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.entity.SysUserInfoEntity;
import com.dooleen.service.app.oauth.service.SysUserInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 
 * @Author : name
 * @Update : 2020-06-06 19:37:42
 */
@Slf4j
@Component
public class  SysUserInfoServiceErrorImpl implements SysUserInfoService{
	@Override
	public String querySysUserInfoByUserName(
			String commonMsg) {
		log.info(">>调用微服务dooleen-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(querySysUserInfoByUserName)");
		log.error("===error: ", map);
		return commonMsg;
	}
	@Override
	public CommonMsg<SysUserInfoEntity, SysRoleEntity> querySysUserInfoByUserName(
			CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg) {
		log.info(">>调用微服务dooleen-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(querySysUserInfoByUserName)");
		// 设置失败返回值
		commonMsg.getHead().setRespCode(RespStatus.errorCode);
		commonMsg.getHead().setRespMsg(map);
		log.error("===error: ", map);
		return commonMsg;
	}
	//客户
	@Override
	public String querySysCustomUserInfoByUserName(String commonMsg) {
		log.info(">>调用微服务sysing-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(querySysCustomUserInfoByUserName)");
		log.error("===error: ", map);
		return commonMsg;
	}
	@Override
	public CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> querySysCustomUserInfoByUserName(
			CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> commonMsg) {
		log.info(">>调用微服务sysing-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(querySysCustomUserInfoByUserName)");
		// 设置失败返回值
		commonMsg.getHead().setRespCode(RespStatus.errorCode);
		commonMsg.getHead().setRespMsg(map);
		log.error("===error: ", map);
		return commonMsg;
	}

	//license
	@Override
	public String checkTenantLicense(
			String commonMsg) {
		log.info(">>调用微服务dooleen-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(checkTenantLicense)");
		log.error("===error: ", map);
		return commonMsg;
	}
	@Override
	public CommonMsg<LicenseEntity, NullEntity> checkTenantLicense(
			CommonMsg<LicenseEntity, NullEntity> commonMsg) {
		log.info(">>调用微服务dooleen-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(checkTenantLicense)");
		// 设置失败返回值
		commonMsg.getHead().setRespCode(RespStatus.errorCode);
		commonMsg.getHead().setRespMsg(map);
		log.error("===error: ", map);
		return commonMsg;
	}
}