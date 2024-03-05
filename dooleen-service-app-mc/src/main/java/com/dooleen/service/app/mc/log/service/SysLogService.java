package com.dooleen.service.app.mc.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.system.log.entity.SysLogEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-25 11:07:26
 * @Description : 系统日志管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysLogService extends IService<SysLogEntity> {

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysLogEntity, NullEntity> saveSysLog(CommonMsg<SysLogEntity, NullEntity> commonMsg);

}
