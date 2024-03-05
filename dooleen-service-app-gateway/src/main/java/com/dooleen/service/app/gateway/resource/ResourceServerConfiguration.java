package com.dooleen.service.app.gateway.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.dooleen.service.app.gateway.conf.FilterUrlsPropertiesConfig;

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

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private FilterUrlsPropertiesConfig filterUrlsPropertiesConfig;
    
	@Override
    public void configure(HttpSecurity http) throws Exception {
        // 请求进行拦截 验证 accessToken
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        for (String url : filterUrlsPropertiesConfig.getAnon()) {
            registry.antMatchers(url).permitAll();
        }
        //拦截其他所有请求
        registry.anyRequest().authenticated()
                .and().csrf().disable();
    }
}
