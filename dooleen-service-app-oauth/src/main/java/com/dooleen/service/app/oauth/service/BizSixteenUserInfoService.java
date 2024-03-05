package com.dooleen.service.app.oauth.service;

import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.service.app.oauth.entity.SysRoleEntity;
import com.dooleen.service.app.oauth.service.impl.BizSixteenUserInfoServiceErrorImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@FeignClient(value = "dooleen-service-biz-manage" ,fallback = BizSixteenUserInfoServiceErrorImpl.class)
public interface BizSixteenUserInfoService {
	@RequestMapping(value = "/biz/sixteen/bizSixteenUserInfo/queryBizSixteenUserInfoByUserName",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	String queryBizSixteenUserInfoByUserName(@RequestBody String commonMsg);
	
	@RequestMapping(value = "/biz/sixteen/bizSixteenUserInfo/queryBizSixteenUserInfoByUserName",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> queryBizSixteenUserInfoByUserName(@RequestBody CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> commonMsg);

}