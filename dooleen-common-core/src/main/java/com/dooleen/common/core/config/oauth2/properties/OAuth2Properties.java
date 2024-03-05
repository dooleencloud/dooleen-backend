package com.dooleen.common.core.config.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:24:47
 * @Description : 
 * @Author : apple
 * @Update: 2020年5月9日 下午10:24:47
 */
@Data
@ConfigurationProperties(prefix = "dooleen.security.oauth2")
public class OAuth2Properties {

    private String jwtSigningKey = "DooleenDooleen";

    private OAuth2ClientProperties[] clients = {};
}
