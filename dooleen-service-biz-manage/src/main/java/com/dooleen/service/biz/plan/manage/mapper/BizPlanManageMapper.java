package com.dooleen.service.biz.plan.manage.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.plan.manage.entity.BizPlanManageEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-28 22:00:44
 * @Description : 计划管理维护DAO
 * @Author : apple
 * @Update: 2021-04-28 22:00:44
 */
@Service
public interface BizPlanManageMapper extends BaseMapper<BizPlanManageEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("update biz_plan_manage set iteration_name=#{bizPlanManageEntity.iterationName} WHERE tenant_id=#{bizPlanManageEntity.tenantId} and project_no=#{bizPlanManageEntity.projectNo} and iteration_no=#{bizPlanManageEntity.iterationNo}")
    void updateByCondition(@Param("bizPlanManageEntity") BizPlanManageEntity bizPlanManageEntity);
}
