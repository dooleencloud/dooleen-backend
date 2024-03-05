package com.dooleen.service.app.gateway.filter;

import com.dooleen.service.app.gateway.conf.NotEncodeConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.dooleen.service.app.gateway.conf.NotCheckTokenUrlsPropertiesConfig;
import com.dooleen.service.app.gateway.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 *
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年5月9日 下午10:24:47
 * @Description : 启动类
 * @Author : apple
 * @Update: 2020年5月9日 下午10:24:47
 */
@Slf4j
@Component
public class AfterFilter extends ZuulFilter {
	@Autowired
	private NotCheckTokenUrlsPropertiesConfig notCheckTokenUrlsPropertiesConfig;
	@Autowired
	private NotEncodeConfig notEncodeConfig;

	// 不需要检查token的接口
	private List<String> notCheckTokenUrls;

	//不需要加密的接口
	private List<String> notEncodeUrls;

	@Autowired
	private LogService logService;

	public AfterFilter() {
	}

	public String filterType() {
		return POST_TYPE;
	}

	public int filterOrder() {
		return 1000;
	}

	public boolean shouldFilter() {
		return true;
	}
	@PostConstruct
	public void getNotCheckTokenUrls(){
		notCheckTokenUrls = new ArrayList<>();
		// 获取不检查频繁请求的接口列表
		for (String url : notCheckTokenUrlsPropertiesConfig.getAnon()) {
			notCheckTokenUrls.add(url);
		}
		log.debug("需直接放行的频繁请求接口===>{}", notCheckTokenUrls);
	}

	@PostConstruct
	public void getNotEncodeUrls(){
		notEncodeUrls = new ArrayList<>();
		// 获取不检查频繁请求的接口列表
		for (String url : notEncodeConfig.getAnon()) {
			notEncodeUrls.add(url);
		}
		log.debug("不需要加密处理的接口===>{}", notEncodeUrls);
	}
	@Override
	public Object run() {

        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            String currUri = request.getRequestURI().trim();
            log.info("==>>拦截访问日志开始start.....");
			//css ,js 等静态资源文件直接放过
			if (currUri.indexOf("/file/static") >= 0 || currUri.indexOf("/dooleenReport/ureport/res/") >= 0 ) {
				ctx.setSendZuulResponse(true); // 对请求进行路由
				return true;
			}
			// 获取token, 刷新token直接放行
			if (notCheckTokenUrls.contains(currUri) && !currUri.equals("/dooleenApi/oauth/token")) {
				ctx.setSendZuulResponse(true); // 对请求进行路由
				log.info(">>>日志拦截直接通过..."+currUri);
				return true;
			}

			// 下载excel 时直接不收集返回报文
			if (currUri.indexOf("/excelDownload/") >= 0 ) {
				this.logService.send(RequestContext.getCurrentContext(),"",false);
				ctx.setSendZuulResponse(true); // 对请求进行路由
				log.info(">>>日志拦截直接通过..."+currUri);
				return true;
			}
			log.info("==>>拦截返回数据.....");
			InputStream stream = RequestContext.getCurrentContext().getResponseDataStream();
			byte[] bytes = StreamUtils.copyToByteArray(stream);
			String responseBody = new String(bytes,"UTF8");

			//不需要加密的可以写在这里
            if (notEncodeUrls.contains(currUri)){
                this.logService.send(RequestContext.getCurrentContext(),responseBody,false);
            }
            else {
                this.logService.send(RequestContext.getCurrentContext(),responseBody,true);
            }
			RequestContext.getCurrentContext().setResponseBody(responseBody);
		} catch (Exception e) {
			log.error("ERROR ===>> 发送日志失败，请检查RabbitMQ是否正常！");
			e.printStackTrace();
        }
		log.info("==>>拦截访问日志结束end.....");
        return true;
    }
	/***
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
	 *
	 * @param request
	 * @return
	 */
	public String getClientIP(HttpServletRequest request) {
		String fromSource = "X-Real-IP";
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			fromSource = "X-Forwarded-For";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			fromSource = "Proxy-Client-IP";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			fromSource = "WL-Proxy-Client-IP";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			fromSource = "request.getRemoteAddr";
		}
		return ip;
	}

}
