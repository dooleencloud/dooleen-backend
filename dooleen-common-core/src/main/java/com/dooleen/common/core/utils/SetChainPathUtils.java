package com.dooleen.common.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dooleen.common.core.common.entity.CommonMsg;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Maintainer:liqiuhong
 * @Update:
 */
public class SetChainPathUtils {
	private final static Logger log = LoggerFactory.getLogger(SetChainPathUtils.class);
	public static void setChainPathInfo(CommonMsg commonMsg,String packageName)
	{
//		String host = "no ip";
//        try {
//            host = InetAddress.getLocalHost().getHostAddress();
//            log.debug(">>current ip address is : "+host);
//        } catch (UnknownHostException e) {
//            log.error("get server host Exception e:", e);
//        }
//        Map<String, String> mp = new HashMap<String, String>();
//        if(commonMsg.getHead().getChainPath() != null)
//        {
//        	mp = commonMsg.getHead().getChainPath();
//        }
////	      Set<String> setmap = mp.keySet();             //**(1)keySet()取出方式
////	      Iterator<String> it = setmap.iterator();
////	      while(it.hasNext())
////	      {
////	    	  log.info(">>the next value is :"+mp.get(it.next()));  //有了键值key=it.next()，再使用get()方法获得value值
////	      }
//		int currItem =  mp.size()+1;
//		String chainInfo ="host:"+host+",packageName:"+packageName;
//		log.debug(">>chainInfo = "+chainInfo);
//		mp.put(currItem+"", chainInfo);
//		commonMsg.getHead().setChainPath(mp);
	}
}
