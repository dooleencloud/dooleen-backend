package com.dooleen.service.system.license.service;

import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.license.entity.LicenseEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 15:51:08
 * @Description : 租户管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface LicenseService{

	/**
	 * 
	 * 检查license是否过期
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<LicenseEntity, NullEntity> checkTenantLicense(CommonMsg<LicenseEntity, NullEntity> commonMsg);

}
