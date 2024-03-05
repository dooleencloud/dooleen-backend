package com.dooleen.common.core.app.general.flow.mapper;

import java.util.List;

import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessCcRecordEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-30 09:54:05
 * @Description : 流程处理抄送记录表DAO
 * @Author : apple
 * @Update: 2020-07-30 09:54:05
 */
public interface GeneralFlowProcessCcRecordMapper extends BaseMapper<GeneralFlowProcessCcRecordEntity> {

    /**
     * 批量更新处理记录的阶段状态
     * @param generalFlowProcessRecordEntity
     * @return
     */
    @Select("update general_flow_process_cc_record set  flow_end_flag =  #{generalFlowProcessRecordEntity.flowEndFlag}, process_stage_status = #{generalFlowProcessRecordEntity.processStageStatus} where tenant_id=#{generalFlowProcessRecordEntity.tenantId} and biz_id=#{generalFlowProcessRecordEntity.bizId}")
    void batchUpdateProcessRecord(@Param("generalFlowProcessRecordEntity") GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity);
}
