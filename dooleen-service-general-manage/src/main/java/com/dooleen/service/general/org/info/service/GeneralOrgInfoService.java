package com.dooleen.service.general.org.info.service;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 00:05:24
 * @Description : 组织机构管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralOrgInfoService extends IService<GeneralOrgInfoEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity, NullEntity> queryGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralOrgInfoEntity> queryGeneralOrgInfoList(CommonMsg<NullEntity,GeneralOrgInfoEntity> commonMsg);
	/**
	 * 
	 * 获取机构树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity,JSONObject> queryGeneralOrgTree(CommonMsg<GeneralOrgInfoEntity,JSONObject> commonMsg);
	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity,GeneralOrgInfoEntity> queryGeneralOrgInfoListPage(CommonMsg<GeneralOrgInfoEntity,GeneralOrgInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity,GeneralOrgInfoEntity> queryGeneralOrgInfoListMap(CommonMsg<GeneralOrgInfoEntity,GeneralOrgInfoEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity, NullEntity> saveGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralOrgInfoEntity> saveGeneralOrgInfoList(CommonMsg<NullEntity,GeneralOrgInfoEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity, NullEntity> updateGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity, NullEntity> saveOrUpdateGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralOrgInfoEntity>  saveOrUpdateGeneralOrgInfoList(CommonMsg<NullEntity, GeneralOrgInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralOrgInfoEntity, NullEntity> deleteGeneralOrgInfo(CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralOrgInfoEntity> deleteGeneralOrgInfoList(CommonMsg<NullEntity, GeneralOrgInfoEntity> commonMsg);

		/**
	 * 导出数据
		 * @param belongProjectNo 
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportGeneralOrgInfoExcel(CommonMsg<GeneralOrgInfoEntity,GeneralOrgInfoEntity> commonMsg,HttpServletResponse response);
}
