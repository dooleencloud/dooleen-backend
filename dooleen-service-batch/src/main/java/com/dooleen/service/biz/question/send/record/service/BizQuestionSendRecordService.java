package com.dooleen.service.biz.question.send.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.question.send.record.entity.BizQuestionSendRecordEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-25 15:53:09
 * @Description : 问卷下发记录管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizQuestionSendRecordService extends IService<BizQuestionSendRecordEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity, NullEntity> queryBizQuestionSendRecord(CommonMsg<BizQuestionSendRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionSendRecordEntity> queryBizQuestionSendRecordList(CommonMsg<NullEntity,BizQuestionSendRecordEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity,BizQuestionSendRecordEntity> queryBizQuestionSendRecordListPage(CommonMsg<BizQuestionSendRecordEntity,BizQuestionSendRecordEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity,BizQuestionSendRecordEntity> queryBizQuestionSendRecordListMap(CommonMsg<BizQuestionSendRecordEntity,BizQuestionSendRecordEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity, NullEntity> saveBizQuestionSendRecord(CommonMsg<BizQuestionSendRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionSendRecordEntity> saveBizQuestionSendRecordList(CommonMsg<NullEntity,BizQuestionSendRecordEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity, NullEntity> updateBizQuestionSendRecord(CommonMsg<BizQuestionSendRecordEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity, NullEntity> saveOrUpdateBizQuestionSendRecord(CommonMsg<BizQuestionSendRecordEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionSendRecordEntity>  saveOrUpdateBizQuestionSendRecordList(CommonMsg<NullEntity, BizQuestionSendRecordEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendRecordEntity, NullEntity> deleteBizQuestionSendRecord(CommonMsg<BizQuestionSendRecordEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionSendRecordEntity> deleteBizQuestionSendRecordList(CommonMsg<NullEntity, BizQuestionSendRecordEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizQuestionSendRecordExcel(CommonMsg<BizQuestionSendRecordEntity,BizQuestionSendRecordEntity> commonMsg,HttpServletResponse response);
}
