package com.dooleen.service.system.tenant.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.system.tenant.menu.entity.SysTenantMenuRelationEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-09 19:49:16
 * @Description : 系统租户菜单关系管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysTenantMenuRelationService extends IService<SysTenantMenuRelationEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity, NullEntity> querySysTenantMenuRelation(CommonMsg<SysTenantMenuRelationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysTenantMenuRelationEntity> querySysTenantMenuRelationList(CommonMsg<NullEntity,SysTenantMenuRelationEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity,SysTenantMenuRelationEntity> querySysTenantMenuRelationListPage(CommonMsg<SysTenantMenuRelationEntity,SysTenantMenuRelationEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity,SysTenantMenuRelationEntity> querySysTenantMenuRelationListMap(CommonMsg<SysTenantMenuRelationEntity,SysTenantMenuRelationEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity, NullEntity> saveSysTenantMenuRelation(CommonMsg<SysTenantMenuRelationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysTenantMenuRelationEntity> saveSysTenantMenuRelationList(CommonMsg<NullEntity,SysTenantMenuRelationEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity, NullEntity> updateSysTenantMenuRelation(CommonMsg<SysTenantMenuRelationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity, NullEntity> saveOrUpdateSysTenantMenuRelation(CommonMsg<SysTenantMenuRelationEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysTenantMenuRelationEntity>  saveOrUpdateSysTenantMenuRelationList(CommonMsg<NullEntity, SysTenantMenuRelationEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysTenantMenuRelationEntity, NullEntity> deleteSysTenantMenuRelation(CommonMsg<SysTenantMenuRelationEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysTenantMenuRelationEntity> deleteSysTenantMenuRelationList(CommonMsg<NullEntity, SysTenantMenuRelationEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysTenantMenuRelationExcel(CommonMsg<SysTenantMenuRelationEntity,SysTenantMenuRelationEntity> commonMsg,HttpServletResponse response);
}
