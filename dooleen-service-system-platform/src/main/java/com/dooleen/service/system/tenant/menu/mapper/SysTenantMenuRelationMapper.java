package com.dooleen.service.system.tenant.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.tenant.menu.entity.SysTenantMenuRelationEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-09 19:49:16
 * @Description : 系统租户菜单关系管理DAO
 * @Author : apple
 * @Update: 2020-06-09 19:49:16
 */
public interface SysTenantMenuRelationMapper extends BaseMapper<SysTenantMenuRelationEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysTenantMenuRelationEntity> querySysTenantMenuRelationByCondition(@Param("sysTenantMenuRelationEntity") SysTenantMenuRelationEntity sysTenantMenuRelationEntity);
}
