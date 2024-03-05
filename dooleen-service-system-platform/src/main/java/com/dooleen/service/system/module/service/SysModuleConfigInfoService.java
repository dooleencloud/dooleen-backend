package com.dooleen.service.system.module.service;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.system.module.entity.SysModuleConfigInfoEntity;
import com.dooleen.common.core.app.system.module.entity.TableInfo;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.module.vo.PushBaseDataVO;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-22 10:20:14
 * @Description : 系统数据表管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysModuleConfigInfoService extends IService<SysModuleConfigInfoEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity, NullEntity> querySysModuleConfigInfo(CommonMsg<SysModuleConfigInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysModuleConfigInfoEntity> querySysModuleConfigInfoList(CommonMsg<NullEntity,SysModuleConfigInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity,SysModuleConfigInfoEntity> querySysModuleConfigInfoListPage(CommonMsg<SysModuleConfigInfoEntity,SysModuleConfigInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity,SysModuleConfigInfoEntity> querySysModuleConfigInfoListMap(CommonMsg<SysModuleConfigInfoEntity,SysModuleConfigInfoEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity, NullEntity> saveSysModuleConfigInfo(CommonMsg<SysModuleConfigInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysModuleConfigInfoEntity> saveSysModuleConfigInfoList(CommonMsg<NullEntity,SysModuleConfigInfoEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity, NullEntity> updateSysModuleConfigInfo(CommonMsg<SysModuleConfigInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity, NullEntity> saveOrUpdateSysModuleConfigInfo(CommonMsg<SysModuleConfigInfoEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysModuleConfigInfoEntity>  saveOrUpdateSysModuleConfigInfoList(CommonMsg<NullEntity, SysModuleConfigInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysModuleConfigInfoEntity, NullEntity> deleteSysModuleConfigInfo(CommonMsg<SysModuleConfigInfoEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysModuleConfigInfoEntity> deleteSysModuleConfigInfoList(CommonMsg<NullEntity, SysModuleConfigInfoEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysModuleConfigInfoExcel(CommonMsg<SysModuleConfigInfoEntity,SysModuleConfigInfoEntity> commonMsg,HttpServletResponse response);

	CommonMsg<TableInfo, TableInfo> queryTableList(CommonMsg<TableInfo, TableInfo> commonMsg);

	CommonMsg<PushBaseDataVO, NullEntity> pushBaseData(CommonMsg<PushBaseDataVO, NullEntity> commonMsg);
}
