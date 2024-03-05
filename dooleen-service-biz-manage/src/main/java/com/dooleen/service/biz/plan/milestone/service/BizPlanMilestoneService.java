package com.dooleen.service.biz.plan.milestone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.plan.milestone.entity.BizPlanMilestoneEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-28 22:02:22
 * @Description : 里程碑信息维护服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizPlanMilestoneService extends IService<BizPlanMilestoneEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity, NullEntity> queryBizPlanMilestone(CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizPlanMilestoneEntity> queryBizPlanMilestoneList(CommonMsg<NullEntity,BizPlanMilestoneEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity> queryBizPlanMilestoneListPage(CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity> queryBizPlanMilestoneListMap(CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity, NullEntity> saveBizPlanMilestone(CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizPlanMilestoneEntity> saveBizPlanMilestoneList(CommonMsg<NullEntity,BizPlanMilestoneEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity, NullEntity> updateBizPlanMilestone(CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity, NullEntity> saveOrUpdateBizPlanMilestone(CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizPlanMilestoneEntity>  saveOrUpdateBizPlanMilestoneList(CommonMsg<NullEntity, BizPlanMilestoneEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizPlanMilestoneEntity, NullEntity> deleteBizPlanMilestone(CommonMsg<BizPlanMilestoneEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizPlanMilestoneEntity> deleteBizPlanMilestoneList(CommonMsg<NullEntity, BizPlanMilestoneEntity> commonMsg);

		/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizPlanMilestoneExcel(CommonMsg<BizPlanMilestoneEntity,BizPlanMilestoneEntity> commonMsg,HttpServletResponse response);
}
