package com.dooleen.service.app.mc.listener;

import com.dooleen.common.core.constant.MqQueueConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.dooleen.common.core.app.system.module.mapper.SysModuleConfigInfoMapper;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.mq.msg.PushDataMq;
import com.dooleen.common.core.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zoujin
 * @date 2020/7/14
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.PUSH_DATA_QUEUE)
public class PushDataListener {

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private  SysModuleConfigInfoMapper sysModuleConfigInfoMapper;
	
    @RabbitHandler
    public void receive(PushDataMq pushDataMq) {
    	try {
    		log.info("执行 PUSH_DATA_QUEUE 队列监听");
        	
    		// 源租户ID
    		String sourceTenantId = pushDataMq.getSourceTenantId();
    		
    		// 目标租户列表
    		List<String> targetTenantIdList = pushDataMq.getTargetTenantIdList();
    		
    		// 源租户数据
    		Map<String, List<LinkedHashMap<String, Object>>> sourceData = pushDataMq.getSourceData();
    		
    		// 模块关联表列表
    		List<String> tableNameList = pushDataMq.getTableNameList().stream().distinct().collect(Collectors.toList());;
        	
    		// 新增sql: {"XRAY1001": "insert into ......"}
    		Map<String, String> addSqlMap = Maps.newLinkedHashMap();
    		StringBuilder addSqlSb = null;
    		StringBuilder addExecSqlSb = null;
    		
    		// 删除sql: {"XRAY1001": "delete from update_history_info;"}
    		Map<String, String> delSqlMap = Maps.newLinkedHashMap();
    		StringBuilder delExecSqlSb = null;
    		
    		String beforeValue = "";
    		String afterValue = "";
    		Map<String, Object> targetDataMap = null;
    		// 处理每个租户需要插入的数据
    		for(String targetTenantId: targetTenantIdList) {
    			
    			addExecSqlSb = new StringBuilder();
    			
    			delExecSqlSb = new StringBuilder();
    			
    			for(String tableName: tableNameList) {

    				// 新增数据Sql
    				addSqlSb = new StringBuilder();
    				addSqlSb.append("insert into ");
    				addSqlSb.append(tableName);
    				addSqlSb.append(" ( ");
    				
    				// 删除数据sql
    				delExecSqlSb.append("delete from ");
    				delExecSqlSb.append(tableName);
    				delExecSqlSb.append(" where tenant_id = '");
    				delExecSqlSb.append(targetTenantId);
    				delExecSqlSb.append("'");
    				delExecSqlSb.append(";");
    				
    				int addDataCount = 1;
    				boolean addFieldFlag = true;
    				// 处理数据的【id】和【tenantId】
    				for(LinkedHashMap<String, Object> data: sourceData.get(tableName)) {
    					targetDataMap = Maps.newLinkedHashMap(data);
    					
    					// 只有第一条数据，需要处理字段名
    					if(addFieldFlag) {						
    						// 处理表字段
    						// 判断sql字段之间是否需要添加逗号
    						int count = 1;
    						for(String key: targetDataMap.keySet()) {
    							addSqlSb.append(key);
    							// 如果不是最后一个字段，则需要加逗号
    							if(count != targetDataMap.size()) {
    								addSqlSb.append(",");
    							}
    							count ++;
    						}
    						addSqlSb.append(" ) ");
    						addSqlSb.append(" VALUES ");
    					}
    					
    					// 处理数据中，每个字段包含的租户编号串
    					for(String targetDataKey: targetDataMap.keySet()) {
    						beforeValue = null == targetDataMap.get(targetDataKey) ? "" : String.valueOf(targetDataMap.get(targetDataKey));
    						if(StringUtils.isNotBlank(beforeValue)) {
    							afterValue = beforeValue.replace(sourceTenantId, targetTenantId);
    							targetDataMap.put(targetDataKey, afterValue);
    						}
    					}
    					
    					addSqlSb.append(" ( ");
    					
    					// 处理表数据
    					// 判断sql字段之间是否需要添加逗号
    					int count = 1;
    					for(String key: targetDataMap.keySet()) {
    						addSqlSb.append("'");
    						addSqlSb.append(targetDataMap.get(key));
    						addSqlSb.append("'");
    						// 如果不是最后一个字段的数据，则需要加逗号
    						if(count != targetDataMap.size()) {
    							addSqlSb.append(",");
    						}
    						count ++;
    					}
    					
    					addSqlSb.append(" ) ");
    					
    					// 如果不是最后一条数据，则数据括号之间需要加逗号
    					// 如果是最后一条数据，则加上分号
    					if(addDataCount != sourceData.get(tableName).size()) {
    						addSqlSb.append(",");
    					}else {
    						addSqlSb.append(";");
    					}
    										
    					addFieldFlag = false;
    					
    					addDataCount ++;
    				}
    				
    				addExecSqlSb.append(addSqlSb);
    			}
    		
    			addSqlMap.put(targetTenantId, addExecSqlSb.toString());
    			delSqlMap.put(targetTenantId, delExecSqlSb.toString());
    		}

    		// 查询模块对应的redis相关数据
    		List<String> redisKeyList = Lists.newArrayList();
    		tableNameList.forEach(tableName -> {
    			String entityName = TableEntityORMEnum.getEntityByTable(tableName);
    			// 拼接redis key : 租户id + 实体类名  （例：XRAY1001SysMenu）
    			if(StringUtils.isNotBlank(entityName)) {				
    				redisKeyList.add(sourceTenantId + entityName);
    			}
    		});
    		
    		// 获取redis中的数据：{XRAY1001SysMenu: 356}, 并处理为各个目标租户的redis数据: {XRAY1002SysMenu: 356}
    		Map<String, String> redisMap = Maps.newHashMap();
    		redisKeyList.forEach(redisKey -> {			
    			String no = String.valueOf(redisUtil.get(redisKey));
    			for(String targetTenantId: targetTenantIdList) {				
    				redisMap.put(redisKey.replace(sourceTenantId, targetTenantId), no);
    			}
    		});
    		
    		
    		// 拼接插入数据的sql
    		for(String key: addSqlMap.keySet()) {	
    			
    			// 插入数据前，先删除旧数据
    			sysModuleConfigInfoMapper.deleteBaseDataBatch(delSqlMap.get(key));
    			
    			// 插入数据
    			sysModuleConfigInfoMapper.insertBaseDataBatch(addSqlMap.get(key));
    			
    			// 更新redis
    			for(String redisKey: redisMap.keySet()) {
    				if(key.equals(redisKey.substring(0, 8))) {
    					redisUtil.setObject(redisKey, redisMap.get(redisKey));
    				}
    			}
    		}
    		
    		log.info("推送租户数据成功！");
		} catch (Exception e) {
			log.error("推送基础数据报错：{}", e.getMessage());
		}
    }
}
