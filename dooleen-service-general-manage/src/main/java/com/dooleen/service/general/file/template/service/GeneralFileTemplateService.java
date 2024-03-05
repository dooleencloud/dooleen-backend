package com.dooleen.service.general.file.template.service;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.general.file.template.entity.GeneralFileTemplateEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-25 11:07:35
 * @Description : 文档模板管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralFileTemplateService extends IService<GeneralFileTemplateEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity, NullEntity> queryGeneralFileTemplate(CommonMsg<GeneralFileTemplateEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,JSONObject> queryGeneralFileTemplateList(CommonMsg<NullEntity,JSONObject> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity,GeneralFileTemplateEntity> queryGeneralFileTemplateListPage(CommonMsg<GeneralFileTemplateEntity,GeneralFileTemplateEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity,GeneralFileTemplateEntity> queryGeneralFileTemplateListMap(CommonMsg<GeneralFileTemplateEntity,GeneralFileTemplateEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity, NullEntity> saveGeneralFileTemplate(CommonMsg<GeneralFileTemplateEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralFileTemplateEntity> saveGeneralFileTemplateList(CommonMsg<NullEntity,GeneralFileTemplateEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity, NullEntity> updateGeneralFileTemplate(CommonMsg<GeneralFileTemplateEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity, NullEntity> saveOrUpdateGeneralFileTemplate(CommonMsg<GeneralFileTemplateEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileTemplateEntity>  saveOrUpdateGeneralFileTemplateList(CommonMsg<NullEntity, GeneralFileTemplateEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralFileTemplateEntity, NullEntity> deleteGeneralFileTemplate(CommonMsg<GeneralFileTemplateEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralFileTemplateEntity> deleteGeneralFileTemplateList(CommonMsg<NullEntity, GeneralFileTemplateEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportGeneralFileTemplateExcel(CommonMsg<GeneralFileTemplateEntity,GeneralFileTemplateEntity> commonMsg,HttpServletResponse response);
}
