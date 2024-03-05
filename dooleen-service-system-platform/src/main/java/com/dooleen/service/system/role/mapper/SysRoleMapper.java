package com.dooleen.service.system.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
import com.dooleen.service.system.group.role.entity.SysGroupRoleRelationEntity;
import com.dooleen.service.system.role.entity.SysRoleEntity;
import com.dooleen.service.system.user.group.entity.SysUserGroupRelationEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-07 21:34:01
 * @Description : 系统角色管理DAO
 * @Author : apple
 * @Update: 2020-06-07 21:34:01
 */
@Service
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 根据用户id获取用户的角色列表
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT roles.* FROM sys_role roles, sys_user_role_relation relation WHERE "
    		+ "roles.tenant_id=#{sysUserRoleRelationEntity.tenantId} "
    		+ "and relation.tenant_id=#{sysUserRoleRelationEntity.tenantId} "
    		+ "and relation.user_id=#{sysUserRoleRelationEntity.userId} "
    		+ "and roles.id = relation.role_id "
    		+ "and roles.valid_flag='1'")
    List<SysRoleEntity> querySysRoleByUserId(@Param("sysUserRoleRelationEntity") SysUserRoleRelationEntity sysUserRoleRelationEntity);
    
    /**
     * 根据用户组id获取用户的角色列表
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT roles.* FROM sys_role roles, sys_group_role_relation relation WHERE "
    		+ "roles.tenant_id=#{sysGroupRoleRelationEntity.tenantId} "
    		+ "and relation.tenant_id=#{sysGroupRoleRelationEntity.tenantId} "
    		+ "and relation.user_group_id=#{sysGroupRoleRelationEntity.userGroupId} "
    		+ "and roles.id = relation.role_id "
    		+ "and roles.valid_flag='1'")
    List<SysRoleEntity> querySysRoleByGroupId(@Param("sysGroupRoleRelationEntity") SysGroupRoleRelationEntity sysGroupRoleRelationEntity);

    /**
     * 根据用户id获取用户组的角色列表
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT roles.* FROM sys_role roles, sys_group_role_relation grouprolerelation,sys_user_group_relation usergrouprelation WHERE "
    		+ "roles.tenant_id=#{sysUserGroupRelationEntity.tenantId} "
    		+ "and grouprolerelation.tenant_id=#{sysUserGroupRelationEntity.tenantId} "
    		+ "and usergrouprelation.tenant_id=#{sysUserGroupRelationEntity.tenantId} "
    		+ "and roles.id=grouprolerelation.role_id and grouprolerelation.user_group_id=usergrouprelation.user_group_id "
    		+ "and usergrouprelation.user_id=#{sysUserGroupRelationEntity.userId} "
    		+ "and roles.valid_flag='1'")
    List<SysRoleEntity> querySysRoleByUserGroupId(@Param("sysUserGroupRelationEntity") SysUserGroupRelationEntity sysUserGroupRelationEntity);
}
