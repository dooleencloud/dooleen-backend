package com.dooleen.common.core.app.system.form.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.form.entity.SysDynamicFormEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-05-23 17:56:43
 * @Description : 系统动态表单管理DAO
 * @Author : apple
 * @Update: 2020-05-23 17:56:43
 */
@Service
public interface SysDynamicFormMapper extends BaseMapper<SysDynamicFormEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysDynamicFormEntity> querySysDynamicFormByCondition(String test);
}
