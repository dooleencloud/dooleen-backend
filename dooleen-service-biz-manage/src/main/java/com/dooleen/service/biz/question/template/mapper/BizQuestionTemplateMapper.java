package com.dooleen.service.biz.question.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.question.template.entity.BizQuestionTemplateEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:31:07
 * @Description : 问卷模板管理DAO
 * @Author : apple
 * @Update: 2021-02-22 11:31:07
 */
@Service
public interface BizQuestionTemplateMapper extends BaseMapper<BizQuestionTemplateEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizQuestionTemplateEntity> queryBizQuestionTemplateByCondition(@Param("bizQuestionTemplateEntity") BizQuestionTemplateEntity bizQuestionTemplateEntity);
}
