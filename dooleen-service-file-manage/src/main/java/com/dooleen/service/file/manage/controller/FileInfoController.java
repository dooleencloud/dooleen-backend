
package com.dooleen.service.file.manage.controller;

import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import com.dooleen.common.core.app.general.file.info.service.GeneralFileInfoService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.file.manage.fastdfs.util.CommonFileUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-28 11:30:33
 * @Description : 业务功能点管理Controller
 * @Author : apple
 * @Update: 2020-08-28 11:30:33
 */
@Slf4j
@RestController
@Api(tags = "业务功能点管理相关接口")
@RequestMapping("/file/office/")
public class FileInfoController {
	@Autowired
	private CommonFileUtil fileUtil;

	@Autowired
	private GeneralFileInfoService seneralFileInfoService;


	/**
	 * 根据文件ID 查询文件Key值
	 *
	 * @Author name
	 * @CreateTime 2020/04/21 16:34
	 * @Param fileId
	 * @Return String
	 */
	@RequestMapping("getFileKey")
	@SecretAnnotation(decode=false,encode=false)
	public String getFileKey(@RequestParam("tenantId") String tenantId , @RequestParam("fileId") String fileId) {
		log.info("==>>获取参数==="+tenantId);
		GeneralFileInfoEntity generalFileInfoEntity = new GeneralFileInfoEntity();
		generalFileInfoEntity.setTenantId(tenantId);
		generalFileInfoEntity.setId(fileId);
		GeneralFileHistoryEntity generalFileHistoryEntity = new GeneralFileHistoryEntity();
		CommonMsg<GeneralFileInfoEntity, GeneralFileHistoryEntity> fileCommonMsg = CreateCommonMsg
				.getCommonMsg(generalFileInfoEntity, generalFileHistoryEntity);
		fileCommonMsg = seneralFileInfoService.queryGeneralFileInfoAndHistory(fileCommonMsg);
		generalFileInfoEntity = fileCommonMsg.getBody().getSingleBody();

		// 如果返回不成功，直接返回错误到页面
		log.info(">>>返回文件key信息 {}", generalFileInfoEntity.getFileSign());
		return generalFileInfoEntity.getFileSign();
	}
}
