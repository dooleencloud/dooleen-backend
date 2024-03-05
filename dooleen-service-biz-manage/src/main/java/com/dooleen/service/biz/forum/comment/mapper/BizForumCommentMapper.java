package com.dooleen.service.biz.forum.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.forum.comment.entity.BizForumCommentEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-21 10:17:42
 * @Description : 评论管理DAO
 * @Author : apple
 * @Update: 2021-07-21 10:17:42
 */
@Service
public interface BizForumCommentMapper extends BaseMapper<BizForumCommentEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizForumCommentEntity> queryBizForumCommentByCondition(@Param("bizForumCommentEntity") BizForumCommentEntity bizForumCommentEntity);
}
