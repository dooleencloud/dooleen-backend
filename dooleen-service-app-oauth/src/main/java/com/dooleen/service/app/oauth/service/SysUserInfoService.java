package com.dooleen.service.app.oauth.service;

import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.app.oauth.entity.LicenseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.entity.SysUserInfoEntity;
import com.dooleen.service.app.oauth.service.impl.SysUserInfoServiceErrorImpl;

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
@Service
@FeignClient(value = "dooleen-service-system-platform" ,fallback = SysUserInfoServiceErrorImpl.class)
public interface SysUserInfoService {
	@RequestMapping(value = "/platform/sysUserInfo/querySysUserInfoByUserName",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	String querySysUserInfoByUserName(@RequestBody String commonMsg);
	
	@RequestMapping(value = "/platform/sysUserInfo/querySysUserInfoByUserName",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<SysUserInfoEntity, SysRoleEntity> querySysUserInfoByUserName(@RequestBody CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg);

	//客户
	@RequestMapping(value = "/platform/sysCustomUserInfo/querySysCustomUserInfoByUserName",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	String querySysCustomUserInfoByUserName(@RequestBody String commonMsg);

	@RequestMapping(value = "/platform/sysCustomUserInfo/querySysCustomUserInfoByUserName",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> querySysCustomUserInfoByUserName(@RequestBody CommonMsg<SysCustomUserInfoEntity, SysRoleEntity> commonMsg);

	@RequestMapping(value = "/platform/checkTenantLicense",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	String checkTenantLicense(@RequestBody String commonMsg);

	@RequestMapping(value = "/platform/checkTenantLicense",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<LicenseEntity, NullEntity> checkTenantLicense(@RequestBody CommonMsg<LicenseEntity, NullEntity> commonMsg);

}