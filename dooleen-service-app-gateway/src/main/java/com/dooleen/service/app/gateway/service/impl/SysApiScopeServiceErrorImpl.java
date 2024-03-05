package com.dooleen.service.app.gateway.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
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
@Component
public class SysApiScopeServiceErrorImpl implements SysApiScopeService{
	@Override
	public String querySysApiScopeByUserIdAndAddress(String commonMsg) {
		log.info(">>调用微服务dooleen-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(querySysApiScopeByUserIdAndAddress)");
		return commonMsg;
	}
	
	@Override
	public CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScopeByUserIdAndAddress(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg) {
		log.info(">>调用微服务dooleen-service-system-platform失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(querySysApiScopeByUserIdAndAddress)");
		// 设置失败返回值
		commonMsg.getHead().setRespCode(RespStatus.errorCode);
		commonMsg.getHead().setRespMsg(map);
		log.error("===error: "+ map.toString());
		return commonMsg;
	}
}