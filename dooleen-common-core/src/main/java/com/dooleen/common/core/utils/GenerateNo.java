package com.dooleen.common.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 生成ID、编号
 * @Author : apple
 * @Update:
 */
@Slf4j
public class GenerateNo {
	/**
	 * 生成各种编号
	 * 
	 * @param tenantId  租户号
	 * @param tableName 表名字
	 */
	
	public static  String getId(String tenantId, String tableName,String prefix,RedisTemplate redisTemplate) {
		String reValue = "";
		int len = 16;
		// String prStr = tenantId+":"+tenantId + tableName.trim();
		String prStr = tenantId +":"+ tableName.trim();
		int redisValue = 0;
		if (tenantId != null && !tenantId.trim().equals("") && tableName != null && !tableName.trim().equals("")) {
			redisValue = new Long(redisTemplate.opsForValue().increment(prStr)).intValue();
			reValue = tenantId + prefix + String.format("%08d", redisValue);
		} else {
			reValue = null;
			log.debug("生成ID时：tenantId=" + tenantId + ",tableName=" + tableName);
		}
		return reValue;
	}

	/**
	 * 生成各种编号
	 * 
	 * @param key    键值
	 * @param prefix 前缀
	 * @param 长度
	 */
	public String getNo(String key, String prefix, int len) {
		RedisUtil redisUtil = new RedisUtil();
		String reValue = "";
		String prStr = key.trim() + "-" + prefix.trim();
		int redisValue = 0;
		if (key != null && !key.trim().equals("") && prefix != null && !prefix.trim().equals("")) {
			//redisValue = new Long(redisUtil.incr(prStr,1)).intValue();
			reValue = prStr + String.format("%0" + (len - prStr.length()) + "d", redisValue);
		} else {
			log.info("生成编号时：Key=" + key + ",prefix=" + prefix);
		}
		return reValue;
	}

	/**
	 * 生成序号
	 * 
	 * @param key 键值
	 */
	public int getSeqNo(String key, String prefix) {
		RedisUtil redisUtil = new RedisUtil();
		int redisValue = 0;
		String prStr = key.trim() + "-" + prefix.trim();
		if (key != null && !key.trim().equals("") && prefix != null && !prefix.trim().equals("")) {
			//redisValue = new Long(redisUtil.incr(prStr,1)).intValue();

		} else {
			log.info("生成序号时：Key=" + key + ",prefix=" + prefix);
		}
		return redisValue;
	}
}
