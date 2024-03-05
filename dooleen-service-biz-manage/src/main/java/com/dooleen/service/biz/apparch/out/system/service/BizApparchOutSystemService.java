package com.dooleen.service.biz.apparch.out.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.apparch.out.system.entity.BizApparchOutSystemEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-09-01 16:24:16
 * @Description : 外联系统管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizApparchOutSystemService extends IService<BizApparchOutSystemEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity, NullEntity> queryBizApparchOutSystem(CommonMsg<BizApparchOutSystemEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizApparchOutSystemEntity> queryBizApparchOutSystemList(CommonMsg<NullEntity,BizApparchOutSystemEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity,BizApparchOutSystemEntity> queryBizApparchOutSystemListPage(CommonMsg<BizApparchOutSystemEntity,BizApparchOutSystemEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity,BizApparchOutSystemEntity> queryBizApparchOutSystemListMap(CommonMsg<BizApparchOutSystemEntity,BizApparchOutSystemEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity, NullEntity> saveBizApparchOutSystem(CommonMsg<BizApparchOutSystemEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizApparchOutSystemEntity> saveBizApparchOutSystemList(CommonMsg<NullEntity,BizApparchOutSystemEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity, NullEntity> updateBizApparchOutSystem(CommonMsg<BizApparchOutSystemEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity, NullEntity> saveOrUpdateBizApparchOutSystem(CommonMsg<BizApparchOutSystemEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizApparchOutSystemEntity>  saveOrUpdateBizApparchOutSystemList(CommonMsg<NullEntity, BizApparchOutSystemEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizApparchOutSystemEntity, NullEntity> deleteBizApparchOutSystem(CommonMsg<BizApparchOutSystemEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizApparchOutSystemEntity> deleteBizApparchOutSystemList(CommonMsg<NullEntity, BizApparchOutSystemEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizApparchOutSystemExcel(CommonMsg<BizApparchOutSystemEntity,BizApparchOutSystemEntity> commonMsg,HttpServletResponse response);
}
