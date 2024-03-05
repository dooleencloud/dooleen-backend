package com.dooleen.service.biz.apparch.system.function.mapper;

import java.util.List;

import com.dooleen.service.biz.apparch.system.function.entity.BizApparchSystemFunctionEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-09-02 11:54:57
 * @Description : 系统功能点管理DAO
 * @Author : apple
 * @Update: 2020-09-02 11:54:57
 */
public interface BizApparchSystemFunctionMapper extends BaseMapper<BizApparchSystemFunctionEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizApparchSystemFunctionEntity> queryBizApparchSystemFunctionByCondition(@Param("bizApparchSystemFunctionEntity") BizApparchSystemFunctionEntity bizApparchSystemFunctionEntity);
}
