package com.dooleen.service.app.mc.history.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.system.history.entity.SysHistoryInfoEntity;
import com.dooleen.common.core.app.system.history.mapper.SysHistoryInfoMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.app.mc.history.service.SysHistoryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-07 10:57:34
 * @Description : 历史记录表管理服务实现
 * @Author : apple
 * @Update: 2020-07-07 10:57:34
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysHistoryInfoServiceImpl extends ServiceImpl<SysHistoryInfoMapper, SysHistoryInfoEntity> implements SysHistoryInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	private  static String seqPrefix= TableEntityORMEnum.SYS_HISTORY_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_HISTORY_INFO.getEntityName();
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public CommonMsg<SysHistoryInfoEntity, NullEntity> saveSysHistoryInfo(CommonMsg<SysHistoryInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

}
