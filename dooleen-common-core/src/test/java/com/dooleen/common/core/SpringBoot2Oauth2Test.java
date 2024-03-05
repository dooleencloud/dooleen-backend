package com.dooleen.common.core;


import org.apache.commons.codec.binary.Base64;

/**
 * Created on 2018/4/28.
 *
 * @author zlf
 * @since 1.0
 */

public class SpringBoot2Oauth2Test {
    //端口
    final static long PORT = 8888;
    //clientId
    final static String CLIENT_ID = "dooleen";
    //clientSecret
    final static String CLIENT_SECRET = "dooleen";
    //用户名
    final static String USERNAME = "admin";
    //密码
    final static String PASSWORD = "123456";
    //获取accessToken得URI
    final static String TOKEN_REQUEST_URI = "http://127.0.0.1:" + PORT + "/oauth/token?grant_type=password&username=" + USERNAME + "&password=" + PASSWORD + "&scope=all";
    //获取用户信息得URL
    final static String USER_INFO_URI = "http://127.0.0.1:" + PORT + "/userRedis";
    //登录地址
    final static String SIGN_IN_URI = "http://127.0.0.1:" + PORT + "/login";

   
    public static void main(String[] args) {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        System.out.println("authorization = "+authHeader);

    }
}
