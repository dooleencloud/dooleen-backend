package com.dooleen.common.core.config.db.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程信息管理实体
 * @Author : name
 * @Update : 2020-06-28 18:24:13
 */
@Configuration
@PropertySource(value = "classpath:mongo-pool.properties")
@ConfigurationProperties(prefix = "spring.data.mongodb.dooleen1002")
public class DOOL1002MongoConfig extends AbstractMongoConfig {

    @Bean(name="DOOL1002MongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}




