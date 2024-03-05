package com.dooleen.common.core.app.system.user.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-07 21:45:18
 * @Description : 系统用户角色关系管理DAO
 * @Author : apple
 * @Update: 2020-06-07 21:45:18
 */
@Service
public interface SysUserRoleRelationMapper extends BaseMapper<SysUserRoleRelationEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysUserRoleRelationEntity> querySysUserRoleRelationByCondition(String test);

    @Select("<script>"
    		+ "select role_id AS roleId from sys_user_role_relation where tenant_id = #{tenantId} and user_id = #{userId}"
    		+ "</script>")
	List<String> selectRoleIdsByUserIds(String tenantId, String userId);
}
