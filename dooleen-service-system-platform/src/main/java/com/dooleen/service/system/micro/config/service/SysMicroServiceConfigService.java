package com.dooleen.service.system.micro.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.system.micro.config.entity.SysMicroServiceConfigEntity;
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
 * @CreateDate : 2021-11-24 20:51:35
 * @Description : 服务配置管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysMicroServiceConfigService extends IService<SysMicroServiceConfigEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity, NullEntity> querySysMicroServiceConfig(CommonMsg<SysMicroServiceConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysMicroServiceConfigEntity> querySysMicroServiceConfigList(CommonMsg<NullEntity,SysMicroServiceConfigEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity,SysMicroServiceConfigEntity> querySysMicroServiceConfigListPage(CommonMsg<SysMicroServiceConfigEntity,SysMicroServiceConfigEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity,SysMicroServiceConfigEntity> querySysMicroServiceConfigListMap(CommonMsg<SysMicroServiceConfigEntity,SysMicroServiceConfigEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity, NullEntity> saveSysMicroServiceConfig(CommonMsg<SysMicroServiceConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysMicroServiceConfigEntity> saveSysMicroServiceConfigList(CommonMsg<NullEntity,SysMicroServiceConfigEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity, NullEntity> updateSysMicroServiceConfig(CommonMsg<SysMicroServiceConfigEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity, NullEntity> saveOrUpdateSysMicroServiceConfig(CommonMsg<SysMicroServiceConfigEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMicroServiceConfigEntity>  saveOrUpdateSysMicroServiceConfigList(CommonMsg<NullEntity, SysMicroServiceConfigEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMicroServiceConfigEntity, NullEntity> deleteSysMicroServiceConfig(CommonMsg<SysMicroServiceConfigEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMicroServiceConfigEntity> deleteSysMicroServiceConfigList(CommonMsg<NullEntity, SysMicroServiceConfigEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysMicroServiceConfigExcel(CommonMsg<SysMicroServiceConfigEntity,SysMicroServiceConfigEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<SysMicroServiceConfigEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
