package com.dooleen.service.biz.model.task.group.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.model.task.group.entity.BizModelTaskGroupEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2022-01-04 23:10:49
 * @Description : 模型任务组管理DAO
 * @Author : apple
 * @Update: 2022-01-04 23:10:49
 */
@Service
public interface BizModelTaskGroupMapper extends BaseMapper<BizModelTaskGroupEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizModelTaskGroupEntity> queryBizModelTaskGroupByCondition(@Param("bizModelTaskGroupEntity") BizModelTaskGroupEntity bizModelTaskGroupEntity);
}
