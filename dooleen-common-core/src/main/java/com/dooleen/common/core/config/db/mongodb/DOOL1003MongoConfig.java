package com.dooleen.common.core.config.db.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-08-12 -- 17:17
 * @Version 1.0
 * @Description         login
 */
@Configuration
@PropertySource(value = "classpath:mongo-pool.properties")
@ConfigurationProperties(prefix = "spring.data.mongodb.dooleen1003")
public class DOOL1003MongoConfig extends AbstractMongoConfig {

    @Bean(name="DOOL1003MongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

}




