package com.dooleen.service.app.mc.send.sms.service;

import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.*;


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
public interface SmsService{
	public void getSmsCode(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);
}