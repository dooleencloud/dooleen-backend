package com.dooleen.service.app.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.service.app.gateway.entity.SysApiScopeEntity;
import com.dooleen.service.app.gateway.service.SysApiScopeService;

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
public class SysApiScopeServiceImpl {
 
	@Autowired
	private  SysApiScopeService sysApiScopeService;
	
	public String querySysApiScopeByUserIdAndAddress(String commonMsg) {
		commonMsg =  sysApiScopeService.querySysApiScopeByUserIdAndAddress(commonMsg);
		log.info("====querySysApiScopeByUserIdAndAddress service end == {}", RespStatus.json.getString("S0000"));
		return commonMsg;
	}

	public CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScopeByUserIdAndAddress(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		commonMsg =  sysApiScopeService.querySysApiScopeByUserIdAndAddress(commonMsg);
		log.info("====querySysApiScopeByUserIdAndAddress service end == {}", RespStatus.json.getString("S0000"));
		return commonMsg;
	}
}