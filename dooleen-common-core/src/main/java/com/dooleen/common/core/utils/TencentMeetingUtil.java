package com.dooleen.common.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TencentMeetingUtil {

    private static final String SECRET_ID = "zcyOACohnHGNq4YbDluXj5LVf0xsFUgKrQp9";
    private static final String SECRET_KEY = "2B7OcA8dMEoTsPjGNUSgnIfetRyz0hQa";
    private static final String APP_ID="253029876";
    private static final String SDK_ID="2008120773";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	// 获取用户列表
//    	String uri = "/v1/users/list?page=1&page_size=20";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	getUserList(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url);
    	
    	// 获取用户详情
//    	String uri = "/v1/users/DOOL100100000170";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	getUserDetail(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url);
    	
    	// 创建用户
//        String uri="/v1/users";
//        String url="https://api.meeting.qq.com" + uri;
//        String reqBody = "{"+
//                "\"username\": \"zhangsan\", "+
//                "\"phone\" : \"18333333333\", "+
//                "\"userid\": \"DOOL100100000170\", " +
//                "\"email\": \"1016295574@qq.com\"" +
//                "}";
//        createUser(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url, reqBody);
    	
    	// 更新用户信息
//    	String uri = "/v1/users/DOOL100100000170";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	String reqBody = "{"
//    			+ "\"email\": \"2524771874@qq.com\""
//    			+ "}";
//    	updateUser(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url, reqBody);
    	
    	// 删除用户
//    	String uri = "/v1/users/DOOL100100000170";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	deleteUser(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url);
    	
    	System.out.println("12345678910000".substring(0, 10));
    	
    	// 创建会议
//        String uri="/v1/meetings";
//        String url="https://api.meeting.qq.com" + uri;
//        
//        Date today=  new  Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(new  Date());//今天
//        c.add(Calendar.DAY_OF_MONTH,1);
//        Date tomorrow=c.getTime();//这是明天
//        int createTime = getSecondTimestamp(today);
//        int createtomorrow = getSecondTimestamp(tomorrow);
//        
//        String reqBody = "{\r\n" + 
//        		"     \"userid\": \"123456789\",\r\n" + 
//        		"     \"instanceid\": 3,\r\n" + 
//        		"     \"subject\": \"日常会议\",\r\n" + 
//        		"     \"type\": 0,\r\n" + 
//        		"     \"hosts\": [\r\n" + 
//        		"          {\r\n" + 
//        		"               \"userid\": \"123456789\"\r\n" + 
//        		"          }\r\n" + 
//        		"     ],\r\n" + 
//        		"     \"start_time\": \"" + createTime + "\",\r\n" + 
//        		"     \"end_time\": \"" + createtomorrow + "\",\r\n" + 
//        		"     \"invitees\": [\r\n" + 
//        		"          {\r\n" + 
//        		"               \"userid\": \"123456789\"\r\n" + 
//        		"          }\r\n" + 
//        		"     ],\r\n" + 
//        		"     \"settings \": {\r\n" + 
//        		"          \"mute_enable_join \": true,\r\n" + 
//        		"          \"allow_unmute_self \": false,\r\n" + 
//        		"          \"mute_all \": false,\r\n" + 
//        		"          \"host_video \": true,\r\n" + 
//        		"          \"participant_video \": false,\r\n" + 
//        		"          \"enable_record \": false,\r\n" + 
//        		"          \"play_ivr_on_leave\": false,\r\n" + 
//        		"          \"play_ivr_on_join\": false,\r\n" + 
//        		"          \"live_url\": false\r\n" + 
//        		"     }\r\n" + 
//        		"}";
//        createMeeting(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url, reqBody);
    	
    	// 查询用户会议列表
//    	String uri = "/v1/meetings?userid=123456789&instanceid=1";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	getUserMeetingList(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url);
    	
    	// 通过会议 ID 查询
//    	String uri = "/v1/meetings/2950784254465143992?userid=DOOL100100000170&instanceid=1";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	getMeetingById(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url);
    	
    	// 通过会议 code 查询
//    	String uri = "/v1/meetings?meeting_code=175221423&userid=DOOL100100000171&instanceid=2";
//    	String url = "https://api.meeting.qq.com" + uri;
//    	getMeetingByCode(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url);
    	
    	// 取消会议
//    	String uri = "/v1/meetings/2950784254465143992/cancel";
//    	String url = "https://api.meeting.qq.com" + uri;
//        String reqBody = "{"+
//        	      "\"userid\": \"DOOL100100000170\", "+
//        	      "\"instanceid\" : 1, "+
//        	      "\"reason_code\": 1, " +
//        	      "\"reason_detail\": \"取消会议\"" +
//        	      "}";
//    	cancelMeeting(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url, reqBody);
    	
    	// 结束会议
//    	String uri = "/v1/meetings/2950784254465143992/dismiss";
//    	String url = "https://api.meeting.qq.com" + uri;
//        String reqBody = "{"+
//        	      "\"userid\": \"DOOL100100000170\", "+
//        	      "\"instanceid\" : 1, "+
//        	      "\"reason_code\": 3, " +
//        	      "\"reason_detail\": \"结束会议，11111\"" +
//        	      "}";
//    	terminatedMeeting(SECRET_ID, SECRET_KEY, APP_ID, SDK_ID, uri, url, reqBody);
	}
    
    /**
     * 获取用户列表
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     */
    public static String getUserList(String secretId, 
    								 String secretKey, 
    								 String appId, 
    								 String sdkId,
    								 String uri,
    								 String url) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		log.info("========================getUserList start======================");
		
		String result = request(secretId, secretKey, appId, sdkId, uri, url, "", "GET");
    	
        log.info("========================getUserList end======================");
        return result;
	}
    
    /**
     * 获取用户详情
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     */
    public static String getUserDetail(String secretId, 
    								   String secretKey, 
    								   String appId, 
    								   String sdkId,
    								   String uri,
    								   String url) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		log.info("========================getUserDetail start======================");
		
		String result = request(secretId, secretKey, appId, sdkId, uri, url, "", "GET");
    	
        log.info("========================getUserDetail end======================");
        return result;
	}
    
    /**
     * 创建用户
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     * @param reqBody
     */
    public static String createUser(String secretId, 
									String secretKey, 
									String appId, 
									String sdkId,
									String uri,
									String url,
									String reqBody) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================createUser end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, reqBody, "POST");
    	
        log.info("========================createUser end======================");
        return result;
    }
    
    /**
     * 更新用户
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     * @param reqBody
     */
    public static String updateUser(String secretId, 
									String secretKey, 
									String appId, 
									String sdkId,
									String uri,
									String url,
									String reqBody) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================updateUser end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, reqBody, "PUT");

        log.info("========================updateUser end======================");
        return result;
    }
    
    /**
     * 删除用户
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     * @param reqBody
     */
    public static String deleteUser(String secretId, 
									String secretKey, 
									String appId, 
									String sdkId,
									String uri,
									String url) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================deleteUser end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, "", "PUT");
    	
        log.info("========================deleteUser end======================");
        return result;
    }
    
    /**
     * 创建会议
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     * @param reqBody
     */
    public static String createMeeting(String secretId, 
									   String secretKey, 
									   String appId, 
									   String sdkId,
									   String uri,
									   String url,
									   String reqBody) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================createMeeting end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, reqBody, "POST");
    	
        log.info("========================createMeeting end======================");
        return result;
	}
    
    /**
     * 查询用户的会议列表
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     */
    public static String getUserMeetingList(String secretId, 
									   		String secretKey, 
									   		String appId, 
										    String sdkId,
										    String uri,
										    String url) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================getUserMeetingList end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, "", "GET");
    	
        log.info("========================getUserMeetingList end======================");
        return result;
	}
    
    /**
     * 通过会议 ID 查询
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     */
    public static String getMeetingById(String secretId, 
									   	String secretKey, 
									   	String appId, 
										String sdkId,
										String uri,
										String url) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================getMeetingById end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, "", "GET");
    	
        log.info("========================getMeetingById end======================");
        return result;
	}
    
    /**
     * 通过会议 code 查询
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     */
    public static String getMeetingByCode(String secretId, 
									   	  String secretKey, 
									   	  String appId, 
										  String sdkId,
										  String uri,
										  String url) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================getMeetingByCode end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, "", "GET");
    	
        log.info("========================getMeetingByCode end======================");
        return result;
	}
    
    /**
     * 取消会议
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     * @param reqBody
     */
    public static String cancelMeeting(String secretId, 
									       String secretKey,
									       String appId, 
									       String sdkId,
									       String uri,
									       String url,
									       String reqBody) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================cancelMeeting end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, reqBody, "POST");
    	
        log.info("========================cancelMeeting end======================");
        return result;
	}
    
    /**
     * 结束会议
     * @param secretId
     * @param secretKey
     * @param appId
     * @param sdkId
     * @param uri
     * @param url
     * @param reqBody
     */
    public static String terminatedMeeting(String secretId, 
									       String secretKey,
									       String appId, 
									       String sdkId,
									       String uri,
									       String url,
									       String reqBody) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("========================terminatedMeeting end======================");
    	
    	String result = request(secretId, secretKey, appId, sdkId, uri, url, reqBody, "POST");
    	
        log.info("========================terminatedMeeting end======================");
        return result;
	}
    
    public static String request(String secretId, 
								 String secretKey, 
								 String appId, 
								 String sdkId,
								 String uri,
								 String url,
								 String reqBody,
								 String method) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		// 时间戳
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		
		// 随机数
		String nonce=String.valueOf(new Random().nextInt(java.lang.Integer.MAX_VALUE));
		
        //生成签名
        String signature = sign(secretId, secretKey, method, nonce, timestamp, uri, reqBody);
        String result = "";
        switch (method) {
        case "POST":			
			result = doPost(url, secretId, timestamp, nonce, appId, signature, reqBody, sdkId);
			break;
		case "GET":			
			result = doGet(url, secretId, timestamp, nonce, appId, signature, reqBody, sdkId);
			break;
		case "PUT":
			result = doPut(url, secretId, timestamp, nonce, appId, signature, reqBody, sdkId);
			break;
		case "DELETE":
			result = doDelete(url, secretId, timestamp, nonce, appId, signature, reqBody, sdkId);
			break;
		default:
			break;
		}
        return result;
	}
    
    /**
     * post请求
     * @param url
     * @param secretId
     * @param timestamp
     * @param nonce
     * @param appId
     * @param mkString
     * @param req_body
     * @param skid_ID
     */
	public static String doPost(String url, 
								String secretId, 
								String timestamp,
								String nonce,
								String appId,
								String mkString,
								String req_body,
								String skidId){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String jsonStr = "";
		try {
			httpPost.setHeader("X-TC-Key",secretId);
			httpPost.setHeader("AppId", appId);
			httpPost.setHeader("SdkId", skidId);
			httpPost.setHeader("X-TC-Nonce",nonce);
			httpPost.setHeader("X-TC-Timestamp",timestamp);
			httpPost.setHeader("X-TC-Signature", mkString);
			httpPost.setHeader("content-type", "application/json");
			
//			httpPost.setHeader("X-TC-Registered", "1");
//			httpPost.setHeader("Body",req_body);
			httpPost.setEntity(new StringEntity(req_body, StandardCharsets.UTF_8));
			
			CloseableHttpResponse httpResponse = null;
			httpResponse = httpClient.execute(httpPost); 
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {  
				jsonStr = EntityUtils.toString(httpEntity,"UTF-8");
            } 
			log.debug("doPost====>返回:{}", jsonStr);
		} catch (IOException e) {
		}finally{
			httpPost.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
		return jsonStr;
	}
	
    /**
     * get请求
     * @param url
     * @param secretId
     * @param timestamp
     * @param nonce
     * @param appId
     * @param mkString
     * @param req_body
     * @param skid_ID
     */
	public static String doGet(String url, 
								String secretId, 
								String timestamp,
								String nonce,
								String appId,
								String mkString,
								String req_body,
								String skidId){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		String jsonStr = "";
		try {
			httpGet.setHeader("X-TC-Key",secretId);
			httpGet.setHeader("AppId", appId);
			httpGet.setHeader("SdkId", skidId);
			httpGet.setHeader("X-TC-Nonce",nonce);
			httpGet.setHeader("X-TC-Timestamp",timestamp);
			httpGet.setHeader("X-TC-Signature", mkString);
			httpGet.setHeader("content-type", "application/json");
			
			CloseableHttpResponse httpResponse = null;
			httpResponse = httpClient.execute(httpGet); 
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {  
				jsonStr = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            } 
			log.debug("doGet====>返回:{}", jsonStr);
		} catch (IOException e) {
		}finally{
			httpGet.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
		return jsonStr;
	}
	
    /**
     * put请求
     * @param url
     * @param secretId
     * @param timestamp
     * @param nonce
     * @param appId
     * @param mkString
     * @param req_body
     * @param skid_ID
     */
	public static String doPut(String url, 
								String secretId, 
								String timestamp,
								String nonce,
								String appId,
								String mkString,
								String req_body,
								String skidId){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		String jsonStr = "";
		try {
			httpPut.setHeader("X-TC-Key",secretId);
			httpPut.setHeader("AppId", appId);
			httpPut.setHeader("SdkId", skidId);
			httpPut.setHeader("X-TC-Nonce",nonce);
			httpPut.setHeader("X-TC-Timestamp",timestamp);
			httpPut.setHeader("X-TC-Signature", mkString);
			httpPut.setHeader("content-type", "application/json");
			
			httpPut.setEntity(new StringEntity(req_body, StandardCharsets.UTF_8));
			
			CloseableHttpResponse httpResponse = null;
			httpResponse = httpClient.execute(httpPut); 
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {  
				jsonStr = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            } 
			log.debug("doPut====>返回:{}", jsonStr);
		} catch (IOException e) {
		}finally{
			httpPut.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
		return jsonStr;
	}
	
    /**
     * delete请求
     * @param url
     * @param secretId
     * @param timestamp
     * @param nonce
     * @param appId
     * @param mkString
     * @param req_body
     * @param skid_ID
     */
	public static String doDelete(String url, 
								  String secretId, 
								  String timestamp,
								  String nonce,
								  String appId,
								  String mkString,
								  String req_body,
								  String skidId){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		String jsonStr = "";
		try {
			httpDelete.setHeader("X-TC-Key",secretId);
			httpDelete.setHeader("AppId", appId);
			httpDelete.setHeader("SdkId", skidId);
			httpDelete.setHeader("X-TC-Nonce",nonce);
			httpDelete.setHeader("X-TC-Timestamp",timestamp);
			httpDelete.setHeader("X-TC-Signature", mkString);
			httpDelete.setHeader("content-type", "application/json");
			
			CloseableHttpResponse httpResponse = null;
			httpResponse = httpClient.execute(httpDelete); 
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {  
				jsonStr = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            } 
			log.debug("doDelete====>返回:{}", jsonStr);
		} catch (IOException e) {
		}finally{
			httpDelete.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
		return jsonStr;
	}
    
	/**
	 * 签名
	 * @param secretId
	 * @param secretKey
	 * @param method
	 * @param nonce
	 * @param timestamp
	 * @param uri
	 * @param requestBody
	 */
    public static String sign(String secretId, String secretKey, String method, String nonce, String timestamp, String uri,String requestBody)
        throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String sig="";
        try{
        	String HTTPMethod = method;
        	String Headers = "X-TC-Key=" + secretId + "&X-TC-Nonce=" + nonce + "&X-TC-Timestamp=" + timestamp;
        	String URI = uri;
        	String Params = requestBody;
        	String tobeSig = HTTPMethod + "\n" +    
		        			Headers + "\n" +       
		        			URI + "\n" +           
		        			Params ;     
        			
            log.debug("tobeSig：{}", tobeSig);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(tobeSig.getBytes(StandardCharsets.UTF_8));
            String hexHash = bytesToHex(hash);
            return new String(Base64.getEncoder().encode(hexHash.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return sig;
    }
    
    public static String bytesToHex(byte[] bytes) {
    	 
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
 
        return new String(buf);
    }
    
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }
}
