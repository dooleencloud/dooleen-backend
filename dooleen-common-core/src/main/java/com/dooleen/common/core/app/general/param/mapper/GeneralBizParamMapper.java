package com.dooleen.common.core.app.general.param.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.param.entity.GeneralBizParamEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-20 16:55:59
 * @Description : 系统参数管理DAO
 * @Author : apple
 * @Update: 2020-06-20 16:55:59
 */
@Service
public interface GeneralBizParamMapper extends BaseMapper<GeneralBizParamEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralBizParamEntity> queryGeneralBizParamByCondition(@Param("generalBizParamEntity") GeneralBizParamEntity generalBizParamEntity);
}
