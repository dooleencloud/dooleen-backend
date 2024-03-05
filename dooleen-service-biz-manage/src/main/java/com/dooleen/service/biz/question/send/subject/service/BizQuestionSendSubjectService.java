package com.dooleen.service.biz.question.send.subject.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.question.send.subject.entity.BizQuestionSendSubjectEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:37:04
 * @Description : 问卷下发题目管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizQuestionSendSubjectService extends IService<BizQuestionSendSubjectEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity, NullEntity> queryBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectList(CommonMsg<NullEntity,BizQuestionSendSubjectEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectListPage(CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity> queryBizQuestionSendSubjectListMap(CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity, NullEntity> saveBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionSendSubjectEntity> saveBizQuestionSendSubjectList(CommonMsg<NullEntity,BizQuestionSendSubjectEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity, NullEntity> updateBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity, NullEntity> saveOrUpdateBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionSendSubjectEntity>  saveOrUpdateBizQuestionSendSubjectList(CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionSendSubjectEntity, NullEntity> deleteBizQuestionSendSubject(CommonMsg<BizQuestionSendSubjectEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionSendSubjectEntity> deleteBizQuestionSendSubjectList(CommonMsg<NullEntity, BizQuestionSendSubjectEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizQuestionSendSubjectExcel(CommonMsg<BizQuestionSendSubjectEntity,BizQuestionSendSubjectEntity> commonMsg,HttpServletResponse response);

	/**
	 * 统计答卷
	 * @param projectName
	 * @param subjectTitle
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	JSONObject questionReport(String tenantId, String projectName, String batchNo,  String subjectTitle,String flag);
}
