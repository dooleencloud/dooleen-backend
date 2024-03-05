package com.dooleen.service.app.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.dooleen.common.core.utils.CheckTableEntityORME;
import com.dooleen.common.core.utils.DooleenBanner;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
//import com.dooleen.service.app.gateway.filter.AccessFilter;

/**
 * 
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:24:47
 * @Description :  启动类
 * @Author : apple
 * @Update: 2020年5月9日 下午10:24:47
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableZuulProxy
@EnableHystrixDashboard
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = {
		"com.dooleen.common.core.app.**",
		"com.dooleen.service.app.gateway.*",
		"com.dooleen.common.core.config.oauth2.properties",
		"com.dooleen.common.core.global.exception",
		"com.dooleen.common.core.utils.ae",
		"com.dooleen.common.core.config.redis",
		"com.dooleen.common.core.config.ioc"})
//扫描DAO
@MapperScan(basePackages = {
		"com.dooleen.common.core.app.**.mapper"})
public class AppGatewayApplication {

    public static void main(String[] args) throws Exception {
		// 检查TableEntityORMEnum中是否有重复元素
		CheckTableEntityORME.checkTableEntityORME();
		
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    	SpringApplication springApplication = new SpringApplication(AppGatewayApplication.class);
    	springApplication.setBanner(new DooleenBanner());
        springApplication.run(args); 
    }
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 允许cookies跨域
		config.addAllowedOrigin("*");// 允许向该服务器提交请求的URI，*表示全部允许。。这里尽量限制来源域，比如http://xxxx:8080 ,以降低安全风险。。
		config.addAllowedHeader("*");// 允许访问的头信息,*表示全部
		config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
		config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");// 允许Get的请求方法
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
