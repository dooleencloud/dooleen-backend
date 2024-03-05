package com.dooleen.service.app.oauth.service.impl;

import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.service.BizSixteenUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 
 * @Author : name 产品
 * @Update : 2020-06-06 19:37:42
 */
@Slf4j
@Component
public class BizSixteenUserInfoServiceErrorImpl implements BizSixteenUserInfoService {
	@Override
	public String queryBizSixteenUserInfoByUserName(
			String commonMsg) {
		log.info(">>调用微服务dooleen-service-biz-manage失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(queryBizSixteenUserInfoByUserName)");
		log.error("===error: "+map.toString());
		return commonMsg;
	}
	@Override
	public CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> queryBizSixteenUserInfoByUserName(
			CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> commonMsg) {
		log.info(">>调用微服务dooleen-service-biz-manage失败，进入断路器！");
		Map<String, String> map = new HashMap<String, String>(2);
		// 设置失败返回信息
		map.put("E9001", RespStatus.json.getString("E9001")+"(queryBizSixteenUserInfoByUserName)");
		// 设置失败返回值
		commonMsg.getHead().setRespCode(RespStatus.errorCode);
		commonMsg.getHead().setRespMsg(map);
		log.error("===error: "+map.toString());
		return commonMsg;
	}
}