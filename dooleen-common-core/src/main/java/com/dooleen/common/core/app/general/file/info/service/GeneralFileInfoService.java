package com.dooleen.common.core.app.general.file.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-21 07:40:13
 * @Description : 文档信息管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralFileInfoService extends IService<GeneralFileInfoEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, NullEntity> queryGeneralFileInfo(
			CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileInfoEntity> queryGeneralFileInfoList(
			CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> queryGeneralFileInfoListPage(
			CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> queryGeneralFileInfoListMap(
			CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, NullEntity> saveGeneralFileInfo(
			CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileInfoEntity> saveGeneralFileInfoList(
			CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, NullEntity> updateGeneralFileInfo(
			CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, NullEntity> saveOrUpdateGeneralFileInfo(
			CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileInfoEntity> saveOrUpdateGeneralFileInfoList(
			CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, NullEntity> deleteGeneralFileInfo(
			CommonMsg<GeneralFileInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileInfoEntity> deleteGeneralFileInfoList(
			CommonMsg<NullEntity, GeneralFileInfoEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportGeneralFileInfoExcel(CommonMsg<GeneralFileInfoEntity, GeneralFileInfoEntity> commonMsg,
			HttpServletResponse response);

	/**
	 * 查询文件及历史信息
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月22日 下午4:10:08
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> queryGeneralFileInfoAndHistory(
			CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> commonMsg);

	/**
	 * 保存文件及历史
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月22日 下午7:35:42
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> saveGeneralFileInfoAndHistory(
			CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> commonMsg);
}
