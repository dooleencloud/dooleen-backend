package com.dooleen.common.core.shardingsphere.strategy;

import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
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
public class MyPreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
	
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		String idStr = shardingValue.getValue();
		log.info("=============路由到数据库："+idStr.substring(0, 8)+"==============");
		return  idStr.substring(0, 8);
	}

}
