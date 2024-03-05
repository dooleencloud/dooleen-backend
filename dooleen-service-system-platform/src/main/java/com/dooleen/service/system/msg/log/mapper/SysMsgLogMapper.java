package com.dooleen.service.system.msg.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.msg.log.entity.SysMsgLogEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-24 17:47:37
 * @Description : 消息日志管理DAO
 * @Author : apple
 * @Update: 2020-07-24 17:47:37
 */
public interface SysMsgLogMapper extends BaseMapper<SysMsgLogEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysMsgLogEntity> querySysMsgLogByCondition(@Param("sysMsgLogEntity") SysMsgLogEntity sysMsgLogEntity);
}
