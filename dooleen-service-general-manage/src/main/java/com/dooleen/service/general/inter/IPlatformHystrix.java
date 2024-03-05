package com.dooleen.service.general.inter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dooleen.common.core.app.system.param.entity.SysParamEntity;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.service.general.inter.impl.PlatformHystrixImpl;

@FeignClient(value = "dooleen-service-system-platform", fallback = PlatformHystrixImpl.class)
public interface IPlatformHystrix {

	/**
	 * 查询系统参数(加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/platform/sysParam/querySysParamListPage",method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	String querySysParamListPage(@RequestBody String commonMsgStr);
	
	/**
	 * 查询系统参数(非加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/platform/sysParam/querySysParamListPage",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<SysParamEntity, SysParamEntity> querySysParamListPage(@RequestBody CommonMsg<SysParamEntity, SysParamEntity> commonMsg);
	
	/**
	 * 分页查询三方配置(加密)
	 * @param commonMsg
	 * @return
	 */
	@RequestMapping(value="/platform/sysThirdPartyInfo/querySysThirdPartyInfoListPage", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	String querySysThirdPartyInfoListPage(@RequestBody String commonMsgStr);
	
	/**
	 * 分页查询三方配置(非加密)
	 * @param commonMsg
	 * @return
	 */
	@RequestMapping(value="/platform/sysThirdPartyInfo/querySysThirdPartyInfoListPage", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> querySysThirdPartyInfoListPage(@RequestBody CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> commonMsg);
}
