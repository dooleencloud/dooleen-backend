package com.dooleen.service.system.menu.service;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.menu.entity.SysMenuEntity;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.menu.entity.SysTenantMenuRelationEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-08 16:40:01
 * @Description : 系统菜单管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysMenuService extends IService<SysMenuEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, NullEntity> querySysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMenuEntity> querySysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, SysMenuEntity> querySysMenuListPage(CommonMsg<SysMenuEntity, SysMenuEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, SysMenuEntity> querySysMenuListMap(CommonMsg<SysMenuEntity, SysMenuEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, NullEntity> saveSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMenuEntity> saveSysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, NullEntity> updateSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, NullEntity> saveOrUpdateSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMenuEntity> saveOrUpdateSysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, NullEntity> deleteSysMenu(CommonMsg<SysMenuEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMenuEntity> deleteSysMenuList(CommonMsg<NullEntity, SysMenuEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportSysMenuExcel(CommonMsg<SysMenuEntity, SysMenuEntity> commonMsg, HttpServletResponse response);

	/**
	 * 根据指定字段查询集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月8日 下午7:24:30
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMenuEntity, JSONObject> querySysMenuTree(CommonMsg<SysMenuEntity, JSONObject> commonMsg);

	/**
	 * 根据租户ID获取菜单
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月9日 下午8:05:36
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity, SysMenuEntity> querySysMenuListByTenantId(CommonMsg<SysTenantMenuRelationEntity, SysMenuEntity> commonMsg);

	
	/**
	 * 同步菜单到租户
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月10日 下午12:35:06
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysTenantInfoEntity> synchronizeSysMenu(CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg);

		
	/**
	 * 获取用户的菜单结构
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月10日 下午10:49:51
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserRoleRelationEntity, JSONObject> querySysMenuTreeByUserId(CommonMsg<SysUserRoleRelationEntity, JSONObject> commonMsg);

	/**
	 * 获取用户项目菜单
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月12日 下午3:07:31
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserRoleRelationEntity, JSONObject> querySysMenuTreeByUserIdAndProject(CommonMsg<SysUserRoleRelationEntity, JSONObject> commonMsg);
	
	/**
	 * 获取用户有权限的按钮列表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月12日 下午3:07:31
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserRoleRelationEntity, JSONObject> querySysButtonByUserId(CommonMsg<SysUserRoleRelationEntity, JSONObject> commonMsg);



}
