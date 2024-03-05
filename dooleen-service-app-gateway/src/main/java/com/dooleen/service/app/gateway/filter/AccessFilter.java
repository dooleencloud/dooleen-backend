package com.dooleen.service.app.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.config.oauth2.properties.OAuth2Properties;
import com.dooleen.common.core.utils.*;
import com.dooleen.common.core.utils.aes.AESUtil;
import com.dooleen.service.app.gateway.conf.FrequentUrlsPropertiesConfig;
import com.dooleen.service.app.gateway.conf.NotCheckTokenUrlsPropertiesConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
/**
 * 全局错误码表
 * 439 用户ID为空
 * 440 登录过期
 * 441 请求太快
 * 442 交易签名不一致
 * 443 非令牌所有者请求
 * 444 数据签名错误
 */
@Slf4j
@Component
public class AccessFilter extends ZuulFilter {
	@Autowired
	RedisUtil redisUtil;

	@Autowired
	private OAuth2Properties oAuth2Properties;
//	@Autowired
//	private  SysApiScopeService sysApiScopeService;

	@Autowired
	private FrequentUrlsPropertiesConfig frequentUrlsPropertiesConfig;

	@Autowired
	private NotCheckTokenUrlsPropertiesConfig notCheckTokenUrlsPropertiesConfig;

	@Override
	public String filterType() {
		return "pre"; // 可以在请求被路由之前调用
	}

	@Override
	public int filterOrder() {
		return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
	}

	@Override
	public boolean shouldFilter() {
		return true; // 是否执行该过滤器，此处为true，说明需要过滤
	}

	// 需放行的频繁请求接口
	private List<String> frequentUrls;

	// 不需要检查token的接口
	private List<String> notCheckTokenUrls;

