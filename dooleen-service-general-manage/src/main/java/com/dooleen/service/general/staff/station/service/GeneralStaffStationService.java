package com.dooleen.service.general.staff.station.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-10-10 10:15:54
 * @Description : 工位信息表服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralStaffStationService extends IService<GeneralStaffStationEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity, NullEntity> queryGeneralStaffStation(CommonMsg<GeneralStaffStationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralStaffStationEntity> queryGeneralStaffStationList(CommonMsg<NullEntity,GeneralStaffStationEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity,GeneralStaffStationEntity> queryGeneralStaffStationListPage(CommonMsg<GeneralStaffStationEntity,GeneralStaffStationEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity,GeneralStaffStationEntity> queryGeneralStaffStationListMap(CommonMsg<GeneralStaffStationEntity,GeneralStaffStationEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity, NullEntity> saveGeneralStaffStation(CommonMsg<GeneralStaffStationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,GeneralStaffStationEntity> saveGeneralStaffStationList(CommonMsg<NullEntity,GeneralStaffStationEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity, NullEntity> updateGeneralStaffStation(CommonMsg<GeneralStaffStationEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity, NullEntity> saveOrUpdateGeneralStaffStation(CommonMsg<GeneralStaffStationEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralStaffStationEntity>  saveOrUpdateGeneralStaffStationList(CommonMsg<NullEntity, GeneralStaffStationEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<GeneralStaffStationEntity, NullEntity> deleteGeneralStaffStation(CommonMsg<GeneralStaffStationEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, GeneralStaffStationEntity> deleteGeneralStaffStationList(CommonMsg<NullEntity, GeneralStaffStationEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportGeneralStaffStationExcel(CommonMsg<GeneralStaffStationEntity,GeneralStaffStationEntity> commonMsg,HttpServletResponse response);
}
