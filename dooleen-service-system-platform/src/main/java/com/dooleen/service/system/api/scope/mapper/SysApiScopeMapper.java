package com.dooleen.service.system.api.scope.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.api.scope.entity.SysApiScopeEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-10 16:26:18
 * @Description : 系统接口管理DAO
 * @Author : apple
 * @Update: 2020-06-10 16:26:18
 */
public interface SysApiScopeMapper extends BaseMapper<SysApiScopeEntity> {

    /**
     * 租户查询接口列表
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT apiscope.* FROM sys_api_scope apiscope,sys_tenant_privilege_relation relation WHERE apiscope.tenant_id=#{sysApiScopeEntity.tenantId} and relation.tenant_id=#{sysApiScopeEntity.tenantId} and apiscope.id=relation.resource_id and relation.resource_type='2' and apiscope.valid_flag='1' order by apiscope.interface_category asc")
    List<SysApiScopeEntity> queryByApiScopeId(@Param("sysApiScopeEntity") SysApiScopeEntity sysApiScopeEntity);
    
    /**
     * 根据用户名和接口名查询是否有接口权限
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("<script>"
    		+ "SELECT count(*) FROM sys_api_scope apiscope,sys_role_privilege_relation relation WHERE "
    		+ "apiscope.tenant_id=#{tenantId} and relation.tenant_id=#{tenantId} and apiscope.id=relation.resource_id "
    		+ "and relation.resource_type='2' and relation.role_id in "
    		+"(<foreach collection='roleList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>)"
    		+ " and apiscope.interface_address=#{interfaceAddress} and apiscope.valid_flag='1' "
    		+"</script>")
    int querySysApiScopeByUserIdAndAddress(@Param("roleList") List<String> roleList, @Param("tenantId") String tenantId, @Param("interfaceAddress") String interfaceAddress);
}
