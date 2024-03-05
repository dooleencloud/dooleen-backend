package com.dooleen.service.biz.question.send.list.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.question.send.list.entity.BizQuestionSendListEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-22 11:29:58
 * @Description : 下发问卷管理DAO
 * @Author : apple
 * @Update: 2021-02-22 11:29:58
 */
@Service
public interface BizQuestionSendListMapper extends BaseMapper<BizQuestionSendListEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizQuestionSendListEntity> queryBizQuestionSendListByCondition(@Param("bizQuestionSendListEntity") BizQuestionSendListEntity bizQuestionSendListEntity);
}
