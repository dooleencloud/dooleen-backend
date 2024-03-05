package com.dooleen.service.system.msg.notice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.system.msg.notice.entity.SysMsgNoticeEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-06-05 13:38:58
 * @Description : 消息通知管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface SysMsgNoticeService extends IService<SysMsgNoticeEntity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity, NullEntity> querySysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysMsgNoticeEntity> querySysMsgNoticeList(CommonMsg<NullEntity,SysMsgNoticeEntity> commonMsg);

	CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgList(CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity> querySysMsgNoticeListPage(CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity> querySysMsgNoticeListMap(CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity, NullEntity> saveSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,SysMsgNoticeEntity> saveSysMsgNoticeList(CommonMsg<NullEntity,SysMsgNoticeEntity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity, NullEntity> updateSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity, NullEntity> saveOrUpdateSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMsgNoticeEntity>  saveOrUpdateSysMsgNoticeList(CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<SysMsgNoticeEntity, NullEntity> deleteSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, SysMsgNoticeEntity> deleteSysMsgNoticeList(CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void exportSysMsgNoticeExcel(CommonMsg<SysMsgNoticeEntity,SysMsgNoticeEntity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<SysMsgNoticeEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
