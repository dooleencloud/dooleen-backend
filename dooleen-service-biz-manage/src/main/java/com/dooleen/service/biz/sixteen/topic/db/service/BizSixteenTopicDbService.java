package com.dooleen.service.biz.sixteen.topic.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.sixteen.topic.db.entity.BizSixteenTopicDbEntity;
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
 * @CreateDate : 2021-05-08 21:06:08
 * @Description : 16PF题目管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizSixteenTopicDbService extends IService<BizSixteenTopicDbEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity, NullEntity> queryBizSixteenTopicDb(CommonMsg<BizSixteenTopicDbEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenTopicDbEntity> queryBizSixteenTopicDbList(CommonMsg<NullEntity,BizSixteenTopicDbEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity,BizSixteenTopicDbEntity> queryBizSixteenTopicDbListPage(CommonMsg<BizSixteenTopicDbEntity,BizSixteenTopicDbEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity,BizSixteenTopicDbEntity> queryBizSixteenTopicDbListMap(CommonMsg<BizSixteenTopicDbEntity,BizSixteenTopicDbEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity, NullEntity> saveBizSixteenTopicDb(CommonMsg<BizSixteenTopicDbEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenTopicDbEntity> saveBizSixteenTopicDbList(CommonMsg<NullEntity,BizSixteenTopicDbEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity, NullEntity> updateBizSixteenTopicDb(CommonMsg<BizSixteenTopicDbEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity, NullEntity> saveOrUpdateBizSixteenTopicDb(CommonMsg<BizSixteenTopicDbEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenTopicDbEntity>  saveOrUpdateBizSixteenTopicDbList(CommonMsg<NullEntity, BizSixteenTopicDbEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenTopicDbEntity, NullEntity> deleteBizSixteenTopicDb(CommonMsg<BizSixteenTopicDbEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenTopicDbEntity> deleteBizSixteenTopicDbList(CommonMsg<NullEntity, BizSixteenTopicDbEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizSixteenTopicDbExcel(CommonMsg<BizSixteenTopicDbEntity,BizSixteenTopicDbEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<BizSixteenTopicDbEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
