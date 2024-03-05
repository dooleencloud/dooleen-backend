package com.dooleen.service.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.dooleen.common.core.utils.DooleenBanner;
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Import(FdfsClientConfig.class)
@ComponentScan(basePackages = {
		"com.dooleen.common.core.app.**",
		"com.dooleen.service.file.**",
		"com.dooleen.service.file.manage.fastdfs.util",
		"com.dooleen.common.core.config.oauth2.properties",
		"com.dooleen.common.core.utils.ae",
		"com.dooleen.common.core.swagger",
		"com.dooleen.common.core.aop.aspect",
		"com.dooleen.common.core.global.exception",
		"com.dooleen.common.core.config.redis",
		"com.dooleen.common.core.config.ioc"})
//扫描DAO
@MapperScan(basePackages = {"com.dooleen.common.core.app.**.mapper"})
public class FileManageApplication {
    public static void main(String[] args) {
    	AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    	SpringApplication springApplication = new SpringApplication(FileManageApplication.class);
    	springApplication.setBanner(new DooleenBanner());
        springApplication.run(args); 
    }

}
