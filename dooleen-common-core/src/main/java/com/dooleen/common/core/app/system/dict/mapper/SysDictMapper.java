package com.dooleen.common.core.app.system.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.dict.entity.SysDictEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-20 11:14:37
 * @Description : 系统字典管理DAO
 * @Author : apple
 * @Update: 2020-06-20 11:14:37
 */
public interface SysDictMapper extends BaseMapper<SysDictEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysDictEntity> querySysDictByCondition(@Param("sysDictEntity") SysDictEntity sysDictEntity);
}
