package com.dooleen.service.batch.question.sent.list.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dooleen.service.batch.question.sent.list.entity.BizQuestionSendListEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:29:58
 * @Description : 下发问卷管理服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface BizQuestionSendListService extends IService<BizQuestionSendListEntity> {
	void sendUserQuestion();
}
