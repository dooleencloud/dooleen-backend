package com.dooleen.service.system.api.scope.service;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.api.scope.entity.SysApiScopeEntity;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-10 16:26:18
 * @Description : 系统接口管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysApiScopeService extends IService<SysApiScopeEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysApiScopeEntity> querySysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, SysApiScopeEntity> querySysApiScopeListPage(
			CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, SysApiScopeEntity> querySysApiScopeListMap(
			CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, NullEntity> saveSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysApiScopeEntity> saveSysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, NullEntity> updateSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, NullEntity> saveOrUpdateSysApiScope(
			CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysApiScopeEntity> saveOrUpdateSysApiScopeList(
			CommonMsg<NullEntity, SysApiScopeEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, NullEntity> deleteSysApiScope(CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysApiScopeEntity> deleteSysApiScopeList(CommonMsg<NullEntity, SysApiScopeEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportSysApiScopeExcel(CommonMsg<SysApiScopeEntity, SysApiScopeEntity> commonMsg, HttpServletResponse response);

	/**
	 * 同步接口到租户
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月11日 下午8:24:32
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysTenantInfoEntity> synchronizeSysApiScope(CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg);

	/**
	 * 查询api 集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月11日 下午10:05:00
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, JSONObject> querySysApiScopeTree(CommonMsg<SysApiScopeEntity, JSONObject> commonMsg);

	/**
	 * 查询用户是否具备接口权限
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月17日 上午7:55:47
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysApiScopeEntity, NullEntity> querySysApiScopeByUserIdAndAddress(
			CommonMsg<SysApiScopeEntity, NullEntity> commonMsg);

}
