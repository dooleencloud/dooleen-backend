package com.dooleen.common.core.config.db.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
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

public class MongoConfig {
    MongoTransactionManager transactionManager(MongoDbFactory factory){
        return new MongoTransactionManager(factory);
    }
}