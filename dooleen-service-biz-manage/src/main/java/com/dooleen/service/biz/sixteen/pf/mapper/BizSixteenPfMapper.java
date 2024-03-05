package com.dooleen.service.biz.sixteen.pf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.sixteen.pf.entity.BizSixteenPfEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-05-08 20:29:23
 * @Description : 16PF要素管理DAO
 * @Author : apple
 * @Update: 2021-05-08 20:29:23
 */
@Service
public interface BizSixteenPfMapper extends BaseMapper<BizSixteenPfEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizSixteenPfEntity> queryBizSixteenPfByCondition(@Param("bizSixteenPfEntity") BizSixteenPfEntity bizSixteenPfEntity);
}