	@PostConstruct
	public void getFrequentUrls(){
		frequentUrls = new ArrayList<>();
		// 获取不检查频繁请求的接口列表
		for (String url : frequentUrlsPropertiesConfig.getAnon()) {
			frequentUrls.add(url);
		}
		log.debug("需放行的频繁请求接口===>{}", frequentUrls);
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
	@Override
	public Object run() {
		try {
			RequestContext ctx = RequestContext.getCurrentContext();

			ctx.set("startTime", System.currentTimeMillis());

			InputStream stream = ctx.getRequest().getInputStream();
			String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			HttpServletRequest request = ctx.getRequest();
			HttpServletResponse response = ctx.getResponse();
			String currUri = request.getRequestURI().trim();
			log.debug("===begin getway，url== "+currUri);

			// 获取token, 刷新token直接放行
			if (notCheckTokenUrls.contains(currUri)) {
				ctx.setSendZuulResponse(true); // 对请求进行路由
				log.info(">>>t直接通过..."+currUri);
				return true;
			}

			//css ,js 等静态资源文件直接放过
			if (currUri.indexOf("/file/static") >= 0 || currUri.indexOf("/dooleenReport/ureport/res/") >= 0 ) {
				ctx.setSendZuulResponse(true); // 对请求进行路由
//				ctx.setResponseStatusCode(200);
//				ctx.set("isSuccess", true);
				log.info(">>直接通过..."+currUri);
				return true;
			}
			BaseEntity baseBean = new BaseEntity();
			CommonMsg<BaseEntity, BaseEntity> commonMsg = CreateCommonMsg.getCommonMsg(baseBean, baseBean);
			Map<String, String> map = new HashMap<String, String>();
			String header = request.getHeader("Authorization");
			log.debug("====header===>{}", header);
			String token = "";
			if(null != header) {
				token = StringUtils.substringAfter(header, "bearer ").trim();
			}
			String userId = request.getHeader("UserId");

			String UserSign = request.getHeader("UserSign");
			String remote = request.getHeader("remote");
			String getToken = "";
			if(request.getParameter("UID") != null) {
				userId = request.getParameter("UID");
				byte[] fileIdByte = Base64.decodeBase64(userId);
				userId = new String(fileIdByte);
			}
			if(request.getParameter("remote") != null) {
				remote = request.getParameter("remote");
			}
			if(request.getParameter("UserSign") != null) {
				UserSign = request.getParameter("UserSign");
			}
			//对get请求token进行校验
			if(request.getParameter("token") != null) {
				getToken = request.getParameter("token");
			}
			// 处理导入导出
			if(("excel".equals(remote) || "no".equals(remote)) && !"".equals(getToken)) {
				token = getToken;
			}
			// 处理ureport
			if (currUri.indexOf("/dooleenReport/ureport/") > -1){
				token = getToken;
				if(null != header || token.equals("")) {
					token = StringUtils.substringAfter(header, "bearer ").trim();
				}
			}
			log.debug(remote + "===UserSign == "+UserSign);

			if (StringUtils.isBlank(userId) && "no".equals(remote)) {
				log.error("==>>用户ID为空");
				ctx.setSendZuulResponse(false); // 不对其进行路由
				ctx.setResponseStatusCode(439);
				ctx.set("isSuccess", false);
				return null;
			}
			Map userInfo = null;
			try {
				Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();
				userInfo = (Map) claims.get("userInfo");
				log.debug("====== claims = " + claims.toString());
			}
			catch(ExpiredJwtException e) {
				// 设置失败返回值
				commonMsg.getHead().setRespCode(RespStatus.errorCode);
				// 设置失败返回信息"非令牌所有者请求"
				map.put("E4004", RespStatus.json.getString("E4004"));
				commonMsg.getHead().setRespMsg(map);
				log.error("登录令牌过期！", map);
				String jsonCommonMsg = JSONObject.toJSONString(commonMsg);
				ctx.setSendZuulResponse(false); // 不对其进行路由
				ctx.setResponseStatusCode(440);
				ctx.getResponse().setCharacterEncoding("UTF-8");
				ctx.getResponse().setContentType("text/html;cahrset=UTF-8");
				ctx.setResponseBody(jsonCommonMsg);
				if(currUri.indexOf("/file/office") >= 0) { //在线文档处理！
					ctx.setResponseBody("登录令牌过期，请重新<a href='/#/login?tenantId='>登录！</a>");
				}
				ctx.set("isSuccess", false);
				return null;
			}
			catch(MalformedJwtException e) {
				// 设置失败返回值
				commonMsg.getHead().setRespCode(RespStatus.errorCode);
				// 设置失败返回信息"非令牌所有者请求"
				map.put("E4004", RespStatus.json.getString("E4004"));
				commonMsg.getHead().setRespMsg(map);
				log.error("登录令牌不合法！", map);
				String jsonCommonMsg = JSONObject.toJSONString(commonMsg);
				ctx.setSendZuulResponse(false); // 不对其进行路由
				ctx.setResponseStatusCode(445);
				ctx.getResponse().setCharacterEncoding("UTF-8");
				ctx.getResponse().setContentType("text/html;cahrset=UTF-8");
				ctx.setResponseBody(jsonCommonMsg);
				if(currUri.indexOf("/file/office") >= 0) { //在线文档处理！
					ctx.setResponseBody("登录令牌过期，请重新<a href='/#/login?tenantId='>登录！</a>");
				}
				ctx.set("isSuccess", false);
				return null;
			}
			String tokenUserId = (String)userInfo.get("userId");
			String tokenRealName = (String)userInfo.get("realName");

			// 设置返回报文头
            if(currUri.indexOf("/dooleenReport/ureport") < 0) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=utf-8");
            }
			//String host = InetAddress.getLocalHost().getHostAddress();

			// 将报文体MD5后存入redis,防止频繁请求
			String bodyMd5 =  DooleenMD5Util.md5(body, ConstantValue.md5Key);
			if(AESUtil.protectFlag()) {
				if (!frequentUrls.contains(currUri) && redisUtil.hasKey("requestMd5:" + bodyMd5) && "no".equals(remote)) {
					// 设置失败返回值
					log.error("用户" + tokenRealName + "频繁发起请求，可能被DOS攻击!={}", AESUtil.encode());
					ctx.setSendZuulResponse(false); // 不对其进行路由
					ctx.setResponseStatusCode(441);
					ctx.set("isSuccess", false);
					return null;
				} else {
					redisUtil.set("requestMd5:" + bodyMd5, "", AESUtil.protectTime());
				}
			}
			//已配置的放行接口 检查签名
			System.out.println("notCheckTokenUrls=="+notCheckTokenUrls.toString());
			if (notCheckTokenUrls.contains(currUri) && notCheckTokenUrls.toString().indexOf("/dooleenReport/ureport/res/**") == -1) {
				if (!UserSign.equals(bodyMd5)) {
					log.error("交易签名不一致", map);
					ctx.setSendZuulResponse(false); // 不对其进行路由
					ctx.setResponseStatusCode(442);
					ctx.set("isSuccess", false);
					return null;
				}
				else {
					ctx.setSendZuulResponse(true); // 对请求进行路由
//					ctx.setResponseStatusCode(200);
//					ctx.set("isSuccess", true);
					log.info(">>直接通过..." + currUri);
					return true;
				}
			}

			// 比较是否获取token人的请求：
			if (!tokenUserId.equals(userId)) {
				log.error("非令牌所有者请求", map);
				ctx.setSendZuulResponse(false); // 不对其进行路由
				ctx.setResponseStatusCode(443);
				ctx.set("isSuccess", false);
				return null;
			}

			if (!bodyMd5.equals(UserSign) && !"uploadFile".equals(UserSign) && currUri.indexOf("/api/v3") < 0) {
				log.error("数据签名错误", map);
				ctx.setSendZuulResponse(false); // 不对其进行路由
				ctx.setResponseStatusCode(444);
				ctx.set("isSuccess", false);
				return null;
			}

			// 检查接口权限
//			SysApiScopeEntity   sysApiScopeEntity = new SysApiScopeEntity();
//			sysApiScopeEntity.setTenantId(tokenTenantId);
//			sysApiScopeEntity.setId(userId);
//			sysApiScopeEntity.setInterfaceAddress(currUri);
//			NullEntity nullEntity = new NullEntity();
//			CommonMsg<SysApiScopeEntity, NullEntity> apiCommonMsg = CreateCommonMsg.getCommonMsg(sysApiScopeEntity, nullEntity);
//			if(AESUtil.encode()) {
//				String commonMsgStr = "";
//				JSONObject comObj = new JSONObject();
//				log.info("====AESUtil === {}", AESUtil.encode());
//				try {
//					commonMsgStr = AESUtil.encrypt(JSON.toJSONString(apiCommonMsg));
//					commonMsgStr = sysApiScopeService.querySysApiScopeByUserIdAndAddress(commonMsgStr);
//					comObj = JSON.parseObject(AESUtil.decrypt(commonMsgStr));
//					apiCommonMsg = JSON.parseObject(AESUtil.decrypt(commonMsgStr), new TypeReference<CommonMsg<SysApiScopeEntity, NullEntity>>(){});
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				SysApiScopeEntity  sysUserInfo  = JSON.parseObject(comObj.getJSONObject("body").get("singleBody").toString(),SysApiScopeEntity.class);
//			}
//			else {
//				apiCommonMsg = sysApiScopeService.querySysApiScopeByUserIdAndAddress(apiCommonMsg);
//			}
//			log.info("apiCommonMsg====="+ apiCommonMsg.getHead().getRespCode());
//			if(apiCommonMsg.getHead().getRespCode().equals("S0000")) {
//				// 设置失败返回值
//				commonMsg.getHead().setRespCode(RespStatus.errorCode);
//				// 设置失败返回信息
//				map.put("E4006", RespStatus.json.getString("E4006"));
//				commonMsg.getHead().setRespMsg(map);
//				log.error("没有接口访问权限！——"+currUri, map);
//
//				String jsonCommonMsg = JSONObject.toJSONString(commonMsg);
//				ctx.setSendZuulResponse(false); // 不对其进行路由
//				ctx.setResponseStatusCode(200);
//				if (AESUtil.encode()) {
//						jsonCommonMsg = AESUtil.encrypt(jsonCommonMsg);
//					}
//				ctx.setResponseBody(jsonCommonMsg);
//				ctx.set("isSuccess", false);
//				return null;
//			}
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("get server host Exception e:", e);
			return null;
		}
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
