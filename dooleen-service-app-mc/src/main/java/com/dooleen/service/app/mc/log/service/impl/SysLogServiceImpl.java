package com.dooleen.service.app.mc.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.system.log.entity.SysLogEntity;
import com.dooleen.common.core.app.system.log.mapper.SysLogMapper;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.app.mc.log.service.SysLogService;
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
 * @CreateDate : 2020-07-25 11:07:26
 * @Description : 系统日志管理服务实现
 * @Author : apple
 * @Update: 2020-07-25 11:07:26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

	private  static String seqPrefix= TableEntityORMEnum.SYS_LOG.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_LOG.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public CommonMsg<SysLogEntity, NullEntity> saveSysLog(CommonMsg<SysLogEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);

		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
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
