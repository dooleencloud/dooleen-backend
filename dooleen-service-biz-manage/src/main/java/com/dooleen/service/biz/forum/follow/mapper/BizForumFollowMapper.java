package com.dooleen.service.biz.forum.follow.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.forum.follow.entity.BizForumFollowEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-21 10:19:30
 * @Description : 用户关注管理DAO
 * @Author : apple
 * @Update: 2021-07-21 10:19:30
 */
@Service
public interface BizForumFollowMapper extends BaseMapper<BizForumFollowEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("<script>"
            +"	SELECT funsTotal,followTotal FROM "
            +" (SELECT count(*) funsTotal FROM biz_forum_follow WHERE tenant_id = #{bizForumFollowEntity.tenantId} "
            +" AND be_follow_user_name = #{bizForumFollowEntity.userName} and follow_status = '1') as a,"
            +" (SELECT count(*) followTotal FROM biz_forum_follow WHERE tenant_id = #{bizForumFollowEntity.tenantId} "
            +" AND user_name = #{bizForumFollowEntity.userName} and follow_status = '1') as b"
            +"</script>")
    BizForumFollowEntity queryBizForumFollowTotal(@Param("bizForumFollowEntity") BizForumFollowEntity bizForumFollowEntity);
}
