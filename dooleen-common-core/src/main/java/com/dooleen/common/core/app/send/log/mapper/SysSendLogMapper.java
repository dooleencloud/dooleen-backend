package com.dooleen.common.core.app.send.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Learning平台
 * @Project No : Learning
 * @Version : 1.0.0
 * @CreateDate : 2020-11-07 22:12:08
 * @Description : 敏感词管理DAO
 * @Author : apple
 * @Update: 2020-11-07 22:12:08
 */
@Service
public interface SysSendLogMapper extends BaseMapper<SysSendLogEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysSendLogEntity> querySysSendLogByCondition(@Param("SysSendLogEntity") SysSendLogEntity SysSendLogEntity);
}
