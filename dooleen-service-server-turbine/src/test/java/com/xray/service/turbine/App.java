package com.dooleen.service.turbine;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
	 
	public static void main(String[] args) {
		String str;
		try {
			str = Base64.getEncoder().encodeToString("123456".getBytes("utf-8"));
			System.out.println(str);
			
			byte[] bytes = Base64.getDecoder().decode(str);
			System.out.println(new String(bytes, "utf-8"));
			
			
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			    Date date=new Date();
			    date.setTime(0);
			    System.out.println("dater==="+sdf.format(date));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
