package com.dooleen.service.general.inter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dooleen.common.core.app.system.param.entity.SysParamEntity;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.service.general.inter.IPlatformHystrix;

@Component
public class PlatformHystrixImpl implements IPlatformHystrix {
	private final static Logger logger = LoggerFactory.getLogger(PlatformHystrixImpl.class);
	
	@Override
	public String querySysParamListPage(String commonMsgStr) {
		logger.error("querySysParamListPage（加密）-查询系统参数接口，进入熔断，返回null");
		return null;
	}
	
	@Override
	public CommonMsg<SysParamEntity, SysParamEntity> querySysParamListPage(CommonMsg<SysParamEntity, SysParamEntity> commonMsg) {
		logger.error("querySysParamListPage（非加密）-查询系统参数接口，进入熔断，返回null");
		return null;
	}
	
	@Override
	public String querySysThirdPartyInfoListPage(String commonMsgStr) {
		logger.error("querySysThirdPartyInfoListPage（加密）-分页查询三方配置接口，进入熔断，返回null");
		return null;
	}
	
	@Override
	public CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> querySysThirdPartyInfoListPage(CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> commonMsg) {
		logger.error("querySysThirdPartyInfoListPage（非加密）-分页查询三方配置接口，进入熔断，返回null");
		return null;
	}
}
