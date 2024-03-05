package com.xray.common.core;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoveRedisId {
	private static Jedis jedis1;
	private static String pre_str = "DOOL1001";			//redis的key的前缀
 
	/**
	 * redis批量删除以某字符串前缀的key
	 */
	public static void main(String[] args){
		System.out.println("---------------------------------testBatchDel------------------");
		jedis1 = new Jedis("192.168.43.122", 6379);
		//jedis1 = new Jedis("8.129.83.198", 6379);
		// 权限认证
		jedis1.auth("sy@redis");
		MoveRedisId tst=new MoveRedisId();
		tst.batchMove(jedis1);
	}
	
	private void batchMove(Jedis jedis){
		Set<String> set = jedis.keys(pre_str +"*");
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String keyStr = it.next();
			System.out.println(keyStr.substring(8,keyStr.length()));
			jedis.set(keyStr.substring(0,8)+":"+keyStr.substring(8,keyStr.length()),jedis.get(keyStr));
		}
	}
}
