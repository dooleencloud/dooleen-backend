package com.dooleen.common.core.config.redis;
 
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.common.core.mq.utils.RabbitUtil;
import com.dooleen.common.core.utils.GenerateNo;
import com.dooleen.common.core.utils.RedisUtil;

import lombok.extern.slf4j.Slf4j;
 
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : redis缓存配置
 * @Author : apple
 * @Update:
 */ 
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
 
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
 
    /**
     * @description 自定义的缓存key的生成策略
     *              若想使用这个key  只需要将注解上keyGenerator的值设置为keyGenerator即可</br>
     * @return 自定义策略生成的key
     */
     @Bean
     public KeyGenerator keyGenerator() {
         return new KeyGenerator(){
             @Override
             public Object generate(Object target, Method method, Object... params) {
                 StringBuffer sb = new StringBuffer();
                 sb.append(target.getClass().getName());
                 sb.append(method.getName());
                 for(Object obj:params){
                     sb.append(obj.toString());
                 }
                 return sb.toString();
            }
         };
     }
    
 
     /**
      * RedisTemplate配置
      *
      * @param jedisConnectionFactory
      * @return
      */
     @Bean
     public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory ) {
         //设置序列化
         Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
         ObjectMapper om = new ObjectMapper();
         om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
         jackson2JsonRedisSerializer.setObjectMapper(om);
         //配置redisTemplate
         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
         redisTemplate.setConnectionFactory(jedisConnectionFactory);
         RedisSerializer stringSerializer = new StringRedisSerializer();
         redisTemplate.setKeySerializer(stringSerializer);//key序列化
         redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
         redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
         redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
         redisTemplate.afterPropertiesSet();
         return redisTemplate;
     }
}
