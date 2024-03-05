package com.dooleen.common.core.app.general.org.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 00:05:24
 * @Description : 组织机构管理DAO
 * @Author : apple
 * @Update: 2020-06-04 00:05:24
 */
public interface GeneralOrgInfoMapper extends BaseMapper<GeneralOrgInfoEntity> {

    /**
     * 获取是否有子节点
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  父机构号
     */
    @Select("SELECT count(*) FROM general_org_info WHERE tenant_id=#{generalOrgInfoEntity.tenantId} and parent_org_no=#{generalOrgInfoEntity.orgNo}")
    int queryCountByCondition(@Param("generalOrgInfoEntity") GeneralOrgInfoEntity generalOrgInfoEntity);
    
    /**
     * 机构树查询
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  父机构号
     */
    @Select("SELECT * FROM general_org_info WHERE tenant_id=#{generalOrgInfoEntity.tenantId}  and parent_org_no=#{generalOrgInfoEntity.orgNo} order by order_seq asc")
    List<GeneralOrgInfoEntity> queryByParentOrgNo(@Param("generalOrgInfoEntity") GeneralOrgInfoEntity generalOrgInfoEntity);


    /**
     * 查询当前用户所在的节点
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  父机构号
     */
    @Select("<script>"
            +"SELECT b.parent_org_no FROM general_org_staff_relation a, general_org_info b  WHERE "
            +"a.tenant_id=#{tenantId}  "
            +"and b.tenant_id=#{tenantId}  "
            +"and a.staff_name=#{userName} "
            +"and a.belong_org_no in "
            +"(<foreach collection='orgNoList' separator=',' item='id'>"
            + "#{id} "
            + "</foreach>)"
            +" and b.org_no = a.belong_org_no"
            +"</script>")
    List<String> queryCurrentOrgNo(@Param("orgNoList") List<String> orgNoList, @Param("tenantId") String tenantId, @Param("userName") String  userName);

}
