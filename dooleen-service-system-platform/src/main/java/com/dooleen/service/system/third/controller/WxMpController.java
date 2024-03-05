package com.dooleen.service.system.third.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.app.system.third.service.SysThirdPartyInfoService;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.ValidationUtils;
import com.dooleen.common.core.wechat.utils.WeixinCheckoutUtil;
import com.dooleen.common.core.wechat.vo.WXMessgeEntity;
import com.dooleen.service.system.user.info.service.SysUserInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-18 15:47:33
 * @Description : 第三方配置管理Controller
 * @Author : apple
 * @Update: 2020-08-18 15:47:33
 */
@Slf4j
@RestController
@Api(tags = "第三方配置管理相关接口")
@RequestMapping("/platform/third/wx")
public class WxMpController {
	@Autowired
	private SysUserInfoService sysUserInfoService;
	@Autowired
	private SysThirdPartyInfoService sysThirdPartyInfoService;
	/**
	 * 公众号配置回调校验
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wxCallback/{tenantId}", method = RequestMethod.GET)
	public String wxCallbackGet(@PathVariable String tenantId,HttpServletRequest request, HttpServletResponse response) {
		log.info("====>>开始公众号配置回调");
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		//读取三方配置获取token
		SysThirdPartyInfoEntity sysThirdPartyInfoEntity = new SysThirdPartyInfoEntity();
		sysThirdPartyInfoEntity.setTenantId(tenantId);
		sysThirdPartyInfoEntity.setType("微信公众号");
		CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(sysThirdPartyInfoEntity, new NullEntity());
		commonMsg = sysThirdPartyInfoService.querySysThirdPartyInfoByType(commonMsg);
		String content = commonMsg.getBody().getSingleBody().getThirdPartyConfigContent();
		JSONObject jsonObj = JSON.parseObject(content);
		try {
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
			if (WeixinCheckoutUtil.checkSignature(signature, timestamp, nonce,jsonObj.getString("token"))) {
				log.info("微信加密签名:" + signature + ";时间戳:" + timestamp + ";随机数:" + nonce);
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
		return null;
	}
	/**
	 * 微信事件消息回调接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wxCallback/{tenantId}", method = RequestMethod.POST)
	public String wxCallback(@PathVariable String tenantId, HttpServletRequest request, HttpServletResponse response  ) {
		try {
			Marshaller marshaller;
			Unmarshaller unmarshal;
			//你要解析成哪个bean对象，newInstance的参数就是哪个对象
			JAXBContext jaxbContext = JAXBContext.newInstance(WXMessgeEntity.class);
			unmarshal = jaxbContext.createUnmarshaller();
			//xml解码成bean对象
			WXMessgeEntity wxMessgeEntity = (WXMessgeEntity) unmarshal.unmarshal(request.getInputStream());

			System.out.println("===>> 开始回调...");
			if ("text".equals(wxMessgeEntity.getMsgType())) {
				String replayMsg = "";
				WXMessgeEntity bean = new WXMessgeEntity();
				if(!ValidationUtils.isMobile(wxMessgeEntity.getContent().trim())){
					replayMsg = "请输入正确的手机号码！";
				}
				else {
					// 更新用户信息
					SysUserInfoEntity sysUserInfoEntity = new SysUserInfoEntity();
					sysUserInfoEntity.setTenantId(tenantId);
					sysUserInfoEntity.setMobileNo(wxMessgeEntity.getContent().trim());
					CommonMsg<SysUserInfoEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(sysUserInfoEntity, new NullEntity());
					try {
						log.debug("====>>开始获取用户信息");
						commonMsg = sysUserInfoService.querySysUserInfoByMobile(commonMsg);

						//更新用户openID
						commonMsg.getBody().getSingleBody().setWxOpenId(wxMessgeEntity.getFromUserName());
						log.debug("====>>开始更新用户 OpenId="+wxMessgeEntity.getFromUserName());
						sysUserInfoService.updateSysUserInfo(commonMsg);
						replayMsg = "恭喜您，手机号绑定成功，绑定用户："+commonMsg.getBody().getSingleBody().getRealName();
					} catch (Exception e) {
						if(e.toString().indexOf("E1001")>0){
							replayMsg = "用户不存在！";
						}
						else{
							replayMsg = "处理异常请联系管理员！";
						}
					}
				}
				bean.setFromUserName(wxMessgeEntity.getToUserName());
				bean.setToUserName(wxMessgeEntity.getFromUserName());
				bean.setCreateTime(new Date().getTime());
				bean.setMsgType("text");
				bean.setContent(replayMsg);
				marshaller = jaxbContext.createMarshaller();
				StringWriter writer = new StringWriter();
				marshaller.marshal(bean, writer);
				return writer.toString();
			}
			else if ("event".equals(wxMessgeEntity.getMsgType()) && "subscribe".equals(wxMessgeEntity.getEvent())) {
				//读取三方配置获取token
				SysThirdPartyInfoEntity sysThirdPartyInfoEntity = new SysThirdPartyInfoEntity();
				sysThirdPartyInfoEntity.setTenantId(tenantId);
				sysThirdPartyInfoEntity.setType("微信公众号");
				CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(sysThirdPartyInfoEntity, new NullEntity());
				commonMsg = sysThirdPartyInfoService.querySysThirdPartyInfoByType(commonMsg);
				String content = commonMsg.getBody().getSingleBody().getThirdPartyConfigContent();
				JSONObject jsonObj = JSON.parseObject(content);

				String replayMsg = "感谢关注！";
				if(!StringUtil.isEmpty(jsonObj.getString("notice"))){
					replayMsg = jsonObj.getString("notice");
				}
				WXMessgeEntity bean = new WXMessgeEntity();
				bean.setFromUserName(wxMessgeEntity.getToUserName());
				bean.setToUserName(wxMessgeEntity.getFromUserName());
				bean.setCreateTime(new Date().getTime());
				bean.setMsgType("text");
				bean.setContent(replayMsg);
				marshaller = jaxbContext.createMarshaller();
				StringWriter writer = new StringWriter();
				marshaller.marshal(bean, writer);
				return writer.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}