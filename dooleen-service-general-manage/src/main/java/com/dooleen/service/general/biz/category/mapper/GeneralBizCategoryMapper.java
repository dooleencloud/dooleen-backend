package com.dooleen.service.general.biz.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.biz.category.entity.GeneralBizCategoryEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-26 11:12:36
 * @Description : 业务种类管理DAO
 * @Author : apple
 * @Update: 2020-08-26 11:12:36
 */
public interface GeneralBizCategoryMapper extends BaseMapper<GeneralBizCategoryEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralBizCategoryEntity> queryGeneralBizCategoryByCondition(@Param("generalBizCategoryEntity") GeneralBizCategoryEntity generalBizCategoryEntity);
}
