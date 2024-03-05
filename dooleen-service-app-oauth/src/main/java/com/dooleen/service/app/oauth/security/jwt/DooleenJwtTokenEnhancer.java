package com.dooleen.service.app.oauth.security.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.app.oauth.entity.SysUserInfoEntity;

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
public class DooleenJwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    	SysUserInfoEntity user = (SysUserInfoEntity)authentication.getPrincipal();
    	JSONObject jsonObj = new JSONObject();
		jsonObj.put("userId", user.getId());
		jsonObj.put("password", "");
    	jsonObj.put("companyName", user.getCompanyName());
    	jsonObj.put("belongOrgNo", user.getBelongOrgNo());
    	jsonObj.put("belongOrgName", user.getBelongOrgName());
		jsonObj.put("userName", user.getLoginName());
    	jsonObj.put("realName", user.getRealName());
    	jsonObj.put("tenantId", user.getTenantId());
    	jsonObj.put("avatar", user.getHeadImgUrl());
    	jsonObj.put("nickName", user.getNickName());
    	jsonObj.put("sex", user.getSex());
		jsonObj.put("email", user.getEmail());
    	jsonObj.put("passwordStatus", user.getPasswordStatus());
    	jsonObj.put("status", user.getStatus());
		jsonObj.put("effectDate", user.getEffectDate());
		jsonObj.put("invalidDate", user.getInvalidDate());
        Map<String, Object> info = new HashMap<>();
        info.put("dooleen", "https://www.dooleen.com/");
        info.put("userInfo",jsonObj);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
