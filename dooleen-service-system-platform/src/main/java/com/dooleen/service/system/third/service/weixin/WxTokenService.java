package com.dooleen.h5.service;

import com.dooleen.common.core.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Copy Right Information : 独领
 * @Project : 独领教育平台
 * @Project No :
 * @Description :
 * @Version : 1.0.0
 * @Since : 1.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Author : liqh
 * @Maintainer:
 * @Update:
 */
@Service
@Slf4j
public class WxTokenService {
	// 获取微信TOKEN
	public String getToken() {
		UrlUtil HttpUtil = new UrlUtil();
		String appId = "wx07c371e395d9af61";
		String secret = "27f2624e88734932a9c12727b353627a";
		log.info(">>> 开始获取getToken。。。。");
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
		String params = "grant_type=client_credential&" + "appid="+appId+"&secret="+secret;
		String data = HttpUtil.get(requestUrl, params);
		// 解析相应内容（转换成json对象）
		JSONObject json = JSONObject.parseObject(data);
		System.out.println("微信返回json:{}"+json);
		// 用户的唯一标识（openid）
		if (null != json.get("access_token")) {

		}
		return "";
	}

	public static void  main(String[] args){
		new WxTokenService().getToken();
	}
}