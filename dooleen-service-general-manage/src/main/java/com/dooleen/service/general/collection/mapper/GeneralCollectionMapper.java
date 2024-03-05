package com.dooleen.service.general.collection.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.collection.entity.GeneralCollectionEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-30 19:27:29
 * @Description : 收藏管理DAO
 * @Author : apple
 * @Update: 2021-07-30 19:27:29
 */
@Service
public interface GeneralCollectionMapper extends BaseMapper<GeneralCollectionEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralCollectionEntity> queryGeneralCollectionByCondition(@Param("generalCollectionEntity") GeneralCollectionEntity generalCollectionEntity);
}