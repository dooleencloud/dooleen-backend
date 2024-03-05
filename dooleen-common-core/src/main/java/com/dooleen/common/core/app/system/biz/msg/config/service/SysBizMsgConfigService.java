package com.dooleen.common.core.app.system.biz.msg.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.system.biz.msg.config.entity.SysBizMsgConfigEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-28 14:47:34
 * @Description : 业务消息配置管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysBizMsgConfigService extends IService<SysBizMsgConfigEntity> {

	@Cacheable(value = "sysBizMsgConfig", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.bizName", condition = "#commonMsg.head.cacheAble eq 'true'")
	CommonMsg<SysBizMsgConfigEntity, NullEntity> querySysBizMsgConfigByBizName(CommonMsg<SysBizMsgConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity, NullEntity> querySysBizMsgConfig(CommonMsg<SysBizMsgConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysBizMsgConfigEntity> querySysBizMsgConfigList(CommonMsg<NullEntity,SysBizMsgConfigEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity,SysBizMsgConfigEntity> querySysBizMsgConfigListPage(CommonMsg<SysBizMsgConfigEntity,SysBizMsgConfigEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity,SysBizMsgConfigEntity> querySysBizMsgConfigListMap(CommonMsg<SysBizMsgConfigEntity,SysBizMsgConfigEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity, NullEntity> saveSysBizMsgConfig(CommonMsg<SysBizMsgConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysBizMsgConfigEntity> saveSysBizMsgConfigList(CommonMsg<NullEntity,SysBizMsgConfigEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity, NullEntity> updateSysBizMsgConfig(CommonMsg<SysBizMsgConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity, NullEntity> saveOrUpdateSysBizMsgConfig(CommonMsg<SysBizMsgConfigEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysBizMsgConfigEntity>  saveOrUpdateSysBizMsgConfigList(CommonMsg<NullEntity, SysBizMsgConfigEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysBizMsgConfigEntity, NullEntity> deleteSysBizMsgConfig(CommonMsg<SysBizMsgConfigEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysBizMsgConfigEntity> deleteSysBizMsgConfigList(CommonMsg<NullEntity, SysBizMsgConfigEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysBizMsgConfigExcel(CommonMsg<SysBizMsgConfigEntity,SysBizMsgConfigEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<SysBizMsgConfigEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
