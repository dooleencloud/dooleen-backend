package com.dooleen.service.biz.sixteen.user.pf.exch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.biz.sixteen.user.pf.exch.entity.BizSixteenUserPfExchEntity;
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
 * @CreateDate : 2021-05-08 21:12:00
 * @Description : 用户16要素结果管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizSixteenUserPfExchService extends IService<BizSixteenUserPfExchEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity, NullEntity> queryBizSixteenUserPfExch(CommonMsg<BizSixteenUserPfExchEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenUserPfExchEntity> queryBizSixteenUserPfExchList(CommonMsg<NullEntity,BizSixteenUserPfExchEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity,BizSixteenUserPfExchEntity> queryBizSixteenUserPfExchListPage(CommonMsg<BizSixteenUserPfExchEntity,BizSixteenUserPfExchEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity,BizSixteenUserPfExchEntity> queryBizSixteenUserPfExchListMap(CommonMsg<BizSixteenUserPfExchEntity,BizSixteenUserPfExchEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity, NullEntity> saveBizSixteenUserPfExch(CommonMsg<BizSixteenUserPfExchEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenUserPfExchEntity> saveBizSixteenUserPfExchList(CommonMsg<NullEntity,BizSixteenUserPfExchEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity, NullEntity> updateBizSixteenUserPfExch(CommonMsg<BizSixteenUserPfExchEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity, NullEntity> saveOrUpdateBizSixteenUserPfExch(CommonMsg<BizSixteenUserPfExchEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenUserPfExchEntity>  saveOrUpdateBizSixteenUserPfExchList(CommonMsg<NullEntity, BizSixteenUserPfExchEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserPfExchEntity, NullEntity> deleteBizSixteenUserPfExch(CommonMsg<BizSixteenUserPfExchEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenUserPfExchEntity> deleteBizSixteenUserPfExchList(CommonMsg<NullEntity, BizSixteenUserPfExchEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizSixteenUserPfExchExcel(CommonMsg<BizSixteenUserPfExchEntity,BizSixteenUserPfExchEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<BizSixteenUserPfExchEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
