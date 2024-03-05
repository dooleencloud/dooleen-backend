package com.dooleen.service.batch.question.sent.list.service.impl;

import java.util.List;

import com.dooleen.common.core.utils.*;
import com.dooleen.service.batch.question.sent.list.entity.BizQuestionTemplateEntity;
import com.dooleen.service.batch.question.sent.list.mapper.BizQuestionSendSubjectMapper;
import com.dooleen.service.batch.question.sent.list.mapper.BizQuestionSubjectConfigMapper;
import com.dooleen.service.batch.question.sent.list.mapper.BizQuestionTemplateMapper;
import com.dooleen.service.batch.question.sent.list.service.BizQuestionSendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.service.batch.question.sent.list.entity.BizQuestionSendListEntity;
import com.dooleen.service.batch.question.sent.list.mapper.BizQuestionSendListMapper;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:29:58
 * @Description : 下发问卷管理服务实现
 * @Author : apple
 * @Update: 2021-02-22 11:29:58
 */
/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizQuestionSendListServiceImpl extends ServiceImpl<BizQuestionSendListMapper, BizQuestionSendListEntity> implements BizQuestionSendListService {

	@Autowired
	private BizQuestionTemplateMapper bizQuestionTemplateMapper;

	@Autowired
	private BizQuestionSubjectConfigMapper bizQuestionSubjectConfigMapper;

	@Autowired
	private  BizQuestionSendListMapper bizQuestionSendListMapper;

	@Autowired
	private BizQuestionSendSubjectMapper bizQuestionSendSubjectMapper;

	private  static String seqPrefix= TableEntityORMEnum.BIZ_QUESTION_SEND_LIST.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_QUESTION_SEND_LIST.getEntityName();

	private  static String subjectSeqPrefix= TableEntityORMEnum.BIZ_QUESTION_SEND_SUBJECT.getSeqPrefix();
	private  static String subjectTableName = TableEntityORMEnum.BIZ_QUESTION_SEND_SUBJECT.getEntityName();
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public void sendUserQuestion() {
		//获取模板配置列表
		QueryWrapper<BizQuestionTemplateEntity> templateQueryWrapper = new QueryWrapper<BizQuestionTemplateEntity>();
		templateQueryWrapper.lambda().ge(BizQuestionTemplateEntity::getValidDate, DateUtils.getShortDateStr());
		List<BizQuestionTemplateEntity> templateEntityList = bizQuestionTemplateMapper.selectList(templateQueryWrapper);
		for(int i = 0; i<templateEntityList.size(); i++){

		}
	}
}
