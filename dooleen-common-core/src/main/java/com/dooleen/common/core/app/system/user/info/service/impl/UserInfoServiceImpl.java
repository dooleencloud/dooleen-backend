package com.dooleen.common.core.app.system.user.info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.app.system.user.info.service.UserInfoService;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import com.dooleen.common.core.utils.FormatUserName;
import com.dooleen.common.core.utils.GenerateNo;
import com.dooleen.common.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 通过实体列表返回用户头像Map集合
 * @Author : apple
 * @Update: 2020-06-28 18:24:13
 */
@Slf4j
@Service
public class UserInfoServiceImpl<T> extends ServiceImpl<SysUserInfoMapper, SysUserInfoEntity> implements UserInfoService<T> {

	@Autowired
	private  SysUserInfoMapper sysUserInfoMapper;


	@Autowired
	private RedisTemplate<String, ?> redisTemplate;


	@Override
	public Map<String, SendMsgUserInfoEntity> getHeadImgUrlList(List<T> entityList,String userName) {
		HashSet userNameHash = new HashSet();
		String tenantId = "";
		for (T entity: entityList) {
			if (null != entity) {
				// 通过实例得到类
				@SuppressWarnings("rawtypes")
				// entityClass
				Class currEntityClass = (Class) entity.getClass();
				//Class entityClass = (Class) entity.getClass().getSuperclass();
				try {
					userNameHash.add((String)currEntityClass.getDeclaredField(userName).get(entity));
					if(StringUtil.isEmpty(tenantId)) {
						tenantId = (String) currEntityClass.getDeclaredField("tenantId").get(entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Map<String, SendMsgUserInfoEntity> userImgUrlMap = new HashMap<>();
		List<String> userNames  = new ArrayList<>();
		userNames.addAll(userNameHash);
		if(userNames.size() > 0) {
			List<SendMsgUserInfoEntity> userInfList = sysUserInfoMapper.selectByUserNameList(tenantId, userNames);
			for (SendMsgUserInfoEntity sendMsgUserInfoEntity : userInfList) {
				userImgUrlMap.put(sendMsgUserInfoEntity.getUserName(), sendMsgUserInfoEntity);
			}
		}
		return userImgUrlMap;
	}

	@Override
	public Map<String, SendMsgUserInfoEntity> getHeadImgUrlListBySet(String tenantId, List<String> userNameList) {
		Map<String, SendMsgUserInfoEntity> userImgUrlMap = new HashMap<>();
		if(userNameList.size() > 0) {
			List<SendMsgUserInfoEntity> userInfList = sysUserInfoMapper.selectByUserNameList(tenantId, userNameList);
			for (SendMsgUserInfoEntity sendMsgUserInfoEntity : userInfList) {
				userImgUrlMap.put(sendMsgUserInfoEntity.getUserName(), sendMsgUserInfoEntity);
			}
		}
		return userImgUrlMap;
	}
}
