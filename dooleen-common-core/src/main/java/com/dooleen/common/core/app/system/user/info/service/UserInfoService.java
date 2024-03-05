package com.dooleen.common.core.app.system.user.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowInfoEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程信息管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface UserInfoService<T> extends IService<SysUserInfoEntity> {


	Map<String, SendMsgUserInfoEntity> getHeadImgUrlList(List<T> entityList, String userName);

	Map<String,SendMsgUserInfoEntity> getHeadImgUrlListBySet(String tenantId, List<String> userNameList);
}
