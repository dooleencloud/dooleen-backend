package com.dooleen.common.core.config.http;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
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
@Configuration
public class HttpPool {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    @Primary
    public HttpClient httpClient(){
    	logger.info("===========================init feign httpclient configuration ===========================" );
        // 生成默认请求配置
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        // 超时时间
        requestConfigBuilder.setSocketTimeout(5 * 2000);
        // 连接时间
        requestConfigBuilder.setConnectTimeout(5 * 2000);
        RequestConfig defaultRequestConfig = requestConfigBuilder.build();

//        RequestConfig defaultRequestConfig = RequestConfig.custom()
////                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
////                .setExpectContinueEnabled(true)
////                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
////                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
//                .setSocketTimeout(5000)
//                .setConnectTimeout(3000).build();

        // 连接池配置
        // 长连接保持30秒
        final PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.MILLISECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(5000);
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(500);

        // httpclient 配置
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        HttpClient client = httpClientBuilder.build();

        // 启动定时器，定时回收过期的连接
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	logger.debug("===========================定时回收过期连接池===========================");
                pollingConnectionManager.closeExpiredConnections();
                pollingConnectionManager.closeIdleConnections(5, TimeUnit.SECONDS);
            }
        }, 10 * 2000, 5 * 2000); 
        logger.info("===========================feign httpclient 初始化连接池完成===========================");

        return client;
    }


}
