package com.dooleen.service.system.tool.table.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.tool.table.entity.SysToolTablesEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-21 23:45:59
 * @Description : 系统表DAO
 * @Author : apple
 * @Update: 2020-05-21 23:45:59
 */
public interface SysToolTablesMapper extends BaseMapper<SysToolTablesEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysToolTablesEntity> querySysToolTablesByCondition(String test);
}
