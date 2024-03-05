package com.dooleen.service.app.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.app.gateway.entity.SysApiScopeEntity;
import com.dooleen.service.app.gateway.service.impl.SysApiScopeServiceErrorImpl;

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
@FeignClient(value = "dooleen-service-system-platform" ,fallback = SysApiScopeServiceErrorImpl.class)
public interface SysApiScopeService {
	@RequestMapping(value = "/platform/sysApiScope/querySysApiScopeByUserIdAndAddress",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	String querySysApiScopeByUserIdAndAddress(@RequestBody String commonMsg);
	
	@RequestMapping(value = "/platform/sysApiScope/querySysApiScopeByUserIdAndAddress",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScopeByUserIdAndAddress(@RequestBody CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

}
