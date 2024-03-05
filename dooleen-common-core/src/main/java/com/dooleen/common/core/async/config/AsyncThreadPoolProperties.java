package com.dooleen.common.core.async.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @description: 异步线程池配置文件
 */
@Data
@ConfigurationProperties(prefix = "spring.async.thread.pool")
public class AsyncThreadPoolProperties {
    
    /**
     * 核心线程数,默认：Java虚拟机可用线程数
     */
    private Integer corePoolSize;
    
    /**
     * 线程池最大线程数,默认：40000
     */
    private Integer maxPoolSize;
    
    /**
     * 线程队列最大线程数,默认：80000
     */
    private Integer queueCapacity;
    
    /**
     * 自定义线程名前缀，默认：Async-ThreadPool-
     */
            private String threadNamePrefix;
    
    /**
     * 线程池中线程最大空闲时间，默认：60，单位：秒
     */
    private Integer keepAliveSeconds;
    
    /**
     * 核心线程是否允许超时，默认false
     */
    private boolean allowCoreThreadTimeOut;

}
