package com.dooleen.common.core.app.system.biz.msg.config.mapper;

import java.util.List;

import com.dooleen.common.core.app.system.biz.msg.config.entity.SysBizMsgConfigEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-28 14:47:34
 * @Description : 业务消息配置管理DAO
 * @Author : apple
 * @Update: 2021-05-28 14:47:34
 */
@Service
public interface SysBizMsgConfigMapper extends BaseMapper<SysBizMsgConfigEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysBizMsgConfigEntity> querySysBizMsgConfigByCondition(@Param("sysBizMsgConfigEntity") SysBizMsgConfigEntity sysBizMsgConfigEntity);
}
