package com.dooleen.service.app.oauth.properties;

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
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private Integer accessTokenValiditySeconds;
    
    private Integer refreshTokenValiditySeconds;
}

