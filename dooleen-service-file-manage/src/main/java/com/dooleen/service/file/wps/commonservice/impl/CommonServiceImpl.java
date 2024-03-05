package com.dooleen.service.file.wps.commonservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.send.log.mapper.SysSendLogMapper;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.app.system.msg.config.service.SysMsgConfigEntityService;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.app.system.third.service.SysThirdPartyInfoService;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.service.file.wps.commonservice.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonService {
	@Autowired
	private SysThirdPartyInfoService sysThirdPartyInfoService;

	/**
	 * 获取第三方配置
	 * @param tenantId
	 * @param type
	 * @return
	 */
	@Override
	public JSONObject getThirdParam(String tenantId,String type){
		//读取三方配置获取appId, appKey
		SysThirdPartyInfoEntity sysThirdPartyInfoEntity = new SysThirdPartyInfoEntity();
		sysThirdPartyInfoEntity.setTenantId(tenantId);
		sysThirdPartyInfoEntity.setType(type);
		CommonMsg<SysThirdPartyInfoEntity, NullEntity> thirdCommonMsg = CreateCommonMsg.getCommonMsg(sysThirdPartyInfoEntity, new NullEntity());
		thirdCommonMsg = sysThirdPartyInfoService.querySysThirdPartyInfoByType(thirdCommonMsg);
		String content = thirdCommonMsg.getBody().getSingleBody().getThirdPartyConfigContent();
		JSONObject jsonObj = JSON.parseObject(content);
		return  jsonObj;
	}
}