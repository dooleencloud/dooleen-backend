package com.dooleen.service.system.org.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.org.info.entity.SysOrgInfoEntity;
import com.dooleen.service.system.org.info.entity.SysOrgInfoVO;
import org.springframework.stereotype.Service;

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
@Service
public interface SysOrgInfoMapper extends BaseMapper<SysOrgInfoEntity> {

    /**
     * 获取是否有子节点
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  父机构号
     */
    @Select("SELECT count(*) FROM sys_org_info WHERE tenant_id=#{sysOrgInfoEntity.tenantId} and parent_org_no=#{sysOrgInfoEntity.orgNo}")
    int queryCountByCondition(@Param("sysOrgInfoEntity") SysOrgInfoEntity sysOrgInfoEntity);
    
    /**
     * 机构树查询
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  父机构号
     */
    @Select("SELECT * FROM sys_org_info WHERE tenant_id=#{sysOrgInfoEntity.tenantId}  and parent_org_no=#{sysOrgInfoEntity.orgNo} order by order_seq asc")
    List<SysOrgInfoEntity> queryByParentOrgNo(@Param("sysOrgInfoEntity") SysOrgInfoEntity sysOrgInfoEntity);
}
