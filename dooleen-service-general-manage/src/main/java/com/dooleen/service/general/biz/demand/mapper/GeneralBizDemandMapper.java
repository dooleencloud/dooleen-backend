package com.dooleen.service.general.biz.demand.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.biz.demand.entity.GeneralBizDemandEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-03 15:42:10
 * @Description : 业务需求管理DAO
 * @Author : apple
 * @Update: 2020-07-03 15:42:10
 */
public interface GeneralBizDemandMapper extends BaseMapper<GeneralBizDemandEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralBizDemandEntity> queryGeneralBizDemandByCondition(@Param("generalBizDemandEntity") GeneralBizDemandEntity generalBizDemandEntity);
}
