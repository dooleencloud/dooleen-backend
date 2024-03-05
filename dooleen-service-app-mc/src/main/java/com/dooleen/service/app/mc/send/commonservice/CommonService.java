package com.dooleen.service.app.mc.send.commonservice;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;

import java.util.List;


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
public interface CommonService {
	List<SendMsgUserInfoEntity> getUserList(String tenantId, String sendAddressList);


	void sysSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg, String smsContent,String status,  String errMsg);

	JSONObject getThirdParam(String tenantId, String type);

    SysMsgConfigEntity getMsgConfig(String tenantId, String sendScene, String msgType);

	void  updateUserDingtalkUserId(String id, String dingTalkUserId);

    String  writeNoticeMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg, SendMsgUserInfoEntity userInfo);
}