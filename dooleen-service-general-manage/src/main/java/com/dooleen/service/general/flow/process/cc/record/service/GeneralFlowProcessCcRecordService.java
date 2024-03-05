package com.dooleen.service.general.flow.process.cc.record.service;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessCcRecordEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-30 09:54:05
 * @Description : 流程处理抄送记录表服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralFlowProcessCcRecordService extends IService<GeneralFlowProcessCcRecordEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> queryGeneralFlowProcessCcRecord(CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> queryGeneralFlowProcessCcRecordList(CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity> queryGeneralFlowProcessCcRecordListPage(CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity> queryGeneralFlowProcessCcRecordListMap(CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> saveGeneralFlowProcessCcRecord(CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> saveGeneralFlowProcessCcRecordList(CommonMsg<NullEntity,GeneralFlowProcessCcRecordEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> updateGeneralFlowProcessCcRecord(CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> saveOrUpdateGeneralFlowProcessCcRecord(CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity>  saveOrUpdateGeneralFlowProcessCcRecordList(CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> deleteGeneralFlowProcessCcRecord(CommonMsg<GeneralFlowProcessCcRecordEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> deleteGeneralFlowProcessCcRecordList(CommonMsg<NullEntity, GeneralFlowProcessCcRecordEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportGeneralFlowProcessCcRecordExcel(CommonMsg<GeneralFlowProcessCcRecordEntity,GeneralFlowProcessCcRecordEntity> commonMsg,HttpServletResponse response);
}
