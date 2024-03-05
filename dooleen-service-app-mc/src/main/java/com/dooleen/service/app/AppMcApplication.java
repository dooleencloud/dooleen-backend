package com.dooleen.service.app;

import com.dooleen.common.core.aop.annos.EnableOperateLog;
import com.dooleen.common.core.utils.CheckTableEntityORME;
import com.dooleen.common.core.utils.DooleenBanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@EnableTransactionManagement
@EnableSwagger2
@EnableOperateLog
@EnableAsync
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
//若需要加密传输报文将"com.dooleen.common.core.utils.aes" 加到basePackages中
@ComponentScan(basePackages = {
		"com.dooleen.common.core.app.**",
		"com.dooleen.service.app.**",
		"com.dooleen.common.core.utils.aes",
		"com.dooleen.common.core.config.redis",
		"com.dooleen.common.core.config.mq",
		"com.dooleen.common.core.async.config",
		"com.dooleen.common.core.config.ioc"})
//扫描DAO
@MapperScan(basePackages = {
		"com.dooleen.service.app.mc.**.mapper",
		"com.dooleen.common.core.app.**.mapper"})
public class AppMcApplication {
	public static void main(String[] args) throws Exception {
		// 检查TableEntityORMEnum中是否有重复元素
		CheckTableEntityORME.checkTableEntityORME();

		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
		SpringApplication springApplication = new SpringApplication(AppMcApplication.class);
		springApplication.setBanner(new DooleenBanner());
		springApplication.run(args);
	}
}
