package com.dooleen.service.general;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-18 18:12:48
 * @Description : 人员管理应用启动
 * @Author : liqiuhong
 * @Update: 2020-06-18 18:12:48
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@EnableTransactionManagement
@EnableSwagger2
@EnableOperateLog
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
//若需要加密传输报文将"com.dooleen.common.core.utils.aes" 加到basePackages中
@ComponentScan(basePackages = {
		"com.dooleen.common.core.app.**",
		"com.dooleen.service.general.**",
		"com.dooleen.common.core.aop.aspect",
		"com.dooleen.service.general.util",
		"com.dooleen.common.core.utils.aes",
		"com.dooleen.common.core.swagger",
		"com.dooleen.common.core.global.exception",
		"com.dooleen.common.core.config.redis",
		"com.dooleen.common.core.async.config",
		"com.dooleen.common.core.config.ioc"})
//扫描DAO
@MapperScan(basePackages = {
		"com.dooleen.service.general.**.mapper",
		"com.dooleen.common.core.app.**.mapper"})
public class GeneralManageApplication {
    public static void main(String[] args) throws Exception {
		// 检查TableEntityORMEnum中是否有重复元素01
		CheckTableEntityORME.checkTableEntityORME();
		
		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
		SpringApplication springApplication = new SpringApplication(GeneralManageApplication.class);
		springApplication.setBanner(new DooleenBanner());
		springApplication.run(args); 
    }
}
