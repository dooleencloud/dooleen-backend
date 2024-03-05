package ${sysToolTablesEntity.packageName};

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

import com.dooleen.common.core.aop.annos.EnableOperateLog;
import com.dooleen.common.core.utils.DooleenBanner;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : ${.now?string("yyyy-MM-dd HH:mm:ss")}
 * @Description : ${sysToolTablesEntity.serviceName}应用启动
 * @Author : liqiuhong
 * @Update: ${.now?string("yyyy-MM-dd HH:mm:ss")}
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
		"${sysToolTablesEntity.packageName}.**",
		"com.dooleen.common.core.app.**",
		"com.dooleen.common.core.historylog.*",
		"com.dooleen.common.core.utils.ae",
		"com.dooleen.common.core.swagger",
		"com.dooleen.common.core.global.exception",
		"com.dooleen.common.core.redis.config",
		"com.dooleen.common.core.ioc.config"})
//扫描DAO
@MapperScan(basePackages = {
		"${sysToolTablesEntity.packageName}.**.mapper",
		"com.dooleen.common.core.app.**.mapper",
		}) 
public class ${sysToolTablesEntity.tableName ? cap_first}Application {
    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    	SpringApplication springApplication = new SpringApplication(${sysToolTablesEntity.tableName ? cap_first}Application.class);
    	springApplication.setBanner(new DooleenBanner());
        springApplication.run(args); 
    }
}
