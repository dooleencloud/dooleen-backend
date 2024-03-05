package com.dooleen.common.core.async.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableConfigurationProperties(AsyncThreadPoolProperties.class)
public class TaskExecutorConfig {
	@Autowired
	private AsyncThreadPoolProperties asyncThreadPoolProperties;

	@Bean("asyncTaskExecutor")
	public ThreadPoolTaskExecutor asyncTaskExecutor() {
		System.out.println("asyncThreadPoolProperties==="+asyncThreadPoolProperties.toString());
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

		/**
		 * 拒绝策略，默认是AbortPolicy
		 * AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
		 * DiscardPolicy：丢弃任务但不抛出异常
		 * DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
		 * CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
		 */
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		// 初始化
		taskExecutor.initialize();
		return taskExecutor;
	}
}
