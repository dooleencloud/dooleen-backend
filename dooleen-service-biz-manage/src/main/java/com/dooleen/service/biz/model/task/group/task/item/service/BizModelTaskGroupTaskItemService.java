package com.dooleen.service.biz.model.task.group.task.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.model.task.group.task.item.entity.BizModelTaskGroupTaskItemEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2022-01-04 23:20:40
 * @Description : 模型任务组任务项关系管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizModelTaskGroupTaskItemService extends IService<BizModelTaskGroupTaskItemEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> queryBizModelTaskGroupTaskItem(CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemList(CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemListPage(CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemListMap(CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> saveBizModelTaskGroupTaskItem(CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> saveBizModelTaskGroupTaskItemList(CommonMsg<NullEntity,BizModelTaskGroupTaskItemEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> updateBizModelTaskGroupTaskItem(CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> saveOrUpdateBizModelTaskGroupTaskItem(CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity>  saveOrUpdateBizModelTaskGroupTaskItemList(CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> deleteBizModelTaskGroupTaskItem(CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> deleteBizModelTaskGroupTaskItemList(CommonMsg<NullEntity, BizModelTaskGroupTaskItemEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizModelTaskGroupTaskItemExcel(CommonMsg<BizModelTaskGroupTaskItemEntity,BizModelTaskGroupTaskItemEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<BizModelTaskGroupTaskItemEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
