package com.dooleen.common.core.wechat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * nonce_str随即字符串
 * @author iYjrg_xiebin
 * @date 2015年11月25日下午5:10:32
 */
public class RandCharsUtils {
	private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat df2 = new SimpleDateFormat("ss");
	public static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";   
		Random random = new Random();   
		StringBuffer sb = new StringBuffer();
		int number = 0;
		for (int i = 0; i < length; i++) {   
			number = random.nextInt(base.length());   
			sb.append(base.charAt(number));   
		}   
		return sb.toString();   
	}   
	
	/*
	 * 订单开始交易的时间
	 */
	public static String timeStart(){
		return df.format(new Date());
	}
	
	/*
	 * 订单开始交易的时间
	 */
	public static String timeExpire(){
		Calendar now=Calendar.getInstance();
		now.add(Calendar.MINUTE,30);
		return df.format(now.getTimeInMillis());
	}


	public static void main(String[] args) throws ParseException {
		/*for (int i = 0; i < 10; i++) {
			System.out.println("第"+i+"次是："+getRandomString(32));
		}*/

		System.out.println("开始时间是："+df.parse(df.format(new Date())).getTime());
		System.out.println("失效时间是："+df.parse("20180103171230").getTime());
		long result = df.parse("20180103203530").getTime() - df.parse(timeStart()).getTime();
				//df.parse(timeExpire()).getTime() - df.parse(timeStart()).getTime();
		
		System.out.println("相差时间是："+result/3600);

	}
	

}