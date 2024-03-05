package com.dooleen.service.general.apparch.level.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.apparch.level.entity.GeneralApparchLevelEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-31 16:35:21
 * @Description : 应用架构层级管理DAO
 * @Author : apple
 * @Update: 2020-08-31 16:35:21
 */
public interface GeneralApparchLevelMapper extends BaseMapper<GeneralApparchLevelEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralApparchLevelEntity> queryGeneralApparchLevelByCondition(@Param("generalApparchLevelEntity") GeneralApparchLevelEntity generalApparchLevelEntity);
}
