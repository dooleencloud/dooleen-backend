package com.dooleen.common.core.async.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;
import java.util.concurrent.Executor;


@Configuration
@EnableAsync
@EnableConfigurationProperties(AsyncThreadPoolProperties.class)
public class AsyncConfig implements AsyncConfigurer {
	@Autowired
	private AsyncThreadPoolProperties asyncThreadPoolProperties;

	@Override
	public Executor getAsyncExecutor() {
		//Java虚拟机可用的处理器数
		int processors = Runtime.getRuntime().availableProcessors();

		//定义线程池
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

		//核心线程数
		taskExecutor.setCorePoolSize(Objects.nonNull(asyncThreadPoolProperties.getCorePoolSize()) ? asyncThreadPoolProperties.getCorePoolSize() : processors);

		//线程池最大线程数,默认：20
		taskExecutor.setMaxPoolSize(Objects.nonNull(asyncThreadPoolProperties.getMaxPoolSize()) ? asyncThreadPoolProperties.getMaxPoolSize() : 20);

		//线程队列最大线程数,默认：1000
		taskExecutor.setQueueCapacity(Objects.nonNull(asyncThreadPoolProperties.getMaxPoolSize()) ? asyncThreadPoolProperties.getMaxPoolSize() : 1000);

		//线程名称前缀
		taskExecutor.setThreadNamePrefix(StringUtils.isNotEmpty(asyncThreadPoolProperties.getThreadNamePrefix()) ? asyncThreadPoolProperties.getThreadNamePrefix() : "doo-executor-");

		//线程池中线程最大空闲时间，默认：60，单位：秒
		taskExecutor.setKeepAliveSeconds(asyncThreadPoolProperties.getKeepAliveSeconds());

		//核心线程是否允许超时，默认:false
		taskExecutor.setAllowCoreThreadTimeOut(asyncThreadPoolProperties.isAllowCoreThreadTimeOut());

		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
