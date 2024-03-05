package com.dooleen.service.general.flow.process.record.service;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-01 14:20:27
 * @Description : 流程处理记录管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralFlowProcessRecordService extends IService<GeneralFlowProcessRecordEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> queryGeneralFlowProcessRecord(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> queryGeneralFlowProcessRecordList(
			CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> queryGeneralFlowProcessRecordListPage(
			CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> queryGeneralFlowProcessRecordListMap(
			CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> saveGeneralFlowProcessRecord(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> saveGeneralFlowProcessRecordList(
			CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg);

	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> updateGeneralFlowProcessRecord(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> saveOrUpdateGeneralFlowProcessRecord(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> saveOrUpdateGeneralFlowProcessRecordList(
			CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> deleteGeneralFlowProcessRecord(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> deleteGeneralFlowProcessRecordList(
			CommonMsg<NullEntity, GeneralFlowProcessRecordEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportGeneralFlowProcessRecordExcel(
			CommonMsg<GeneralFlowProcessRecordEntity, GeneralFlowProcessRecordEntity> commonMsg,
			HttpServletResponse response);

	/**
	 * 获取流程实例实时处理数据用于流程图展示
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月11日 上午9:06:42
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowChartData(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 获取流程实例的处理时间线
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月11日 上午11:46:26
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowTimelineData(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);
}
