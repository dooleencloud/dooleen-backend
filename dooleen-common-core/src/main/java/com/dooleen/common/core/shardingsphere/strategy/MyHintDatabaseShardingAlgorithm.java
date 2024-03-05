package com.dooleen.common.core.shardingsphere.strategy;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 自定义数据库路由算法
 * @Author : apple
 * @Update:
 */ 		

@Slf4j
public final class MyHintDatabaseShardingAlgorithm implements HintShardingAlgorithm<String> {
    
    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final HintShardingValue<String> shardingValue) {
    	log.info("=============路由到数据库：==============");
    	Collection<String> result = new ArrayList<>();
        for (String each : availableTargetNames) {
            for (String value : shardingValue.getValues()) {
            	log.info("=============路由到数据库："+value+"==============");
            	result.add(value);
            }
        }
        return result;
    }
}
