package com.dooleen.service.system.micro.component.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.micro.component.entity.SysMicroComponentEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-11-24 20:55:40
 * @Description : 组件管理DAO
 * @Author : apple
 * @Update: 2021-11-24 20:55:40
 */
@Service
public interface SysMicroComponentMapper extends BaseMapper<SysMicroComponentEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysMicroComponentEntity> querySysMicroComponentByCondition(@Param("sysMicroComponentEntity") SysMicroComponentEntity sysMicroComponentEntity);
}
