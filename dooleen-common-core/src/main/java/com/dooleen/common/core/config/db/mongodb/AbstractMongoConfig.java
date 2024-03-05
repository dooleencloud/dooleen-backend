package com.dooleen.common.core.config.db.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Data;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


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
@Data
public class AbstractMongoConfig {

    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String database;

    /**
     * 创建MongoDbFactory，不同数据源继承该方法创建对应的MongoDbFactory。
     * @return
     * @throws Exception
     */
     public MongoDbFactory mongoDbFactory() throws Exception {
         ServerAddress serverAddress = new ServerAddress(host, port);
         MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());
         MongoClientOptions options = MongoClientOptions.builder()
                 .connectionsPerHost(100)
                 .socketTimeout(30000)
                 .connectTimeout(3000)
                 .build();
         return new SimpleMongoDbFactory(new MongoClient(serverAddress, mongoCredential, options), database);
     }

}

