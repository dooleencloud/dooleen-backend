package com.dooleen.common.core.config.oauth2.properties;

import lombok.Data;

/**
 * 
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:24:47
 * @Description : 拦截@EnableGameleyLog注解的方法，将具体修改存储到数据库中
 * @Author : apple
 * @Update: 2020年5月9日 下午10:24:47
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private Integer accessTokenValiditySeconds = 7200;
}
