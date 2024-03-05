package com.dooleen.common.core.app.general.flow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowNodeConfigEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-29 09:17:12
 * @Description : 流程节点配置管理DAO
 * @Author : apple
 * @Update: 2020-06-29 09:17:12
 */
public interface GeneralFlowNodeConfigMapper extends BaseMapper<GeneralFlowNodeConfigEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralFlowNodeConfigEntity> queryGeneralFlowNodeConfigByCondition(@Param("generalFlowNodeConfigEntity") GeneralFlowNodeConfigEntity generalFlowNodeConfigEntity);
}
