package com.dooleen.service.file.wps.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Map;

@Slf4j
public class HttpUtil {

    /**
     * HTTP GET请求
     * 支持 header
     * 在POSTMAN中可模拟，其中Headers为请求中的header参数，params为请求地址中的参数
     */
    public static String get(String uri, Map<String, String> headers) {
        String res = "";
        try {
            // 创建HttpClient实例
            HttpClient client = HttpClientBuilder.create().build();
            // 根据URL创建HttpGet实例
            HttpGet get = new HttpGet(uri);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry != null && entry.getKey() != null) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 执行get请求，得到返回体
            HttpResponse response = client.execute(get);
            // 判断是否正常返回
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 解析数据
                res = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static String post(String uri, Map<String, String> headers, String paramsJson) {
        String res = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(uri);
            //解决中文乱码问题
            StringEntity entity = new StringEntity(paramsJson, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType(Common.CONTENTTYPE);
            post.setEntity(entity);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry != null && entry.getKey() != null) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse result = client.execute(post);
            /*请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                res = EntityUtils.toString(result.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
