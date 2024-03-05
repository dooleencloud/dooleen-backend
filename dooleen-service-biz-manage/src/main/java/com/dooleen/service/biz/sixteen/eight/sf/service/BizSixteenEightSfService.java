package com.dooleen.service.biz.sixteen.eight.sf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.sixteen.eight.sf.entity.BizSixteenEightSfEntity;
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
 * @CreateDate : 2021-05-08 20:36:05
 * @Description : 8SF要素管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizSixteenEightSfService extends IService<BizSixteenEightSfEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity, NullEntity> queryBizSixteenEightSf(CommonMsg<BizSixteenEightSfEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenEightSfEntity> queryBizSixteenEightSfList(CommonMsg<NullEntity,BizSixteenEightSfEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity,BizSixteenEightSfEntity> queryBizSixteenEightSfListPage(CommonMsg<BizSixteenEightSfEntity,BizSixteenEightSfEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity,BizSixteenEightSfEntity> queryBizSixteenEightSfListMap(CommonMsg<BizSixteenEightSfEntity,BizSixteenEightSfEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity, NullEntity> saveBizSixteenEightSf(CommonMsg<BizSixteenEightSfEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenEightSfEntity> saveBizSixteenEightSfList(CommonMsg<NullEntity,BizSixteenEightSfEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity, NullEntity> updateBizSixteenEightSf(CommonMsg<BizSixteenEightSfEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity, NullEntity> saveOrUpdateBizSixteenEightSf(CommonMsg<BizSixteenEightSfEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenEightSfEntity>  saveOrUpdateBizSixteenEightSfList(CommonMsg<NullEntity, BizSixteenEightSfEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenEightSfEntity, NullEntity> deleteBizSixteenEightSf(CommonMsg<BizSixteenEightSfEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenEightSfEntity> deleteBizSixteenEightSfList(CommonMsg<NullEntity, BizSixteenEightSfEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizSixteenEightSfExcel(CommonMsg<BizSixteenEightSfEntity,BizSixteenEightSfEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<BizSixteenEightSfEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
