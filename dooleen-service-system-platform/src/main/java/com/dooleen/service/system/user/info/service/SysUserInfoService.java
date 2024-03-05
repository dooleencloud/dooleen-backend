package com.dooleen.service.system.user.info.service;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.role.entity.SysRoleEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 系统用户管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysUserInfoService extends IService<SysUserInfoEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> querySysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 根据用户名+密码查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, SysRoleEntity> querySysUserInfoByUserName(CommonMsg<SysUserInfoEntity, SysRoleEntity> commonMsg);

	CommonMsg<SysUserInfoEntity, NullEntity> querySysUserInfoByMobile(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysUserInfoEntity> querySysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, SysUserInfoEntity> querySysUserInfoListPage(
			CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, SysUserInfoEntity> querySysUserInfoListMap(
			CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> saveSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysUserInfoEntity> saveSysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> updateSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> saveOrUpdateSysUserInfo(
			CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysUserInfoEntity> saveOrUpdateSysUserInfoList(
			CommonMsg<NullEntity, SysUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> deleteSysUserInfo(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysUserInfoEntity> deleteSysUserInfoList(CommonMsg<NullEntity, SysUserInfoEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportSysUserInfoExcel(CommonMsg<SysUserInfoEntity, SysUserInfoEntity> commonMsg,
			HttpServletResponse response);
	/**
	 * 创建项目Admin用户
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月16日 下午4:39:30
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> createProjectAdminUser(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);
	
	/**
	 * 创建租户Admin用户
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月16日 下午4:39:30
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> createAdminUser(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 支持租户Admin用户密码；
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月16日 下午4:47:18
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserInfoEntity, NullEntity> restAdminPassword(CommonMsg<SysUserInfoEntity, NullEntity> commonMsg);

	
}
