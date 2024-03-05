package com.dooleen.common.core.app.general.org.staff.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-11 10:50:02
 * @Description : 系统组织用户关系管理DAO
 * @Author : apple
 * @Update: 2020-06-11 10:50:02
 */
public interface GeneralOrgStaffRelationMapper extends BaseMapper<GeneralOrgStaffRelationEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
	@Select("<script>"
			+ "select relation.*,userinfo.id userId,userinfo.valid_flag status from general_org_staff_relation as relation "
			+ "LEFT JOIN sys_user_info as userinfo ON relation.tenant_id = userinfo.tenant_id "
			+ "and relation.staff_name = userinfo.user_name WHERE "
    		+ "relation.id in "
    		+"(<foreach collection='idList' separator=',' item='id'>" 
    		+ "#{id} "
    		+ "</foreach>)"
    		+"</script>")
    List<GeneralOrgStaffRelationEntity> queryGeneralOrgStaffRelationByCondition(@Param("idList") List<String> idList);

	@Select("<script>" +
			"SELECT  " +
			"  surr.user_id AS userId,  " +
			"  sr.role_name AS roleName,  " +
			"  sr.role_nick_name AS roleNickName  " +
			"FROM  " +
			"  sys_user_role_relation surr  " +
			"LEFT JOIN sys_role sr ON surr.tenant_id = sr.tenant_id  " +
			"AND surr.role_id = sr.id  " +
			"WHERE  " +
			"  surr.tenant_id = #{tenantId}  " +
			"AND sr.tenant_id = #{tenantId}  " +
			"AND surr.user_id IN " +
			"(<foreach collection='userIdList' separator=',' item='id'>" +
			 "#{id} " +
			"</foreach>)  " +
			"AND surr.valid_flag = '1'  " +
			"AND sr.valid_flag = '1'" +
			"</script>")
	List<Map<String, String>> queryUserRolesByUserId(@Param("tenantId") String tenantId, @Param("userIdList") List<String> userIdList);
}
