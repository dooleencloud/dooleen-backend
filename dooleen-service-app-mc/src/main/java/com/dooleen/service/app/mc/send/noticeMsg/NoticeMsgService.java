package com.dooleen.service.app.mc.send.noticeMsg;

import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

public interface NoticeMsgService {
    /**
     * 群发
     * @param commonMsg
     */
    void sendNoticeMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);

   }
