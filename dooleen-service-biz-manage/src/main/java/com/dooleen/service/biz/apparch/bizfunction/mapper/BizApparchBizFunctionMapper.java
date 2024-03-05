package com.dooleen.service.biz.apparch.bizfunction.mapper;

import java.util.List;

import com.dooleen.service.biz.apparch.bizfunction.entity.BizApparchBizFunctionEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-28 11:30:33
 * @Description : 业务功能点管理DAO
 * @Author : apple
 * @Update: 2020-08-28 11:30:33
 */
public interface BizApparchBizFunctionMapper extends BaseMapper<BizApparchBizFunctionEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizApparchBizFunctionEntity> queryBizApparchBizFunctionByCondition(@Param("bizApparchBizFunctionEntity") BizApparchBizFunctionEntity bizApparchBizFunctionEntity);
}
