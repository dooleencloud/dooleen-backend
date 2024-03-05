package com.dooleen.service.app.oauth.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.app.oauth.entity.LicenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.utils.CommonUtil;
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
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserInfoServiceImpl {
 
	@Autowired
	private  SysUserInfoService sysUserInfoService;
	
	public String querySysUserInfoByUserName(String commonMsg) {
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  sysUserInfoService.querySysUserInfoByUserName(commonMsg);
		log.info("====querySysUserInfoByUserName service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}
	
	public CommonMsg<SysUserInfoEntity, SysRoleEntity> querySysUserInfoByUserName(CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  sysUserInfoService.querySysUserInfoByUserName(commonMsg);
		log.info("====querySysUserInfoByUserName service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}
	// 客户
	public String querySysCustomUserInfoByUserName(String commonMsg) {
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  sysUserInfoService.querySysCustomUserInfoByUserName(commonMsg);
		log.info("====querySysCustomUserInfoByUserName service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}

	public CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> querySysCustomUserInfoByUserName(CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  sysUserInfoService.querySysCustomUserInfoByUserName(commonMsg);
		log.info("====querySysCustomUserInfoByUserName service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}
	//license
	public String checkTenantLicense(String commonMsg) {
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  sysUserInfoService.checkTenantLicense(commonMsg);
		log.info("====checkTenantLicense service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}

	public CommonMsg<LicenseEntity, NullEntity> checkTenantLicense(CommonMsg<LicenseEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  sysUserInfoService.checkTenantLicense(commonMsg);
		log.info("====checkTenantLicense service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}
}