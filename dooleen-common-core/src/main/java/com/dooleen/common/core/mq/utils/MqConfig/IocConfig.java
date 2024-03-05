package com.dooleen.common.core.mq.utils.MqConfig;

import com.dooleen.common.core.mq.utils.RabbitUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 公共依赖注入配置类
 * @Author : apple
 * @Update:
 */ 
@Configuration
public class IocConfig {

	/**
	 * 注入rabbit
	* @Title: rabbit 
	 */
	@Bean
	public RabbitUtil rabbitUtil() {
		return new RabbitUtil();
	}
}
