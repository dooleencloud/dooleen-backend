package com.dooleen.service.app.mc.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.biz.msg.config.entity.SysBizMsgConfigEntity;
import com.dooleen.common.core.app.system.biz.msg.config.service.SysBizMsgConfigService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.constant.MqQueueConstant;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import com.dooleen.service.app.mc.send.dingtalk.service.DingtalkService;
import com.dooleen.service.app.mc.send.noticeMsg.NoticeMsgService;
import com.dooleen.service.app.mc.send.push.service.PushAppMsgService;
import com.dooleen.service.app.mc.send.sms.service.SmsService;
import com.dooleen.service.app.mc.send.wxmp.service.WxmpService;
import com.dooleen.service.app.mc.send.wxmp.service.impl.WxmpServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zoujin
 * @date 2020/7/14
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.PUSH_APP_MESSAGE)
public class PushAppMessageListener {
	@Autowired
	private PushAppMsgService pushAppMsgService;

	@Autowired
	private SmsService smsService;

	@Autowired
	private WxmpService wxmpService;

	@Autowired
	private DingtalkService dingtalkService;

	@Autowired
	private NoticeMsgService noticeMsgService;

	@Autowired
	private SysBizMsgConfigService sysBizMsgConfigService;

	@Autowired
	private CommonService commonService;
	@RabbitHandler
    public void receive(SysSendLogEntity sysSendLogEntity) {
		log.debug("=====>> 开始消费消息"+sysSendLogEntity.toString());
		sysSendLogEntity.setDataSign(DooleenMD5Util.md5(sysSendLogEntity.toString(),  ConstantValue.md5Key));
		CommonMsg<SysSendLogEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(sysSendLogEntity, new NullEntity());
		commonMsg.getHead().setTenantId(sysSendLogEntity.getTenantId());
		commonMsg.getHead().setTransCode("SysSendLog");
		commonMsg.getHead().setRealName(sysSendLogEntity.getSenderRealName());
		commonMsg.getHead().setUserName(sysSendLogEntity.getSenderId());
		//wxmpService.sendWxmpMsg1(commonMsg);
		//读取业务消息配置表，对不同的业务组装消息
		SysBizMsgConfigEntity sysBizMsgConfigEntity = new SysBizMsgConfigEntity();
		sysBizMsgConfigEntity.setTenantId(sysSendLogEntity.getTenantId());
		sysBizMsgConfigEntity.setBizName(sysSendLogEntity.getBizSceneName());
		CommonMsg<SysBizMsgConfigEntity, NullEntity> bizMsgCommonMsg = CreateCommonMsg.getCommonMsg(sysBizMsgConfigEntity, new NullEntity());
		try {
    		bizMsgCommonMsg = sysBizMsgConfigService.querySysBizMsgConfigByBizName(bizMsgCommonMsg);
			if(bizMsgCommonMsg == null){
				log.error("==>>未读到业务短信配置...");
			}
			JSONArray jsonArray = JSONObject.parseArray(bizMsgCommonMsg.getBody().getSingleBody().getMsgTypeList());
			String returnMsg ="";
			for (Object arrayObj: jsonArray) {
				sysSendLogEntity.setMsgType(arrayObj.toString());
				sysSendLogEntity.setSendChannel(arrayObj.toString());
				commonMsg.getBody().setSingleBody(sysSendLogEntity);
				if(arrayObj.equals("短信验证码")){
					//解析处理
					smsService.getSmsCode(commonMsg);
					returnMsg +="发送短信验证码;";
				}
				else if(arrayObj.equals("个推")){
					//解析处理

					pushAppMsgService.pushAppMsg(commonMsg);
					returnMsg +="APP消息推送;";
				}
				else if(arrayObj.equals("微信公众号")){
					//解析处理
					wxmpService.sendWxmpMsg(commonMsg);
					returnMsg +="推送公众号消息;";
				}
				else if(arrayObj.equals("钉钉消息")){
					//解析处理
					dingtalkService.sendDingtalkMsg(commonMsg);
					returnMsg +="钉钉消息;";
				}
				else if(arrayObj.equals("邮件")){
					//todo
					returnMsg +="推送邮件消息;";
				}
				else if(arrayObj.equals("站内消息")){
					noticeMsgService.sendNoticeMsg(commonMsg);
					returnMsg +="站内消息;";
				}
			}
			if(StringUtil.isEmpty(returnMsg)){
				returnMsg = "没有配置发送渠道";
			}
			//写发送日志
			commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(),"0000", returnMsg);
		} catch (Exception e) {
			//写发送日志
			commonService.sysSysSendLog(commonMsg, commonMsg.getBody().getSingleBody().getMsgTitle(), "9000","消息发送异常！");
			log.error("写系统日志报错：{}", e.getMessage());
		}
    }
}
