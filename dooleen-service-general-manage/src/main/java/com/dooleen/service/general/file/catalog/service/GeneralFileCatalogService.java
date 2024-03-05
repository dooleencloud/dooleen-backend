package com.dooleen.service.general.file.catalog.service;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.general.file.catalog.entity.GeneralFileCatalogEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-21 07:54:19
 * @Description : 文档目录管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralFileCatalogService extends IService<GeneralFileCatalogEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, NullEntity> queryGeneralFileCatalog(
			CommonMsg<GeneralFileCatalogEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileCatalogEntity> queryGeneralFileCatalogList(
			CommonMsg<NullEntity, GeneralFileCatalogEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, GeneralFileCatalogEntity> queryGeneralFileCatalogListPage(
			CommonMsg<GeneralFileCatalogEntity, GeneralFileCatalogEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, GeneralFileCatalogEntity> queryGeneralFileCatalogListMap(
			CommonMsg<GeneralFileCatalogEntity, GeneralFileCatalogEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, NullEntity> saveGeneralFileCatalog(
			CommonMsg<GeneralFileCatalogEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileCatalogEntity> saveGeneralFileCatalogList(
			CommonMsg<NullEntity, GeneralFileCatalogEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, NullEntity> updateGeneralFileCatalog(
			CommonMsg<GeneralFileCatalogEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, NullEntity> saveOrUpdateGeneralFileCatalog(
			CommonMsg<GeneralFileCatalogEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileCatalogEntity> saveOrUpdateGeneralFileCatalogList(
			CommonMsg<NullEntity, GeneralFileCatalogEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, NullEntity> deleteGeneralFileCatalog(
			CommonMsg<GeneralFileCatalogEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileCatalogEntity> deleteGeneralFileCatalogList(
			CommonMsg<NullEntity, GeneralFileCatalogEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportGeneralFileCatalogExcel(CommonMsg<GeneralFileCatalogEntity, GeneralFileCatalogEntity> commonMsg,
			HttpServletResponse response);

	/**
	 * 查询目录树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月21日 上午8:24:55
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileCatalogEntity, JSONObject> queryFileCatalogTree(
			CommonMsg<GeneralFileCatalogEntity, JSONObject> commonMsg);
}
