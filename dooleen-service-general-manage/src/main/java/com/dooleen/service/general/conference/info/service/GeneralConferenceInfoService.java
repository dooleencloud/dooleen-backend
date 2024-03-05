package com.dooleen.service.general.conference.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.general.conference.info.entity.GeneralConferenceInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-26 14:09:14
 * @Description : 会议信息表服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralConferenceInfoService extends IService<GeneralConferenceInfoEntity> {

	/**
	 * 根据时间区间查询会议
	 * @param commonMsg
	 * @return
	 */
    CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> queryConferenceDuration(
            CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg);

    /**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity, NullEntity> queryGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralConferenceInfoEntity> queryGeneralConferenceInfoList(CommonMsg<NullEntity,GeneralConferenceInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity> queryGeneralConferenceInfoListPage(CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity> queryGeneralConferenceInfoListMap(CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity, NullEntity> saveGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralConferenceInfoEntity> saveGeneralConferenceInfoList(CommonMsg<NullEntity,GeneralConferenceInfoEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity, NullEntity> updateGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity, NullEntity> saveOrUpdateGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralConferenceInfoEntity>  saveOrUpdateGeneralConferenceInfoList(CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralConferenceInfoEntity, NullEntity> deleteGeneralConferenceInfo(CommonMsg<GeneralConferenceInfoEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralConferenceInfoEntity> deleteGeneralConferenceInfoList(CommonMsg<NullEntity, GeneralConferenceInfoEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportGeneralConferenceInfoExcel(CommonMsg<GeneralConferenceInfoEntity,GeneralConferenceInfoEntity> commonMsg,HttpServletResponse response);

    CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> createTencentMeeting(CommonMsg<GeneralConferenceInfoEntity, GeneralConferenceInfoEntity> commonMsg);
}
