package com.dooleen.service.general.project.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.project.info.entity.GeneralProjectInfoEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-25 23:09:50
 * @Description : 项目信息管理DAO
 * @Author : apple
 * @Update: 2020-06-25 23:09:50
 */
public interface GeneralProjectInfoMapper extends BaseMapper<GeneralProjectInfoEntity> {

    /**
     * 根据用户ID查询用户所在的项目
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT distinct(org.belong_project_no) FROM general_org_info org, general_org_staff_relation orgstaff WHERE "
    		+ "org.tenant_id=#{tenantId} and orgstaff.tenant_id=#{tenantId} "
    		+ "and org.org_no=orgstaff.belong_org_no and orgstaff.staff_name=#{staffId}")
    List<String > queryProjectNoByStaffId(@Param("tenantId") String tenantId,@Param("staffId") String staffId);
    
}
