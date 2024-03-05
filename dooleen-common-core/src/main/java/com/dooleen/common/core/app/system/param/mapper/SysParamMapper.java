package com.dooleen.common.core.app.system.param.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.param.entity.SysParamEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-14 16:30:33
 * @Description : 系统参数管理DAO
 * @Author : apple
 * @Update: 2020-06-14 16:30:33
 */
@Service
public interface SysParamMapper extends BaseMapper<SysParamEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysParamEntity> querySysParamByCondition(@Param("sysParamEntity") SysParamEntity sysParamEntity);
}
