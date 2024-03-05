package com.dooleen.common.core.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import com.dooleen.common.core.aop.aspect.SendMsgAspect;
import com.dooleen.common.core.aop.aspect.ModifyAspect;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.dooleen.common.core.aop.aspect"})
public class AspectConfig {
	
	@Bean
	@Order(1)
	public ModifyAspect getModifyAspect(){
		return new ModifyAspect();
	}
	
	@Bean
	@Order(2)
	public SendMsgAspect getSendMsgAspect(){
		return new SendMsgAspect();
	}
}
