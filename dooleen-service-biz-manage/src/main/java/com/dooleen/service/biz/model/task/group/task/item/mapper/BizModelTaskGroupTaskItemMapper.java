package com.dooleen.service.biz.model.task.group.task.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.model.task.group.task.item.entity.BizModelTaskGroupTaskItemEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2022-01-04 23:20:40
 * @Description : 模型任务组任务项关系管理DAO
 * @Author : apple
 * @Update: 2022-01-04 23:20:40
 */
@Service
public interface BizModelTaskGroupTaskItemMapper extends BaseMapper<BizModelTaskGroupTaskItemEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizModelTaskGroupTaskItemEntity> queryBizModelTaskGroupTaskItemByCondition(@Param("bizModelTaskGroupTaskItemEntity") BizModelTaskGroupTaskItemEntity bizModelTaskGroupTaskItemEntity);
}
