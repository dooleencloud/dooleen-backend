package com.dooleen.service.app.oauth.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.*;

import com.dooleen.service.app.oauth.server.RedisTokenStore;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 清除Redis中 accesstoken refreshtoken
     *
     * @param accesstoken  accesstoken
     * @return true/false
     */
    @PostMapping("/removeToken")
    public Boolean removeToken(@RequestBody Map<String, String> tokenMap) {
    	RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
    	redisTokenStore.setPrefix("DooleenOauth:OAUTH:");
    	
    	OAuth2AccessToken readAccessToken = redisTokenStore.readAccessToken(tokenMap.get("accessToken"));
    	OAuth2RefreshToken readRefreshToken = redisTokenStore.readRefreshToken(tokenMap.get("refreshToken"));
    	
    	redisTokenStore.removeAccessToken(readAccessToken);
    	redisTokenStore.removeRefreshToken(readRefreshToken);
        return true;
    }

}