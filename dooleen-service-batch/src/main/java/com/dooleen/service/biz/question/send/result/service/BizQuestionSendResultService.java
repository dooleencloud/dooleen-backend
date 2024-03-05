package com.dooleen.service.biz.question.send.result.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.question.send.result.entity.BizQuestionSendResultEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-03-26 10:40:07
 * @Description : 问卷下发答题结果服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizQuestionSendResultService extends IService<BizQuestionSendResultEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity, NullEntity> queryBizQuestionSendResult(CommonMsg<BizQuestionSendResultEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionSendResultEntity> queryBizQuestionSendResultList(CommonMsg<NullEntity,BizQuestionSendResultEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity,BizQuestionSendResultEntity> queryBizQuestionSendResultListPage(CommonMsg<BizQuestionSendResultEntity,BizQuestionSendResultEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity,BizQuestionSendResultEntity> queryBizQuestionSendResultListMap(CommonMsg<BizQuestionSendResultEntity,BizQuestionSendResultEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity, NullEntity> saveBizQuestionSendResult(CommonMsg<BizQuestionSendResultEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionSendResultEntity> saveBizQuestionSendResultList(CommonMsg<NullEntity,BizQuestionSendResultEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity, NullEntity> updateBizQuestionSendResult(CommonMsg<BizQuestionSendResultEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity, NullEntity> saveOrUpdateBizQuestionSendResult(CommonMsg<BizQuestionSendResultEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionSendResultEntity>  saveOrUpdateBizQuestionSendResultList(CommonMsg<NullEntity, BizQuestionSendResultEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendResultEntity, NullEntity> deleteBizQuestionSendResult(CommonMsg<BizQuestionSendResultEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionSendResultEntity> deleteBizQuestionSendResultList(CommonMsg<NullEntity, BizQuestionSendResultEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizQuestionSendResultExcel(CommonMsg<BizQuestionSendResultEntity,BizQuestionSendResultEntity> commonMsg,HttpServletResponse response);
}
