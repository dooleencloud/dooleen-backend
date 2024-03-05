package com.dooleen.common.core.app.system.msg.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.msg.notice.entity.SysMsgNoticeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-06-05 13:38:58
 * @Description : 消息通知管理DAO
 * @Author : apple
 * @Update: 2021-06-05 13:38:58
 */
@Service
public interface SysMsgNoticeMapper extends BaseMapper<SysMsgNoticeEntity> {

    /**
     * 查询消息提示列表
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("select * from sys_msg_notice where tenant_id = #{sysMsgNoticeEntity.tenantId} " +
            "and (accept_msg_user_name = #{sysMsgNoticeEntity.acceptMsgUserName}  " +
            "or  send_msg_user_name = #{sysMsgNoticeEntity.acceptMsgUserName}) " +
            "ORDER BY create_datetime desc;")
    List<SysMsgNoticeEntity> querySysMsgNoticeList(@Param("sysMsgNoticeEntity") SysMsgNoticeEntity sysMsgNoticeEntity);
    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("select * from sys_msg_notice where tenant_id = #{sysMsgNoticeEntity.tenantId} " +
            "and biz_scene_name = #{sysMsgNoticeEntity.bizSceneName}  " +
            "and ((accept_msg_user_name = #{sysMsgNoticeEntity.acceptMsgUserName}  " +
            "and send_msg_user_name = #{sysMsgNoticeEntity.sendMsgUserName}) " +
            "or (accept_msg_user_name = #{sysMsgNoticeEntity.sendMsgUserName}  " +
            "and send_msg_user_name = #{sysMsgNoticeEntity.acceptMsgUserName})) " +
            "ORDER BY create_datetime desc;")
    List<SysMsgNoticeEntity> querySysMsgList(@Param("sysMsgNoticeEntity") SysMsgNoticeEntity sysMsgNoticeEntity);
}
