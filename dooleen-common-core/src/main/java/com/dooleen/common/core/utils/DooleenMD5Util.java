package com.dooleen.common.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.extern.slf4j.Slf4j;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Author:liqiuhong
 * @Update:
 */
@Slf4j
public class DooleenMD5Util {
	    /**
	     * MD5方法
	     * 
	     * @param text 明文
	     * @param key 密钥
	     * @return 密文
	     * @throws Exception
	     */
	    public static String md5(String text, String key){
	    	//System.out.println("MD5加密后的字符串为:encodeStr=");
	        //加密后的字符串
	        String encodeStr=DigestUtils.md5Hex(text + key);
	        //System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
	        return encodeStr;
	        }

	    /**
	     * MD5验证方法
	     * 
	     * @param text 明文
	     * @param key 密钥
	     * @param md5 密文
	     * @return true/false
	     * @throws Exception
	     */
	    public static boolean verifyMd5(String text, String key, String md5){
	        //根据传入的密钥进行验证
	        String md5Text = md5(text, key);
	        if(md5Text.equalsIgnoreCase(md5))
	        {
	        	log.info("MD5验证通过");
	            return true;
	        }

	            return false;
	    }
	}
