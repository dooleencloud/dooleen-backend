package com.dooleen.service.system.send.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-11-07 22:12:08
 * @Description : 敏感词管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysSendLogService extends IService<SysSendLogEntity> {

	/**
	 *
	 * 根据主键查询记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity, NullEntity> querySysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);

	/**
	 *
	 * 获取所有记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysSendLogEntity> querySysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg);

	/**
	 *
	 * 根据条件分页查询
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity,SysSendLogEntity> querySysSendLogListPage(CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg);

	/**
	 *
	 * 根据条件查询Map集合
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity,SysSendLogEntity> querySysSendLogListMap(CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg);

	/**
	 *
	 * 新增记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity, NullEntity> saveSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);

	/**
	 *
	 * 新增多条记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysSendLogEntity> saveSysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg);

	/**
	 *
	 * 根据主键修改记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity, NullEntity> updateSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);

	/**
	 *
	 * 如果存在修改录，不存在就新增记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity, NullEntity> saveOrUpdateSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);
	/**
	 *
	 * 批量添加或更新记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysSendLogEntity>  saveOrUpdateSysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg);

	/**
	 *
	 * 根据主键删除记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<SysSendLogEntity, NullEntity> deleteSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg);

	/**
	 *
	 * 删除多条记录
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysSendLogEntity> deleteSysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg);

	/**
	 * 导出数据
	 *
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg
	 * @Return :
	 */
	void exportSysSendLogExcel(CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg, HttpServletResponse response);
}
