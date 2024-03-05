package com.dooleen.service.system.tool.dict.root.service;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.tool.dict.root.entity.SysToolDictRootEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-30 14:12:02
 * @Description : 系统标准词根管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysToolDictRootService extends IService<SysToolDictRootEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity, NullEntity> querySysToolDictRoot(CommonMsg<SysToolDictRootEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysToolDictRootEntity> querySysToolDictRootList(CommonMsg<NullEntity,SysToolDictRootEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity,SysToolDictRootEntity> querySysToolDictRootListPage(CommonMsg<SysToolDictRootEntity,SysToolDictRootEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity,SysToolDictRootEntity> querySysToolDictRootListMap(CommonMsg<SysToolDictRootEntity,SysToolDictRootEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity, NullEntity> saveSysToolDictRoot(CommonMsg<SysToolDictRootEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysToolDictRootEntity> saveSysToolDictRootList(CommonMsg<NullEntity,SysToolDictRootEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity, NullEntity> updateSysToolDictRoot(CommonMsg<SysToolDictRootEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity, NullEntity> saveOrUpdateSysToolDictRoot(CommonMsg<SysToolDictRootEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysToolDictRootEntity>  saveOrUpdateSysToolDictRootList(CommonMsg<NullEntity, SysToolDictRootEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysToolDictRootEntity, NullEntity> deleteSysToolDictRoot(CommonMsg<SysToolDictRootEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysToolDictRootEntity> deleteSysToolDictRootList(CommonMsg<NullEntity, SysToolDictRootEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysToolDictRootExcel(CommonMsg<SysToolDictRootEntity,SysToolDictRootEntity> commonMsg,HttpServletResponse response);
}
