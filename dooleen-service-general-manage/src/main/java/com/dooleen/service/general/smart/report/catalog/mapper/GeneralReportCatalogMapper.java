package com.dooleen.service.general.smart.report.catalog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.smart.report.catalog.entity.GeneralReportCatalogEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-05 14:49:49
 * @Description : 报表目录管理DAO
 * @Author : apple
 * @Update: 2020-08-05 14:49:49
 */
public interface GeneralReportCatalogMapper extends BaseMapper<GeneralReportCatalogEntity> {
	/**
	 * 根据父节点查询目录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月21日 上午8:28:31
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
    @Select("SELECT * FROM general_report_catalog	 WHERE tenant_id=#{generalReportCatalog.tenantId} and parent_catalog_id=#{generalReportCatalog.id} and valid_flag='1' order by order_seq asc")
	List<GeneralReportCatalogEntity> queryByParentCatalogId(@Param("generalReportCatalog") GeneralReportCatalogEntity generalReportCatalog);
    
    
    
    /**
     * 根据用户id获取用户的角色id列表
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT roles.id FROM sys_role roles, sys_user_role_relation relation WHERE roles.tenant_id=#{tenantId} and relation.tenant_id=#{tenantId} and relation.user_id=#{userId} and roles.id = relation.role_id and roles.valid_flag='1'")
    List<String> queryRoleListByUserId(@Param("tenantId") String tenantId,@Param("userId") String userId);
   
    
    /**
     * 根据用户id获取用户组的角色id列表
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT roles.id FROM sys_role roles, sys_group_role_relation grouprolerelation,sys_user_group_relation usergrouprelation WHERE roles.tenant_id=#{tenantId} and grouprolerelation.tenant_id=#{tenantId} and usergrouprelation.tenant_id=#{tenantId} and roles.id=grouprolerelation.role_id and grouprolerelation.user_group_id=usergrouprelation.user_group_id and usergrouprelation.user_id=#{userId} and roles.valid_flag='1'")
    List<String > queryRoleListByUserGroupUserId(@Param("tenantId") String tenantId,@Param("userId") String userId);
    
    
    /**
     * 根据用户角色ID列表查询用户拥有的目录列表
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("<script>"
    		+ "SELECT filecatalog.*,relation.read_flag FROM general_report_catalog	 filecatalog,sys_role_privilege_relation relation WHERE "
    		+ "filecatalog.tenant_id=#{tenantId} and relation.tenant_id=#{tenantId} and filecatalog.id=relation.resource_id "
    		+ "and relation.resource_type='3' and relation.role_id in "
    		+"(<foreach collection='roleList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>)"
    		+ " and filecatalog.valid_flag='1' "
    		+"</script>")
    List<GeneralReportCatalogEntity> queryCatalogByRoleList(@Param("roleList") List<String> roleList, @Param("tenantId") String tenantId);
}
