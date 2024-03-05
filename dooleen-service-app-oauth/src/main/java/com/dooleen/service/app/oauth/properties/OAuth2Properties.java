package com.dooleen.service.app.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

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
@Data
@ConfigurationProperties(prefix = "dooleen.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey = "DooleenDooleen";
    private OAuth2ClientProperties[] clients = {};
}
