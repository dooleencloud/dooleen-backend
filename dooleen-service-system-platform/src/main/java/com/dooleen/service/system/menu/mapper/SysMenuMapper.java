package com.dooleen.service.system.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.user.role.entity.SysUserRoleRelationEntity;
import com.dooleen.service.system.menu.entity.SysMenuEntity;
import com.dooleen.service.system.tenant.menu.entity.SysTenantMenuRelationEntity;
import com.dooleen.service.system.tenant.privilege.entity.SysTenantPrivilegeRelationEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-08 16:40:01
 * @Description : 系统菜单管理DAO
 * @Author : apple
 * @Update: 2020-06-08 16:40:01
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
	
    /**
     * 获取是否有子节点
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  上级菜单ID
     */
    @Select("SELECT count(*) FROM sys_menu WHERE tenant_id=#{sysMenuEntity.tenantId} "
    		+ "and parent_menu_id=#{sysMenuEntity.id} "
    		+ "and valid_flag='1'")
    int queryCountByCondition(@Param("sysMenuEntity") SysMenuEntity sysMenuEntity);
    
    /**
     * 租户菜单树查询
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  父机构号
     */
    //@Select("SELECT * FROM sys_menu WHERE tenant_id=#{sysMenuEntity.tenantId}  and parent_menu_id=#{sysMenuEntity.id} and valid_flag='1' order by menu_seq asc")
    @Select("<script>"
    		+ "SELECT menu.* FROM sys_menu menu,sys_tenant_privilege_relation relation WHERE "
    		+ "menu.tenant_id=#{sysTenantPrivilegeRelationEntity.tenantId} "
    		+ "and relation.tenant_id=#{sysTenantPrivilegeRelationEntity.tenantId} "
    		+ "and relation.resource_type=#{sysTenantPrivilegeRelationEntity.resourceType}  "
    		+ "and menu.id=relation.resource_id  "
    		+"<if test='menuCategory == 1'>"
    		+ "and menu.menu_category=#{menuCategory}  "
    		+"</if>"
    		+ "and  menu.valid_flag='1' order by menu.menu_seq asc"
    		+ "</script>")
    List<SysMenuEntity> queryByParentMenuId(@Param("sysTenantPrivilegeRelationEntity") SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity,@Param("menuCategory") String menuCategory);
   
    
    /**
     * 项目菜单树查询
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  父机构号
     */
    //@Select("SELECT * FROM sys_menu WHERE tenant_id=#{sysMenuEntity.tenantId}  and parent_menu_id=#{sysMenuEntity.id} and valid_flag='1' order by menu_seq asc")
    @Select("<script>"
    		+ "SELECT menu.* FROM sys_menu menu,general_project_privilege_relation relation WHERE "
    		+ "menu.tenant_id=#{sysTenantPrivilegeRelationEntity.tenantId} "
    		+ "and relation.tenant_id=#{sysTenantPrivilegeRelationEntity.tenantId} "
    		+ "and relation.project_id=#{projectId} "
    		+ "and relation.resource_type=#{sysTenantPrivilegeRelationEntity.resourceType}  "
    		+ "and menu.id=relation.resource_id  "
    		+"<if test='menuCategory == 1'>"
    		+ "and menu.menu_category=#{menuCategory}  "
    		+"</if>"
    		+ "and  menu.valid_flag='1' order by menu.menu_seq asc"
    		+ "</script>")
    List<SysMenuEntity> queryByParentMenuIdAndProject(@Param("sysTenantPrivilegeRelationEntity") SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity,@Param("menuCategory") String menuCategory,@Param("projectId") String projectId);
   
    /**
     * 超级用户查询菜单树查询
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  父机构号
     */
    @Select("<script>"
    		+ "SELECT * FROM sys_menu WHERE tenant_id=#{sysMenuEntity.tenantId}  "
    		+"<if test='menuCategory == 1'>"
    		+ "and menu_category=#{menuCategory}  "
    		+"</if>"
    		+ "and valid_flag='1' order by menu_seq asc "
    		+ "</script>")
    List<SysMenuEntity> queryByParentMenuIdForSuper(@Param("sysMenuEntity") SysMenuEntity sysMenuEntity,@Param("menuCategory") String menuCategory);

    /**
     * 根据租户id获取租户菜单列表
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  
     */
    @Select("SELECT menu.* FROM sys_menu menu, sys_tenant_menu_relation relation WHERE "
    		+ "menu.tenant_id=#{sysTenantMenuRelationEntity.tenantId} "
    		+ "and relation.tenant_id=#{sysTenantMenuRelationEntity.tenantId} "
    		+ "and relation.user_tenant_id=#{sysTenantMenuRelationEntity.userTenantId} "
    		+ "and menu.id = relation.menu_id "
    		+ "and menu.valid_flag='1'  order by menu.menu_seq asc")
    List<SysMenuEntity> querySysMenuListByTenantId(@Param("sysTenantMenuRelationEntity") SysTenantMenuRelationEntity sysTenantMenuRelationEntity);

    /**
     * 根据用户id获取用户菜单列表
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  
     */
    @Select("<script>"
    		+ "SELECT menu.* FROM sys_menu menu, sys_role_privilege_relation roleprivilege WHERE menu.tenant_id=#{tenantId} "
    		+ "and roleprivilege.tenant_id=#{tenantId} "
    		+ "and roleprivilege.resource_type='1' "
    		+ "and roleprivilege.role_id in "
    		+"(<foreach collection='roleList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>)"
    		+ " and menu.id = roleprivilege.resource_id and menu.menu_level=#{menuLevel} "
    		+ "and menu.menu_category=#{menuCategory} "
    		+ "and menu.project_control_flag = '0'  "
    		+ "and menu.valid_flag='1' "
    		+ "GROUP BY(menu.id)  order by menu.menu_seq asc"
    		+"</script>")
    List<SysMenuEntity> querySysMenuListByRoleList(@Param("roleList") List<String> roleList,@Param("tenantId") String tenantId,@Param("menuCategory") String menuCategory, @Param("menuLevel") String menuLevel);

    /**
     * 根据用户id获取项目相关的菜单
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  
     */
    @Select("<script>"
    		+ "SELECT menu.* FROM sys_menu menu, sys_role_privilege_relation roleprivilege, sys_role role WHERE menu.tenant_id=#{tenantId} "
    		+ "and roleprivilege.tenant_id=#{tenantId} "
    		+ "and roleprivilege.resource_type='1' "
    		+ "and roleprivilege.role_id in "
    		+"(<foreach collection='roleList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>)"
    		+ " and role.tenant_id=#{tenantId} " 
    		+ "and role.project_id = #{projectId} "
    		+ "and role.id = roleprivilege.role_id "
    		+ "and menu.id = roleprivilege.resource_id and menu.menu_level=#{menuLevel} "
    		+ "and menu.menu_category=#{menuCategory} "
    		+ "and menu.project_control_flag = '1'  "
    		+ "and menu.valid_flag='1' "
    		+ "GROUP BY(menu.id)  order by menu.menu_seq asc"
    		+"</script>")
    List<SysMenuEntity> querySysMenuListByRoleListAndProject(@Param("roleList") List<String> roleList,@Param("tenantId") String tenantId,@Param("projectId") String projectId, @Param("menuCategory") String menuCategory, @Param("menuLevel") String menuLevel);
    
    /**
     * 根据用户权限列表获取用户按钮权限列表
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  
     */
    @Select("<script>"
    		+ "SELECT menu.menu_no FROM sys_menu menu, sys_role_privilege_relation roleprivilege WHERE menu.tenant_id=#{tenantId} and roleprivilege.tenant_id=#{tenantId} and roleprivilege.resource_type='1' and roleprivilege.role_id in "
    		+"(<foreach collection='roleList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>) "
    		+ "and menu.id = roleprivilege.resource_id "
    		+ "and menu.menu_category=#{menuCategory} "
    		+ "and menu.valid_flag='1'  GROUP BY(menu.id)  order by menu.menu_seq asc"
    		+"</script>")
    List<String> querySysButtonListByRoleList(@Param("roleList") List<String> roleList,@Param("tenantId") String tenantId,@Param("menuCategory") String menuCategory, @Param("menuLevel") String menuLevel);

    @Select("<script>"
    		+ "select resource_id AS resourceId from sys_tenant_privilege_relation where tenant_id = #{tenantId} and valid_flag = '1'"
    		+ "</script>")
	List<String> selectAllTenantResourceId(@Param("tenantId") String tenantId);
	
}
