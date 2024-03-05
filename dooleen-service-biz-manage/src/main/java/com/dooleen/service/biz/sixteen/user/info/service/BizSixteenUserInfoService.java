package com.dooleen.service.biz.sixteen.user.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.biz.sixteen.user.info.entity.BizSixteenUserInfoEntity;
import com.dooleen.common.core.app.system.user.role.entity.SysRoleEntity;
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
 * @CreateDate : 2021-05-08 22:00:24
 * @Description : 用户信息管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizSixteenUserInfoService extends IService<BizSixteenUserInfoEntity> {

    // 根据用户名获取用户信息
    CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> queryBizSixteenUserInfoByUserName(CommonMsg<BizSixteenUserInfoEntity, SysRoleEntity> commonMsg);

    /**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity, NullEntity> queryBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenUserInfoEntity> queryBizSixteenUserInfoList(CommonMsg<NullEntity,BizSixteenUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity,BizSixteenUserInfoEntity> queryBizSixteenUserInfoListPage(CommonMsg<BizSixteenUserInfoEntity,BizSixteenUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity,BizSixteenUserInfoEntity> queryBizSixteenUserInfoListMap(CommonMsg<BizSixteenUserInfoEntity,BizSixteenUserInfoEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity, NullEntity> saveBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,BizSixteenUserInfoEntity> saveBizSixteenUserInfoList(CommonMsg<NullEntity,BizSixteenUserInfoEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity, NullEntity> updateBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity, NullEntity> saveOrUpdateBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenUserInfoEntity>  saveOrUpdateBizSixteenUserInfoList(CommonMsg<NullEntity, BizSixteenUserInfoEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<BizSixteenUserInfoEntity, NullEntity> deleteBizSixteenUserInfo(CommonMsg<BizSixteenUserInfoEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, BizSixteenUserInfoEntity> deleteBizSixteenUserInfoList(CommonMsg<NullEntity, BizSixteenUserInfoEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportBizSixteenUserInfoExcel(CommonMsg<BizSixteenUserInfoEntity,BizSixteenUserInfoEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<BizSixteenUserInfoEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
