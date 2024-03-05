package com.dooleen.service.system.tenant.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 15:51:08
 * @Description : 租户管理DAO
 * @Author : apple
 * @Update: 2020-06-04 15:51:08
 */
@Service
public interface SysTenantInfoMapper extends BaseMapper<SysTenantInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysTenantInfoEntity> querySysTenantInfoByCondition(String test);
}
