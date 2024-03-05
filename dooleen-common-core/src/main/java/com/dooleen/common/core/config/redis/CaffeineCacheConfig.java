package com.dooleen.common.core.config.redis;
//package com.dooleen.common.core.redis.config;
//
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.caffeine.CaffeineCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//
//@Configuration
//public class CacheConfig {
//
//    /**
//     * 配置缓存管理器
//     *
//     * @return 缓存管理器
//     */
//    @Bean("caffeineCacheManager")
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                // 设置最后一次写入或访问后经过固定时间过期
//                .expireAfterAccess(60, TimeUnit.SECONDS)
//                // 初始的缓存空间大小
//                .initialCapacity(100)
//                // 缓存的最大条数
//                .maximumSize(1000));
//        return cacheManager;
//    }
//    
//    @Bean
//    public Cache<String, Object> caffeineCache() 
//    {  
//    	return Caffeine.newBuilder()                 // 设置最后一次写入或访问后经过固定时间过期                 
//    			.expireAfterWrite(60, TimeUnit.SECONDS)                 // 初始的缓存空间大小                
//    			.initialCapacity(100)                 // 缓存的最大条数                 
//    			.maximumSize(1000)                 	
//    			.build();     
//    }
//}