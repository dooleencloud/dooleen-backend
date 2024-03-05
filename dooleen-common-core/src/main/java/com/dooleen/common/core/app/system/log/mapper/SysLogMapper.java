package com.dooleen.common.core.app.system.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.log.entity.SysLogEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-25 11:07:25
 * @Description : 系统日志管理DAO
 * @Author : apple
 * @Update: 2020-07-25 11:07:25
 */
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysLogEntity> querySysLogByCondition(@Param("sysLogEntity") SysLogEntity sysLogEntity);
}
