package com.dooleen.common.core.config.ioc;

import java.util.Arrays;

import com.alibaba.druid.support.http.StatViewServlet;
import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.common.core.mq.utils.RabbitUtil;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.GenerateNo;
import com.dooleen.common.core.utils.RedisUtil;
import com.dooleen.common.core.utils.TencentMeetingUtil;

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
	 * 注入redisUtil
	* @Title: redisUtil 
	 */
	@Bean
	public RedisUtil redisUtil(RedisTemplate redisTemplate) {
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.setRedisTemplate(redisTemplate);
		return redisUtil;
	}
	/**
	 * 注入生成序号
	* @Title: GenerateNo 
	 */
	@Bean
	public GenerateNo generateNo() {
		return new GenerateNo();
	}
	
	/**
	 * 注入rabbit
	* @Title: rabbit 
	 */
	@Bean
	public RabbitUtil rabbitUtil() {
		return new RabbitUtil();
	}

	/**
	 * 注入TencentMeetingUtil
	 * @Title: TencentMeetingUtil 
	 */
	@Bean
	public TencentMeetingUtil tencentMeetingUtil() {
		return new TencentMeetingUtil();
	}
	
	/**
	 * 注入MsgSendUtil
	* @Title: msgSendUtil 
	 */
	@Bean
	public MsgSendUtil msgSendUtil() {
		return new MsgSendUtil();
	}
	
	/**
	 * 注入微信工具类
	 * 
	 * @Title: GenerateNo
	 */
	@Bean
	public BeanUtils beanUtils() {
		return new BeanUtils();
	}
	
	/**
	 * 注入ObjectMapper
	 * 
	 * @Title: ObjectMapper
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
	
	/**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    	paginationInterceptor.setLimit(-1);
		paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;
    }

	@Bean // 使用@Bean注入fastJsonHttpMessageConvert
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.需要定义一个Convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2.添加fastjson的配置信息，比如是否要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON_UTF8));
		// SerializerFeature.WriteMapNullValue序列化null的字段
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,   //字符串null返回空字符串
                SerializerFeature.WriteNullNumberAsZero,    //数值类型为0
                SerializerFeature.WriteNullListAsEmpty,     //空字段保留
                SerializerFeature.WriteMapNullValue
                );
		// 3.在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
	// druid 访问
	@Bean
	public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
		ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(),  "/druid/*");
		//registrationBean.addInitParameter("allow", "127.0.0.1");// IP白名单 (没有配置或者为空，则允许所有访问)
		registrationBean.addInitParameter("deny", "");// IP黑名单 (存在共同时，deny优先于allow)
		registrationBean.addInitParameter("loginUsername", "root");
		registrationBean.addInitParameter("loginPassword", "Druid@2020");
		registrationBean.addInitParameter("resetEnable", "false");
		registrationBean.addInitParameter("aopPatterns", "com.dooleen.service.system.api.scope.service.impl.*");

		return registrationBean;
	}
}
