package com.dooleen.service.biz.forum.question.answer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.forum.question.answer.entity.BizForumQuestionAnswerEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-21 10:19:01
 * @Description : 问题回答管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizForumQuestionAnswerService extends IService<BizForumQuestionAnswerEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity, NullEntity> queryBizForumQuestionAnswer(CommonMsg<BizForumQuestionAnswerEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizForumQuestionAnswerEntity> queryBizForumQuestionAnswerList(CommonMsg<NullEntity,BizForumQuestionAnswerEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity,BizForumQuestionAnswerEntity> queryBizForumQuestionAnswerListPage(CommonMsg<BizForumQuestionAnswerEntity,BizForumQuestionAnswerEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity,BizForumQuestionAnswerEntity> queryBizForumQuestionAnswerListMap(CommonMsg<BizForumQuestionAnswerEntity,BizForumQuestionAnswerEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity, NullEntity> saveBizForumQuestionAnswer(CommonMsg<BizForumQuestionAnswerEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizForumQuestionAnswerEntity> saveBizForumQuestionAnswerList(CommonMsg<NullEntity,BizForumQuestionAnswerEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity, NullEntity> updateBizForumQuestionAnswer(CommonMsg<BizForumQuestionAnswerEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity, NullEntity> saveOrUpdateBizForumQuestionAnswer(CommonMsg<BizForumQuestionAnswerEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizForumQuestionAnswerEntity>  saveOrUpdateBizForumQuestionAnswerList(CommonMsg<NullEntity, BizForumQuestionAnswerEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizForumQuestionAnswerEntity, NullEntity> deleteBizForumQuestionAnswer(CommonMsg<BizForumQuestionAnswerEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizForumQuestionAnswerEntity> deleteBizForumQuestionAnswerList(CommonMsg<NullEntity, BizForumQuestionAnswerEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizForumQuestionAnswerExcel(CommonMsg<BizForumQuestionAnswerEntity,BizForumQuestionAnswerEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<BizForumQuestionAnswerEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
