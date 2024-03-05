package com.dooleen.service.biz.model.task.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.model.task.item.entity.BizModelTaskItemEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2022-01-04 23:16:29
 * @Description : 模型任务项管理DAO
 * @Author : apple
 * @Update: 2022-01-04 23:16:29
 */
@Service
public interface BizModelTaskItemMapper extends BaseMapper<BizModelTaskItemEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizModelTaskItemEntity> queryBizModelTaskItemByCondition(@Param("bizModelTaskItemEntity") BizModelTaskItemEntity bizModelTaskItemEntity);
}
