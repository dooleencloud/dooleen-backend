package com.dooleen.service.app.oauth.service.impl;

import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.service.BizSixteenUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
public class BizSixteenUserInfoServiceImpl {
 
	@Autowired
	private BizSixteenUserInfoService bizSixteenUserInfoService;
	
	public String queryBizSixteenUserInfoByUserName(String commonMsg) {
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  bizSixteenUserInfoService.queryBizSixteenUserInfoByUserName(commonMsg);
		log.info("====querySysUserInfoByUserName service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}
	
	public CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> queryBizSixteenUserInfoByUserName(CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg =  bizSixteenUserInfoService.queryBizSixteenUserInfoByUserName(commonMsg);
		log.info("====querySysUserInfoByUserName service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}

}