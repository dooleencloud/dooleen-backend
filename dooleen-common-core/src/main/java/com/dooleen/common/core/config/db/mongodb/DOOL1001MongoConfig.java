package com.dooleen.common.core.config.db.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile 2018-08-12 -- 17:12
 * @Version 1.0
 * @Description     platform_server
 */
@Configuration
@PropertySource(value = "classpath:mongo-pool.properties")
@ConfigurationProperties(prefix = "spring.data.mongodb.dooleen1001")
public class DOOL1001MongoConfig extends AbstractMongoConfig {

    @Primary
    @Bean(name="DOOL1001MongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }


}
