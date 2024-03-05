package com.dooleen.common.core.shardingsphere.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author liqh
 * @description 自定义复合分库策略
 * @date 2020/10/30 13:48
 */
@Slf4j
public class MyDBComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {


    @Override
    public Collection<String> doSharding(Collection<String> databaseNames, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        log.info("=============开始路由：==============");
        // 得到每个分片健对应的值
        Collection<Integer> idValues = this.getShardingValue(complexKeysShardingValue, "id");
        Collection<Integer> tenantIdValues = this.getShardingValue(complexKeysShardingValue, "tenant_id");
        System.out.println("==idValues=="+idValues);
        System.out.println("==tenantIdValues=="+tenantIdValues);
        List<String> shardingSuffix = new ArrayList<>();
        //优先处理ID分片
        if(idValues.size() > 0){
            for (Object id : idValues) {
                String suffix=((String)id).substring(0, 8);
                for (String databaseName : databaseNames) {
                    if (databaseName.endsWith(suffix)) {
                        shardingSuffix.add(databaseName);
                    }
                }
            }
        }
        else {
            for (Object tenantId : tenantIdValues) {
                String suffix=(String)tenantId;
                for (String databaseName : databaseNames) {
                    if (databaseName.endsWith(suffix)) {
                        shardingSuffix.add(databaseName);
                    }
                }
            }
        }
        log.info("=============路由结束：=============="+shardingSuffix.toString());

        return shardingSuffix;
    }

    private Collection<Integer> getShardingValue(ComplexKeysShardingValue<Integer> shardingValues, final String key) {
        Collection<Integer> valueSet = new ArrayList<>();
        Map<String, Collection<Integer>> columnNameAndShardingValuesMap = shardingValues.getColumnNameAndShardingValuesMap();
        if (columnNameAndShardingValuesMap.containsKey(key)) {
            valueSet.addAll(columnNameAndShardingValuesMap.get(key));
        }
        return valueSet;
    }
}