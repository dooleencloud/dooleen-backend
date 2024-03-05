package com.dooleen.service.biz.question.send.result.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.question.send.result.entity.BizQuestionSendResultEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-03-26 10:40:07
 * @Description : 问卷下发答题结果DAO
 * @Author : apple
 * @Update: 2021-03-26 10:40:07
 */
@Service
public interface BizQuestionSendResultMapper extends BaseMapper<BizQuestionSendResultEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizQuestionSendResultEntity> queryBizQuestionSendResultByCondition(@Param("bizQuestionSendResultEntity") BizQuestionSendResultEntity bizQuestionSendResultEntity);
}
