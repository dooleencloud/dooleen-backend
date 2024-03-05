package com.dooleen.service.biz.apparch.system.mapper;

import com.dooleen.service.biz.apparch.system.entity.BizApparchSystemEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-31 20:02:53
 * @Description : 应用系统管理DAO
 * @Author : apple
 * @Update: 2020-08-31 20:02:53
 */
public interface BizApparchSystemMapper extends BaseMapper<BizApparchSystemEntity> {

	/**
     * 获取是否有子节点
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  上级菜单ID
     */
    @Select("SELECT count(*) FROM biz_apparch_system WHERE tenant_id=#{bizApparchSystemEntity.tenantId} "
    		+ "and parent_system_id=#{bizApparchSystemEntity.id} "
    		+ "and valid_flag='1'")
    int queryCountByCondition(@Param("bizApparchSystemEntity") BizApparchSystemEntity bizApparchSystemEntity);
}
