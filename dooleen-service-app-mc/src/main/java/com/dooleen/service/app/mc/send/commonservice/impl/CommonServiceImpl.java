package com.dooleen.service.app.mc.send.commonservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.send.log.mapper.SysSendLogMapper;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;
import com.dooleen.common.core.app.system.msg.config.service.SysMsgConfigEntityService;
import com.dooleen.common.core.app.system.msg.notice.entity.SysMsgNoticeEntity;
import com.dooleen.common.core.app.system.msg.notice.mapper.SysMsgNoticeMapper;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import com.dooleen.common.core.app.system.third.service.SysThirdPartyInfoService;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.service.app.mc.send.commonservice.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
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

	@Autowired
	private SysSendLogMapper sysSysSendLogMapper;

	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;

	@Autowired
	private SysMsgConfigEntityService sysMsgConfigEntityService;

	private  static String seqPrefix= TableEntityORMEnum.SYS_SEND_LOG.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_SEND_LOG.getEntityName();


	@Autowired
	private SysMsgNoticeMapper sysMsgNoticeMapper;

	private  static String nseqPrefix= TableEntityORMEnum.SYS_MSG_NOTICE.getSeqPrefix();
	private  static String ntableName = TableEntityORMEnum.SYS_MSG_NOTICE.getEntityName();


	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	/**
	 * 根据用户名获取用户信息
	 * @return
	 */
	@Override
	public List<SendMsgUserInfoEntity> getUserList(String tenantId, String sendAddressList){
		List<String> list = JSONObject.parseArray(sendAddressList, String.class);
		List<SendMsgUserInfoEntity> listSet = sysUserInfoMapper.selectByUserNameList(tenantId,list);
		return listSet;
	}

    /**
     * 写日志
	 * @param commonMsg
     * @param smsContent
     * @param errMsg
	 */
	@Override
	@Async("asyncTaskExecutor")
	public void sysSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg, String smsContent,String status, String errMsg){
		// 写发送日志
		SysSendLogEntity sysSysSendLogEntity = commonMsg.getBody().getSingleBody();
		sysSysSendLogEntity.setSendStatus(status);
		sysSysSendLogEntity.setStatusInfo(errMsg);
		if("sms".equals(sysSysSendLogEntity.getMsgType())){
			sysSysSendLogEntity.setMsgContent(smsContent);
		}
		sysSysSendLogEntity.setId("");
		sysSysSendLogEntity.setDataSign(DooleenMD5Util.md5(sysSysSendLogEntity.toString(),  ConstantValue.md5Key));
		// 执行保存
		sysSysSendLogMapper.insert(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysSysSendLogEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
	}

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

	/**
	 *
	 */
	@Override
	public SysMsgConfigEntity getMsgConfig(String tenantId,String sendScene,String msgType){
		//获取消息模板配置
		SysMsgConfigEntity sysMsgConfigEntity = new SysMsgConfigEntity();
		sysMsgConfigEntity.setTenantId(tenantId);
		sysMsgConfigEntity.setSendScene(sendScene);
		sysMsgConfigEntity.setMsgType(msgType);
		CommonMsg<SysMsgConfigEntity, NullEntity> configCommonMsg = CreateCommonMsg.getCommonMsg(sysMsgConfigEntity, new NullEntity());
		configCommonMsg = sysMsgConfigEntityService.queryMessageConfigInfoByType(configCommonMsg);
		return configCommonMsg.getBody().getSingleBody();
	}
	/**
	 * 修改用户的钉钉ID
	 */
	@Override
	public  void  updateUserDingtalkUserId(String id,String dingTalkUserId){
		SysUserInfoEntity sysUserInfoEntity = new SysUserInfoEntity();
		sysUserInfoEntity.setId(id);
		sysUserInfoEntity.setDingtalkUserId(dingTalkUserId);
		sysUserInfoMapper.updateById(sysUserInfoEntity);
	}

	/**
	 * 写通知记录表
	 */
	@Override
	//@Async("asyncTaskExecutor")
	public String  writeNoticeMsg(CommonMsg<SysSendLogEntity, NullEntity> commonMsg, SendMsgUserInfoEntity userInfo){
		SysSendLogEntity sysSendLogEntity = commonMsg.getBody().getSingleBody();
		if (commonMsg.getBody().getSingleBody().getSendAddressList().equals("all")) {
			//获取当前租户下所有用户
			QueryWrapper<SysUserInfoEntity> queryWrapper = new QueryWrapper<SysUserInfoEntity>();
			queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId,commonMsg.getHead().getTenantId());
			queryWrapper.lambda().eq(SysUserInfoEntity::getValidFlag, "1");
			List<SysUserInfoEntity> userList = sysUserInfoMapper.selectList(queryWrapper);
			for(SysUserInfoEntity sysUserInfoEntity : userList) {
				SysMsgNoticeEntity sysMsgNoticeEntity = new SysMsgNoticeEntity();
				sysMsgNoticeEntity.setTenantId(sysSendLogEntity.getTenantId());
				sysMsgNoticeEntity.setMsgType(sysSendLogEntity.getMsgNoticeType());
				sysMsgNoticeEntity.setBizSceneName(sysSendLogEntity.getBizSceneName());
				sysMsgNoticeEntity.setMsgTitle(sysSendLogEntity.getMsgTitle());
				sysMsgNoticeEntity.setMsgContent(sysSendLogEntity.getMsgContent());
				sysMsgNoticeEntity.setMsgContentJson(sysSendLogEntity.getMsgContentJson());
				sysMsgNoticeEntity.setMsgResourceId(sysSendLogEntity.getResourceId());
				sysMsgNoticeEntity.setReadStatus("0");
				sysMsgNoticeEntity.setAcceptMsgUserName(sysUserInfoEntity.getUserName());
				sysMsgNoticeEntity.setAcceptRealName(sysUserInfoEntity.getRealName());
				sysMsgNoticeEntity.setSenderHeadImgUrl(sysSendLogEntity.getHeadImgUrl());
				sysMsgNoticeEntity.setSendMsgUserName(sysSendLogEntity.getSenderId());
				sysMsgNoticeEntity.setSendRealName(sysSendLogEntity.getSenderRealName());
				sysMsgNoticeEntity.setDataSign(DooleenMD5Util.md5(sysMsgNoticeEntity.toString(),  ConstantValue.md5Key));
				// 执行保存
				int result = sysMsgNoticeMapper.insert(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysMsgNoticeEntity, commonMsg.getHead(),ntableName, nseqPrefix, redisTemplate));
				//log.info("==>> 全部用户发送写消息提醒失败，{}",sysMsgNoticeEntity.toString());
				if(result < 1){
					log.error("==>> 全部用户发送写消息提醒失败，{}",sysMsgNoticeEntity.toString());
				}
			}
			return "all";
		}
		else{
			SysMsgNoticeEntity sysMsgNoticeEntity = new SysMsgNoticeEntity();
			sysMsgNoticeEntity.setTenantId(sysSendLogEntity.getTenantId());
			sysMsgNoticeEntity.setMsgType(sysSendLogEntity.getMsgNoticeType());
			sysMsgNoticeEntity.setBizSceneName(sysSendLogEntity.getBizSceneName());
			sysMsgNoticeEntity.setMsgTitle(sysSendLogEntity.getMsgTitle());
			sysMsgNoticeEntity.setMsgContent(sysSendLogEntity.getMsgContent());
			sysMsgNoticeEntity.setMsgContentJson(sysSendLogEntity.getMsgContentJson());
			sysMsgNoticeEntity.setMsgResourceId(sysSendLogEntity.getResourceId());
			sysMsgNoticeEntity.setReadStatus("0");
			sysMsgNoticeEntity.setAcceptMsgUserName(userInfo.getUserName());
			sysMsgNoticeEntity.setAcceptRealName(userInfo.getRealName());
			sysMsgNoticeEntity.setSenderHeadImgUrl(sysSendLogEntity.getHeadImgUrl());
			sysMsgNoticeEntity.setSendMsgUserName(sysSendLogEntity.getSenderId());
			sysMsgNoticeEntity.setSendRealName(sysSendLogEntity.getSenderRealName());
			sysMsgNoticeEntity.setDataSign(DooleenMD5Util.md5(sysMsgNoticeEntity.toString(),  ConstantValue.md5Key));
			// 执行保存
			sysMsgNoticeEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysMsgNoticeEntity, commonMsg.getHead(),ntableName, nseqPrefix, redisTemplate);
			int result = sysMsgNoticeMapper.insert(sysMsgNoticeEntity);
			if(result < 1){
				log.error("==>> 写消息提醒失败，{}",sysMsgNoticeEntity.toString());
			}
			return sysMsgNoticeEntity.getId();
		}
	}
}