package com.dooleen.service.system.user.group.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.system.user.group.entity.SysUserGroupRelationEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-12 09:57:14
 * @Description : 系统用户组关系管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysUserGroupRelationService extends IService<SysUserGroupRelationEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity, NullEntity> querySysUserGroupRelation(CommonMsg<SysUserGroupRelationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysUserGroupRelationEntity> querySysUserGroupRelationList(CommonMsg<NullEntity,SysUserGroupRelationEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity,SysUserGroupRelationEntity> querySysUserGroupRelationListPage(CommonMsg<SysUserGroupRelationEntity,SysUserGroupRelationEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity,SysUserGroupRelationEntity> querySysUserGroupRelationListMap(CommonMsg<SysUserGroupRelationEntity,SysUserGroupRelationEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity, NullEntity> saveSysUserGroupRelation(CommonMsg<SysUserGroupRelationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysUserGroupRelationEntity> saveSysUserGroupRelationList(CommonMsg<NullEntity,SysUserGroupRelationEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity, NullEntity> updateSysUserGroupRelation(CommonMsg<SysUserGroupRelationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity, NullEntity> saveOrUpdateSysUserGroupRelation(CommonMsg<SysUserGroupRelationEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysUserGroupRelationEntity>  saveOrUpdateSysUserGroupRelationList(CommonMsg<NullEntity, SysUserGroupRelationEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysUserGroupRelationEntity, NullEntity> deleteSysUserGroupRelation(CommonMsg<SysUserGroupRelationEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysUserGroupRelationEntity> deleteSysUserGroupRelationList(CommonMsg<NullEntity, SysUserGroupRelationEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysUserGroupRelationExcel(CommonMsg<SysUserGroupRelationEntity,SysUserGroupRelationEntity> commonMsg,HttpServletResponse response);
}
