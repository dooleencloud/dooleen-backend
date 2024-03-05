package com.dooleen.common.core.app.general.flow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessUserEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-01 14:20:27
 * @Description : 流程处理记录管理DAO
 * @Author : apple
 * @Update: 2020-07-01 14:20:27
 */
@Service
public interface GeneralFlowProcessRecordMapper extends BaseMapper<GeneralFlowProcessRecordEntity> {

    /**
     * 根据用户角色列表获取用户名userName
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  List<roleNo>
     */
    @Select("<script>"
    		+ "SELECT  user.user_name ,user.real_name  FROM sys_user_role_relation relation,sys_user_info user,sys_role role WHERE "
    		+ "relation.tenant_id=#{tenantId} and user.tenant_id=#{tenantId}  and role.tenant_id=#{tenantId}  and role.role_name in"
    		+"(<foreach collection='roleList' separator=',' item='name'>" 
    		+ "#{name} "
    		+ "</foreach>)"
    		+ "and relation.role_id=role.id and relation.user_id = user.id and user.valid_flag='1'"
    		+"</script>")
    List<GeneralFlowProcessUserEntity> queryUserNameByRoleList(@Param("roleList") List<String> roleList, @Param("tenantId") String tenantId);


	/**
	 * 批量更新处理记录的阶段状态
	 * @param generalFlowProcessRecordEntity
	 * @return
	 */
	@Select("update general_flow_process_record set flow_end_flag =  #{generalFlowProcessRecordEntity.flowEndFlag}, process_stage_status = #{generalFlowProcessRecordEntity.processStageStatus} where tenant_id=#{generalFlowProcessRecordEntity.tenantId} and biz_id=#{generalFlowProcessRecordEntity.bizId} and valid_flag='1'")
	void batchUpdateProcessRecord(@Param("generalFlowProcessRecordEntity") GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity);

	/**
	 * 批量更新处理记录的阶段状态
	 * @param generalFlowProcessRecordEntity
	 * @return
	 */
	@Select("update general_flow_process_record set process_finish_datetime = #{generalFlowProcessRecordEntity.processFinishDatetime} where tenant_id=#{generalFlowProcessRecordEntity.tenantId} and biz_id=#{generalFlowProcessRecordEntity.bizId} and node_order_seq='1'")
	void updateFirstProcessRecord(@Param("generalFlowProcessRecordEntity") GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity);

}
