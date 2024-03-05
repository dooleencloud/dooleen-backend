package com.dooleen.service.general.calendar.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.calendar.info.entity.GeneralCalendarInfoEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-30 14:39:14
 * @Description : 日历管理DAO
 * @Author : apple
 * @Update: 2020-07-30 14:39:14
 */
@Service
public interface GeneralCalendarInfoMapper extends BaseMapper<GeneralCalendarInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralCalendarInfoEntity> querySysCalendarInfoByCondition(@Param("generalCalendarInfoEntity") GeneralCalendarInfoEntity generalCalendarInfoEntity);
    
    /**
     * 根据用户id获取用户的角色id列表
     */
    @Select("SELECT roles.id FROM sys_role roles, sys_user_role_relation relation WHERE roles.tenant_id=#{tenantId} and relation.tenant_id=#{tenantId} and relation.user_id=#{userId} and roles.id = relation.role_id and roles.valid_flag='1'")
    List<String> queryRoleListByUserId(@Param("tenantId") String tenantId,@Param("userId") String userId);
   
    
    /**
     * 根据用户id获取用户组的角色id列表
     */
    @Select("SELECT roles.id FROM sys_role roles, sys_group_role_relation grouprolerelation,sys_user_group_relation usergrouprelation WHERE roles.tenant_id=#{tenantId} and grouprolerelation.tenant_id=#{tenantId} and usergrouprelation.tenant_id=#{tenantId} and roles.id=grouprolerelation.role_id and grouprolerelation.user_group_id=usergrouprelation.user_group_id and usergrouprelation.user_id=#{userId} and roles.valid_flag='1'")
    List<String> queryRoleListByUserGroupUserId(@Param("tenantId") String tenantId,@Param("userId") String userId);
    
    /**
     * 根据用户角色ID列表查询用户拥有的日历列表
     */
    @Select("<script>"
    		+ "SELECT calendar.*,relation.read_flag FROM general_calendar_info calendar,sys_role_privilege_relation relation WHERE "
    		+ "calendar.tenant_id=#{tenantId} and relation.tenant_id=#{tenantId} and calendar.id=relation.resource_id "
    		+ "and relation.resource_type='5' and relation.role_id in "
    		+"(<foreach collection='roleList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>)"
    		+ " and calendar.valid_flag='1' "
    		+"</script>")
    List<GeneralCalendarInfoEntity> queryCalendarByRoleList(@Param("tenantId") String tenantId, 
    														@Param("roleList") List<String> roleList);
}
