package com.dooleen.service.biz.question.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.question.template.entity.BizQuestionTemplateEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:31:07
 * @Description : 问卷模板管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizQuestionTemplateService extends IService<BizQuestionTemplateEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity, NullEntity> queryBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionTemplateEntity> queryBizQuestionTemplateList(CommonMsg<NullEntity,BizQuestionTemplateEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity,BizQuestionTemplateEntity> queryBizQuestionTemplateListPage(CommonMsg<BizQuestionTemplateEntity,BizQuestionTemplateEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity,BizQuestionTemplateEntity> queryBizQuestionTemplateListMap(CommonMsg<BizQuestionTemplateEntity,BizQuestionTemplateEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity, NullEntity> saveBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizQuestionTemplateEntity> saveBizQuestionTemplateList(CommonMsg<NullEntity,BizQuestionTemplateEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity, NullEntity> updateBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity, NullEntity> saveOrUpdateBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionTemplateEntity>  saveOrUpdateBizQuestionTemplateList(CommonMsg<NullEntity, BizQuestionTemplateEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizQuestionTemplateEntity, NullEntity> deleteBizQuestionTemplate(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizQuestionTemplateEntity> deleteBizQuestionTemplateList(CommonMsg<NullEntity, BizQuestionTemplateEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizQuestionTemplateExcel(CommonMsg<BizQuestionTemplateEntity,BizQuestionTemplateEntity> commonMsg,HttpServletResponse response);

    CommonMsg<BizQuestionTemplateEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;

    CommonMsg<BizQuestionTemplateEntity, NullEntity>  sendQuestionNow(CommonMsg<BizQuestionTemplateEntity, NullEntity> commonMsg);

    /**
	 * 问卷下发
	 */
	void sendQuestionToUser();
}
