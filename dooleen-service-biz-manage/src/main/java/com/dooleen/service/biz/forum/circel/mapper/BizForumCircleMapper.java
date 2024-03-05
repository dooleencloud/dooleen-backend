package com.dooleen.service.biz.forum.circel.mapper;

import java.util.List;

import com.dooleen.service.biz.forum.posts.entity.BizForumPostsEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.forum.circel.entity.BizForumCircleEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-21 10:19:44
 * @Description : 圈子管理DAO
 * @Author : apple
 * @Update: 2021-07-21 10:19:44
 */
@Service
public interface BizForumCircleMapper extends BaseMapper<BizForumCircleEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizForumCircleEntity> queryBizForumCircleByCondition(@Param("bizForumCircleEntity") BizForumCircleEntity bizForumCircleEntity);

    /**
     * 查询圈子对应的第一条动态
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param
     */
    @Select("<script>"
            +"	SELECT "
            +"		follow.be_follow_user_name, "
            +"		posts.* "
            +"	FROM "
            +"		biz_forum_posts posts, "
            +"		( SELECT be_follow_user_name, user_name FROM biz_forum_follow WHERE tenant_id = #{tenantId} "
            +"			AND be_follow_user_name IN "
            +"  (<foreach collection='ids' separator=',' item='id'>"
            +       "#{id} "
            +   "</foreach>)  and follow_status='1') follow, "
            +"		( SELECT MAX( create_datetime ) create_datetime FROM biz_forum_posts WHERE tenant_id = #{tenantId} "
            +"			GROUP BY deploy_user_name ) tmp_posts "
            +"	WHERE"
            +"		posts.tenant_id = #{tenantId} "
            +"		AND posts.create_datetime = tmp_posts.create_datetime  "
            +"		AND posts.valid_flag = '1' "
            +"		AND posts.deploy_user_name = follow.user_name "
            +"	ORDER BY "
            +"	    posts.create_datetime DESC "
            +"</script>")
//    @Select("<script>"
//            +"SELECT"
//            +"	t.* "
//            +"FROM"
//            +"	("
//            +"	SELECT "
//            +"		follow.be_follow_user_name, "
//            +"		posts.* "
//            +"	FROM "
//            +"		biz_forum_posts posts, "
//            +"		( SELECT be_follow_user_name, user_name FROM biz_forum_follow WHERE tenant_id = #{tenantId} "
//            +"			AND be_follow_user_name IN "
//            +"          (<foreach collection='ids' separator=',' item='id'>"
//            +               "#{id} "
//            +           "</foreach>)  and follow_status='1') follow, "
//            +"		( SELECT MAX( create_datetime ) create_datetime FROM biz_forum_posts WHERE tenant_id = #{tenantId} "
//            +"			GROUP BY deploy_user_name ) tmp_posts "
//            +"	WHERE"
//            +"		posts.tenant_id = #{tenantId} "
//            +"		AND posts.create_datetime = tmp_posts.create_datetime  "
//            +"		AND posts.valid_flag = '1' "
//            +"		AND posts.deploy_user_name = follow.user_name "
//            +"	ORDER BY"
//            +"		follow.be_follow_user_name ASC, "
//            +"		posts.create_datetime DESC "
//            +"	) t "
//            +" GROUP BY "
//            +"	be_follow_user_name "
//            +"</script>")
    List<BizForumPostsEntity> queryBizForumPostsByCircleIds(@Param("ids") List<String> ids,@Param("tenantId") String tenantId);

}
