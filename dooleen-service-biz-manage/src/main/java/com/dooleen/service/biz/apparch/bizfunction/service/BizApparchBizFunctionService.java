package com.dooleen.service.biz.apparch.bizfunction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.apparch.bizfunction.entity.BizApparchBizFunctionEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-28 11:30:33
 * @Description : 业务功能点管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizApparchBizFunctionService extends IService<BizApparchBizFunctionEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity, NullEntity> queryBizApparchBizFunction(CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizApparchBizFunctionEntity> queryBizApparchBizFunctionList(CommonMsg<NullEntity,BizApparchBizFunctionEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity> queryBizApparchBizFunctionListPage(CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity> queryBizApparchBizFunctionListMap(CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity, NullEntity> saveBizApparchBizFunction(CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizApparchBizFunctionEntity> saveBizApparchBizFunctionList(CommonMsg<NullEntity,BizApparchBizFunctionEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity, NullEntity> updateBizApparchBizFunction(CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity, NullEntity> saveOrUpdateBizApparchBizFunction(CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizApparchBizFunctionEntity>  saveOrUpdateBizApparchBizFunctionList(CommonMsg<NullEntity, BizApparchBizFunctionEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchBizFunctionEntity, NullEntity> deleteBizApparchBizFunction(CommonMsg<BizApparchBizFunctionEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizApparchBizFunctionEntity> deleteBizApparchBizFunctionList(CommonMsg<NullEntity, BizApparchBizFunctionEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizApparchBizFunctionExcel(CommonMsg<BizApparchBizFunctionEntity,BizApparchBizFunctionEntity> commonMsg,HttpServletResponse response);
}
