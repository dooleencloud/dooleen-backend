package com.dooleen.common.core.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.dooleen.common.core.enums.TableEntityORMEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CheckTableEntityORME {
	
	public static void checkTableEntityORME() throws Exception {
		log.debug("开始检查表实体枚举类。。。。。。");
    	TableEntityORMEnum[] enums = TableEntityORMEnum.values();
    	List<String> tableNameList = new ArrayList<String>();
    	List<String> entityNameList = new ArrayList<String>();
    	List<String> seqPrefixList = new ArrayList<String>();
    	Set<String> repeatTableNameList = new HashSet<String>();
    	Set<String> repeatEntityNameList = new HashSet<String>();
    	Set<String> repeatSeqPrefixList = new HashSet<String>();
		for (TableEntityORMEnum enumTmp : enums) {
			// 判断表名是否有重复
			if(tableNameList.contains(enumTmp.getTableName())) {
				repeatTableNameList.add(enumTmp.getTableName());
			}else {
				tableNameList.add(enumTmp.getTableName());
			}
			// 判断实体类名是否有重复
			if(entityNameList.contains(enumTmp.getEntityName())) {
				repeatEntityNameList.add(enumTmp.getEntityName());
			}else {
				entityNameList.add(enumTmp.getEntityName());
			}
			// 判断ID前缀是否有重复
			if(seqPrefixList.contains(enumTmp.getSeqPrefix())) {
				repeatSeqPrefixList.add(enumTmp.getSeqPrefix());
			}else {
				seqPrefixList.add(enumTmp.getSeqPrefix());
			}
		}
		
		if(!repeatTableNameList.isEmpty() || !repeatEntityNameList.isEmpty() || !repeatSeqPrefixList.isEmpty()) {
			log.error("表实体枚举类中，数据有重复!!!!");
			log.debug("重复tableName：{}", repeatTableNameList);
			log.debug("重复entityName：{}", repeatEntityNameList);
			log.debug("重复seqPrefix：{}", repeatSeqPrefixList);
			throw new Exception("表实体枚举类中，数据有重复!!!!");
		}
	}
}
