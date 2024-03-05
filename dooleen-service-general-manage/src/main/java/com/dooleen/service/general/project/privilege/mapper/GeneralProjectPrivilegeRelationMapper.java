package com.dooleen.service.general.project.privilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.project.privilege.entity.GeneralProjectPrivilegeRelationEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-07 11:20:39
 * @Description : 项目权限关系表DAO
 * @Author : apple
 * @Update: 2020-08-07 11:20:39
 */
public interface GeneralProjectPrivilegeRelationMapper extends BaseMapper<GeneralProjectPrivilegeRelationEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralProjectPrivilegeRelationEntity> queryGeneralProjectPrivilegeRelationByCondition(@Param("generalProjectPrivilegeRelationEntity") GeneralProjectPrivilegeRelationEntity generalProjectPrivilegeRelationEntity);
}
