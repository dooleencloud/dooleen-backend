package com.dooleen.service.system.role.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.system.group.role.entity.SysGroupRoleRelationEntity;
import com.dooleen.service.system.role.entity.SysRoleEntity;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-07 21:34:01
 * @Description : 系统角色管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, NullEntity> querySysRole(CommonMsg<SysRoleEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysRoleEntity> querySysRoleList(CommonMsg<NullEntity, SysRoleEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, SysRoleEntity> querySysRoleListPage(CommonMsg<SysRoleEntity, SysRoleEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, SysRoleEntity> querySysRoleListMap(CommonMsg<SysRoleEntity, SysRoleEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, NullEntity> saveSysRole(CommonMsg<SysRoleEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysRoleEntity> saveSysRoleList(CommonMsg<NullEntity, SysRoleEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, NullEntity> updateSysRole(CommonMsg<SysRoleEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, NullEntity> saveOrUpdateSysRole(CommonMsg<SysRoleEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysRoleEntity> saveOrUpdateSysRoleList(CommonMsg<NullEntity, SysRoleEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, NullEntity> deleteSysRole(CommonMsg<SysRoleEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysRoleEntity> deleteSysRoleList(CommonMsg<NullEntity, SysRoleEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportSysRoleExcel(CommonMsg<SysRoleEntity, SysRoleEntity> commonMsg, HttpServletResponse response);

	/**
	 * 根据用户ID获取角色列表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月8日 上午9:53:40
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserRoleRelationEntity, SysRoleEntity> querySysRoleListByUserId(
			CommonMsg<SysUserRoleRelationEntity, SysRoleEntity> commonMsg);

	/**
	 * 根据用户组ID查询集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月12日 上午6:35:11
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysGroupRoleRelationEntity, SysRoleEntity> querySysRoleListByGroupId(
			CommonMsg<SysGroupRoleRelationEntity, SysRoleEntity> commonMsg);

	/**
	 * 按分组查询角色列表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月24日 下午3:24:46
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysRoleEntity, JSONObject> querySysRoleTree(CommonMsg<SysRoleEntity, JSONObject> commonMsg);
	
	/**
	 * 根据用户组ID获取角色列表
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月8日 上午9:53:40
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
//	CommonMsg<SysUserRoleRelationEntity, SysRoleEntity> querySysRoleListByUserGroupId(
//			CommonMsg<SysUserRoleRelationEntity, SysRoleEntity> commonMsg);
}
