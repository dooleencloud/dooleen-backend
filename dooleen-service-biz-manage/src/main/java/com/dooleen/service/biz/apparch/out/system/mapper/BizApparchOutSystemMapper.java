package com.dooleen.service.biz.apparch.out.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.biz.apparch.out.system.entity.BizApparchOutSystemEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-09-01 16:24:16
 * @Description : 外联系统管理DAO
 * @Author : apple
 * @Update: 2020-09-01 16:24:16
 */
public interface BizApparchOutSystemMapper extends BaseMapper<BizApparchOutSystemEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<BizApparchOutSystemEntity> queryBizApparchOutSystemByCondition(@Param("bizApparchOutSystemEntity") BizApparchOutSystemEntity bizApparchOutSystemEntity);
}
