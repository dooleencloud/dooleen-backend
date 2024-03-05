package com.dooleen.service.biz.apparch.system.function.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.apparch.system.function.entity.BizApparchSystemFunctionEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-09-02 11:54:57
 * @Description : 系统功能点管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizApparchSystemFunctionService extends IService<BizApparchSystemFunctionEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity, NullEntity> queryBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionList(CommonMsg<NullEntity,BizApparchSystemFunctionEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity,BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionListPage(CommonMsg<BizApparchSystemFunctionEntity,BizApparchSystemFunctionEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity,BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionListMap(CommonMsg<BizApparchSystemFunctionEntity,BizApparchSystemFunctionEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity, NullEntity> saveBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizApparchSystemFunctionEntity> saveBizApparchSystemFunctionList(CommonMsg<NullEntity,BizApparchSystemFunctionEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity, NullEntity> updateBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity, NullEntity> saveOrUpdateBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizApparchSystemFunctionEntity>  saveOrUpdateBizApparchSystemFunctionList(CommonMsg<NullEntity, BizApparchSystemFunctionEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchSystemFunctionEntity, NullEntity> deleteBizApparchSystemFunction(CommonMsg<BizApparchSystemFunctionEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizApparchSystemFunctionEntity> deleteBizApparchSystemFunctionList(CommonMsg<NullEntity, BizApparchSystemFunctionEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizApparchSystemFunctionExcel(CommonMsg<BizApparchSystemFunctionEntity,BizApparchSystemFunctionEntity> commonMsg,HttpServletResponse response);
}
