package com.dooleen.service.app.mc.send.wxmp.service;

import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


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
public interface WxmpService {
	public void sendWxmpMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);
}