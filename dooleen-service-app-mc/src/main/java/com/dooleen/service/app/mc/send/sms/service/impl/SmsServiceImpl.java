package com.dooleen.service.app.mc.send.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import com.dooleen.service.app.mc.send.sms.service.SmsService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SmsServiceImpl implements SmsService {
	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	//获取验证码
	@Override
	public void getSmsCode(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 签名
		String smsContent = commonMsg.getBody().getSingleBody().getMsgContent();
		SmsSingleSenderResult result = new SmsSingleSenderResult();
		Map<String, String> map = new HashMap<String, String>();

		//读取三方配置获取appId, appKey
		JSONObject jsonObj = commonService.getThirdParam(commonMsg.getBody().getSingleBody().getTenantId(),"腾讯短信");

		if(jsonObj.get("smsChecked") != null && jsonObj.getBoolean("smsChecked")) {
			//获取消息模板配置
			SysMsgConfigEntity sysMsgConfigEntity =commonService.getMsgConfig(commonMsg.getBody().getSingleBody().getTenantId(),commonMsg.getBody().getSingleBody().getBizSceneName(),"短信");

			// 短信模板 ID，需要在短信应用中申请
			int templateId = Integer.parseInt(sysMsgConfigEntity.getTemplateId());
			//按照列表逐个发送
			List<SendMsgUserInfoEntity> userSendList = commonService.getUserList(commonMsg.getBody().getSingleBody().getTenantId(), commonMsg.getBody().getSingleBody().getSendAddressList());
			for (SendMsgUserInfoEntity sendMsgUserInfoEntity : userSendList) {
				try {
					SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(jsonObj.getString("appId")), jsonObj.getString("appKey"));
					String[] params = commonMsg.getBody().getSingleBody().getSmsParam().split(";");
					result = ssender.sendWithParam("86", sendMsgUserInfoEntity.getMobileNo(),
							templateId, params, jsonObj.getString("smsSign"), "", "");
					log.info(">>发送短信返回 result = " + result.toString());
					commonService.sysSysSendLog(commonMsg, smsContent, "0000",result.errMsg);
				} catch (Exception e) {

					commonMsg.getHead().setRespCode(RespStatus.errorCode);
					// 设置失败返回信息
					map.put("E6001", "短信发送失败，请稍后再试！");
					commonMsg.getHead().setRespMsg(map);
					log.error(">> 短信发送异常", e);
					commonMsg.getBody().getSingleBody().setSenderRealName(sendMsgUserInfoEntity.getRealName());
					commonService.sysSysSendLog(commonMsg, smsContent, "9000",result.errMsg);
				}
			}
		}
		else{
			commonService.sysSysSendLog(commonMsg, smsContent,"2000", "短信已关闭");
		}
	}

}