package com.dooleen.common.core.app.general.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowInfoEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程信息管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralFlowProcessService extends IService<GeneralFlowProcessRecordEntity> {

	/**
	 * 
	 * 流程处理主逻辑
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> processFlow(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 开始流程
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月1日 下午3:43:10
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> startFlow(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 拒绝流程
	 *
	 * @Author : apple
	 * @CreateTime : 2020年7月1日 下午3:43:10
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> processReject(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 撤回流程
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月9日 下午2:19:41
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	 CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> processCallBack(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 * 获取流程节点数据 用于画图
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月10日 下午3:24:28
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowInfoEntity, NullEntity> getFlowChartData(CommonMsg<GeneralFlowInfoEntity, NullEntity> commonMsg);

	/**
	 * 获取业务实例实时流程信息数据（用于绘制流程图）
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月11日 上午8:37:45
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowChartData(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);

	/**
	 *  获取流程实例的处理时间线
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年7月11日 上午10:58:28
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowTimelineData(
			CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg);
}
